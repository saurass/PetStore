package com.petpic.petpicserv.service;

import com.petpic.petpicserv.engine.IFileEngine;
import com.petpic.petpicserv.model.Pet;
import com.petpic.petpicserv.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PetServiceTest {

    @Mock
    private IFileEngine fileEngine;

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void read_ExistingFileId_ShouldReturnPet() {
        // Arrange
        int fileId = 1;
        Pet pet = new Pet();
        when(petRepository.findByID(fileId)).thenReturn(Optional.of(pet));

        // Act
        Optional<Pet> result = petService.read(fileId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(pet, result.get());
        verify(petRepository, times(1)).findByID(fileId);
    }

    @Test
    void read_NonExistingFileId_ShouldReturnEmptyOptional() {
        // Arrange
        int fileId = 1;
        when(petRepository.findByID(fileId)).thenReturn(Optional.empty());

        // Act
        Optional<Pet> result = petService.read(fileId);

        // Assert
        assertTrue(result.isEmpty());
        verify(petRepository, times(1)).findByID(fileId);
    }
}
