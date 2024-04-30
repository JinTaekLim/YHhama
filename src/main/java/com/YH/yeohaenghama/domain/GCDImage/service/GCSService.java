package com.YH.yeohaenghama.domain.GCDImage.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

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


    public String uploadPhoto(MultipartFile file, String fileName, String folder) throws IOException {
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

        storage.create(blobInfo, file.getInputStream());

        String publicUrl = "https://storage.googleapis.com/" + bucketName + "/" + filePath;
        log.info("파일 업로드 성공 : " + publicUrl);
        return publicUrl;
    }

    public void delete(String folderName) throws IOException {
        String projectId = "yhhama";

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).setCredentials(credentials).build().getService();
        Bucket bucket = storage.get(bucketName);

        Iterable<Blob> blobs = bucket.list().iterateAll();
        boolean found = false;

        for (Blob blob : blobs) {
            String blobName = blob.getName();
            if (blobName.startsWith(folderName + "/")) {
                found = true;
                Storage.BlobSourceOption precondition = Storage.BlobSourceOption.generationMatch(blob.getGeneration());
                storage.delete(bucketName, blobName, precondition);
                System.out.println("파일 삭제 성공 " + blobName);
            }
        }

        if (!found) {
            System.out.println("해당 파일을 찾을 수 없습니다 : " + folderName);
        }
    }



}
