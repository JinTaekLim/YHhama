package com.YH.yeohaenghama.domain.uploadImage.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class GCSService {

    private final String bucketName;
    private final GoogleCredentials credentials;

    public GCSService(@Value("${spring.cloud.gcp.storage.bucket}") String bucketName,
                      @Value("${spring.cloud.gcp.storage.credentials.location}") Resource credentialsResource) throws IOException {
        this.bucketName = bucketName;
        this.credentials = GoogleCredentials.fromStream(credentialsResource.getInputStream());
    }


    public String uploadPhoto(MultipartFile file, String fileName,String folder) throws IOException {
        if (file == null || file.isEmpty()) {
            log.error("사진 데이터를 전달 받지 못 했습니다.");
            return null;
        }

        log.info("파일명 : {}", fileName);

        String filePath = folder + "/" + fileName;

        Storage storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();

        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, filePath)
                .setContentType(file.getContentType())
                .build();

        // 파일 업로드
        storage.create(blobInfo, file.getInputStream());

        //공개 링크 생성
        String publicUrl = showURL(bucketName, filePath);

        log.info("파일 업로드 성공 : ", publicUrl);
        return publicUrl;
    }

    private String showURL(String bucketName, String objectName) {
        try {
            return "https://storage.googleapis.com/" + bucketName + "/" + objectName;
        } catch (Exception e) {
            log.error("URL 생성 실패 : {}", objectName, e.getMessage());
            throw new NoSuchElementException("[showURL] URL 생성 실패 .");
        }
    }

}
