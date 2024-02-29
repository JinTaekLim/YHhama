package com.YH.yeohaenghama.domain.test.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    private static final String UPLOAD_DIR = "uploads";

    public String saveImage(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + "/" + fileName);
            Files.write(path, bytes);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
