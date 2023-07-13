package com.petpic.petpicserv.service;

import com.petpic.petpicserv.model.Pet;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface IPetService {
    Pet create(MultipartFile file, String description) throws IOException;

    Optional<Pet> read(Integer fileID);

    ResponseEntity<Resource> loadImage(Integer fileID);

    Boolean delete(Integer fileID);

    Optional<Pet> update(Integer fileId, MultipartFile file,
                         String description);

}
