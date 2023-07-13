package com.petpic.petpicserv.service;

import com.petpic.petpicserv.engine.IFileEngine;
import com.petpic.petpicserv.engine.exception.FileEngineException;
import com.petpic.petpicserv.model.Pet;
import com.petpic.petpicserv.repository.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

@Service
public class PetService implements IPetService {

    @Autowired
    private IFileEngine fileEngine;

    @Autowired
    private PetRepository petRepository;

    @Override
    @Transactional
    public Pet create(MultipartFile file, String description) throws IOException {
        String imageName = file.getOriginalFilename();
        String fileExtension = imageName.substring(imageName.lastIndexOf('.') + 1).trim();
        String fileOnlyName = imageName.split("." + fileExtension)[0];
        String newFileName = generateRandomChars(10) + '.' + fileExtension;


        Pet newPet = petRepository.save(new Pet(fileOnlyName, description, newFileName, file.getContentType()));

        String newFileAccessUrl = "/images/" + newPet.getID();
        newPet.setFileUrl(newFileAccessUrl);
        petRepository.save(newPet);

        fileEngine.storeFile(newFileName, file);

        return newPet;
    }

    @Override
    public Optional<Pet> read(Integer fileID) {
        return petRepository.findByID(fileID);
    }

    @Override
    public ResponseEntity<Resource> loadImage(Integer fileID) {


        Optional<Pet> pet = Optional.ofNullable(read(fileID).orElseThrow(EntityNotFoundException::new));

        Resource file = fileEngine.load(pet.get().getFileHashName());

        String contentType = pet.get().getContentType();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, contentType).body(file);
    }

    @Override
    @Transactional
    public Boolean delete(Integer fileID) {
        Optional<Pet> pet = Optional.ofNullable(read(fileID).orElseThrow(EntityNotFoundException::new));

        pet.ifPresent(pet1 -> {
            petRepository.delete(pet1);
        });

        fileEngine.delete(pet.get().getFileHashName());

        return true;
    }

    @Override
    @Transactional
    public Optional<Pet> update(Integer fileId, MultipartFile file, String description) {
        Optional<Pet> pet =
                Optional.ofNullable(read(fileId).orElseThrow(EntityNotFoundException::new));

        pet.ifPresent(pet1 -> {
            String newDescription = description == null ?
                    pet1.getDescription() : description;
            pet1.setDescription(newDescription);
            if(file != null) {
                // update file with same name and everything
                try {
                    fileEngine.storeFile(pet1.getFileHashName(), file);
                    pet1.setContentType(file.getContentType());
                } catch (IOException e) {
                    throw new FileEngineException("Failed to Update File");
                }
            }
            petRepository.save(pet1);
        });

        return pet;
    }

    private String generateRandomChars(int length) {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }

        return sb.toString();
    }
}
