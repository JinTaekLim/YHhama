package com.YH.yeohaenghama.domain.GCDImage.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class GCSRequest {

    private String name;
    private MultipartFile file;

}