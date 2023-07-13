package com.petpic.petpicserv.controller;


import com.petpic.petpicserv.model.Pet;
import com.petpic.petpicserv.service.IPetService;
import com.petpic.petpicserv.validator.ImageValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Size;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@Validated
public class PetController {

    @Autowired
    private IPetService petService;

    @Autowired
    private ImageValidator imageValidator;

    @PostMapping("/file")
    public Pet handleFileUpload(@Size(min = 3, max = 100) String description, @RequestParam(value = "file", required = true) MultipartFile file) throws IOException {


        if (!imageValidator.validate(file)) {
            throw new MultipartException("This is an invalid image");
        }

        return petService.create(file, description);
    }

    @GetMapping("/file/{fileID}")
    public Optional<Pet> getAllFiles(@PathVariable Integer fileID) {
        return Optional.ofNullable(petService.read(fileID).orElseThrow(EntityNotFoundException::new));
    }

    @GetMapping(value = "/images/{fileId}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<Resource> getImage(@PathVariable Integer fileId) {

        return petService.loadImage(fileId);
    }

    @DeleteMapping(value = "/file/{fileId}")
    public Map<String, Boolean> deleteEntity(@PathVariable Integer fileId) {
        petService.delete(fileId);

        Map<String, Boolean> retval = new HashMap<>();
        retval.put("success", true);
        return retval;
    }

    @PatchMapping(value = "/file/{fileId}")
    public Optional<Pet> update(@PathVariable Integer fileId,
                                @RequestParam(value = "description",
                                        required = false) @Size(min
            = 3, max = 100) String description, @RequestParam(value = "file",
            required = false) MultipartFile file) {
        if (file != null && !imageValidator.validate(file)) {
            throw new MultipartException("This is an invalid image");
        }
        return Optional.ofNullable(petService.update(fileId, file, description).orElseThrow(EntityNotFoundException::new));
    }

}
