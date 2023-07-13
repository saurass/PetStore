package com.petpic.petpicserv.engine;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileEngine {

    Boolean storeFile(String filePath, MultipartFile fileToUpload) throws IOException;

    Resource load(String fileID);

    Boolean delete(String fileID);

    Boolean update(String fileID, MultipartFile file);

}
