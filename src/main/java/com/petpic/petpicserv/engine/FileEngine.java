package com.petpic.petpicserv.engine;

import com.petpic.petpicserv.engine.exception.FileEngineException;
import com.petpic.petpicserv.service.exceptions.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileEngine implements IFileEngine {

    @Value("${upload-dir.location}")
    private String rootDir;

    @Override
    public Boolean storeFile(String filePath, MultipartFile fileToUpload) throws IOException {

        Path rootLocation = Paths.get(rootDir);

        if (fileToUpload.isEmpty()) {
            throw new FileEngineException("Storage Engine failed to save empty file");
        }

        Path destinationFile = rootLocation.resolve(filePath).normalize().toAbsolutePath();

        System.out.println(destinationFile);

        try (InputStream inputStream = fileToUpload.getInputStream()) {
            Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new FileEngineException("Storage Engine failed to save empty file");
        }

        return true;

    }

    @Override
    public Resource load(String filename) {

        try {
            Path rootLocation = Paths.get(rootDir);
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename);
        } catch (StorageFileNotFoundException e) {
            throw new StorageFileNotFoundException("Unable to read file: " + filename);
        }
    }

    @Override
    public Boolean delete(String fileName) {
        Path rootLocation = Paths.get(rootDir);
        Path file = rootLocation.resolve(fileName);
        file.toFile().delete();

        return true;
    }

    @Override
    public Boolean update(String fileID, MultipartFile file) {
        return null;
    }
}
