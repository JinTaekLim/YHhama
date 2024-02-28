package com.YH.yeohaenghama.ImageTest.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

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

    public String uploadObjectAndGetUrl(MultipartFile file, String fileName) throws IOException {
        String filePath = "Profile_Image/" + fileName;

        Storage storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();

        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, filePath)
                .setContentType(file.getContentType()).build();

        Blob blob = storage.create(blobInfo, file.getInputStream());

        log.info(blob.getMediaLink());
        return blob.getMediaLink();
    }
}
