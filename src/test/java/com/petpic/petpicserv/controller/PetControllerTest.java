package com.petpic.petpicserv.controller;

import com.petpic.petpicserv.model.Pet;
import com.petpic.petpicserv.service.IPetService;
import com.petpic.petpicserv.validator.ImageValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetControllerTest {

    @Mock
    private IPetService petService;

    @Mock
    private ImageValidator imageValidator;

    @InjectMocks
    private PetController petController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleFileUpload_ValidImage_ShouldCreatePet() throws IOException {
        // Arrange
        String description = "Pet description";
        MultipartFile file = mock(MultipartFile.class);
        when(imageValidator.validate(file)).thenReturn(true);
        Pet createdPet = new Pet();
        when(petService.create(file, description)).thenReturn(createdPet);

        // Act
        Pet result = petController.handleFileUpload(description, file);

        // Assert
        assertNotNull(result);
        assertEquals(createdPet, result);
        verify(petService, times(1)).create(file, description);
    }

    @Test
    void handleFileUpload_InvalidImage_ShouldThrowMultipartException() throws IOException {
        // Arrange
        String description = "Pet description";
        MultipartFile file = mock(MultipartFile.class);
        when(imageValidator.validate(file)).thenReturn(false);

        // Act & Assert
        assertThrows(MultipartException.class,
                () -> petController.handleFileUpload(description, file));
        verify(petService, never()).create(file, description);
    }

    @Test
    void getAllFiles_ValidFileId_ShouldReturnPet() {
        // Arrange
        int fileId = 1;
        Pet pet = new Pet();
        when(petService.read(fileId)).thenReturn(Optional.of(pet));

        // Act
        Optional<Pet> result = petController.getAllFiles(fileId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(pet, result.get());
        verify(petService, times(1)).read(fileId);
    }

    @Test
    void getAllFiles_InvalidFileId_ShouldThrowEntityNotFoundException() {
        // Arrange
        int fileId = 1;
        when(petService.read(fileId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> petController.getAllFiles(fileId));
        verify(petService, times(1)).read(fileId);
    }

    @Test
    void getImage_ShouldReturnImageResponseEntity() {
        // Arrange
        int fileId = 1;
        ResponseEntity<Resource> responseEntity = mock(ResponseEntity.class);
        when(petService.loadImage(fileId)).thenReturn(responseEntity);

        // Act
        ResponseEntity<Resource> result = petController.getImage(fileId);

        // Assert
        assertNotNull(result);
        assertEquals(responseEntity, result);
        verify(petService, times(1)).loadImage(fileId);
    }

    @Test
    void deleteEntity_ShouldReturnSuccessMap() {
        // Arrange
        int fileId = 1;
        Map<String, Boolean> expectedMap = new HashMap<>();
        expectedMap.put("success", true);

        // Act
        Map<String, Boolean> result = petController.deleteEntity(fileId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedMap, result);
        verify(petService, times(1)).delete(fileId);
    }

    @Test
    void update_ValidImage_ShouldReturnUpdatedPet() throws IOException {
        // Arrange
        int fileId = 1;
        String description = "Updated description";
        MultipartFile file = mock(MultipartFile.class);
        when(imageValidator.validate(file)).thenReturn(true);
        Pet updatedPet = new Pet();
        when(petService.update(fileId, file, description)).thenReturn(Optional.of(updatedPet));

        // Act
        Optional<Pet> result = petController.update(fileId, description, file);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(updatedPet, result.get());
        verify(petService, times(1)).update(fileId, file, description);
    }

    @Test
    void update_InvalidImage_ShouldThrowMultipartException() throws IOException {
        // Arrange
        int fileId = 1;
        String description = "Updated description";
        MultipartFile file = mock(MultipartFile.class);
        when(imageValidator.validate(file)).thenReturn(false);

        // Act & Assert
        assertThrows(MultipartException.class, () -> petController.update(fileId, description, file));
        verify(petService, never()).update(fileId, file, description);
    }

}
