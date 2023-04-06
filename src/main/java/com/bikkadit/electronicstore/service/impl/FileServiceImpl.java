package com.bikkadit.electronicstore.service.impl;

import com.bikkadit.electronicstore.exception.BadApiRequest;
import com.bikkadit.electronicstore.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {

        // abc.png
        String originalFilename = file.getOriginalFilename();
        logger.info("Filename :{}", originalFilename);
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNamewithExtension = filename + extension;
        String fullPathWithFileName = path  + fileNamewithExtension;

        logger.info("full image path: {} ",fullPathWithFileName);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

            // file Save
logger.info("File extension is {}",extension);
            File folder = new File(path);

            if (!folder.exists()) {

                // create Folder
                folder.mkdirs();
            }
            // upload

            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNamewithExtension;


        } else {
            throw new BadApiRequest("File with this " + extension + " not Allowed !!");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String fullPath = path + File.separator + name;

        InputStream inputStream = new FileInputStream(fullPath);

        return inputStream;
    }
}
