package com.YH.yeohaenghama.domain.openApi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OpenApiDto {
    private String numOfRows;
    private String page;
    private String MobileOS;
    private String keyword;
    private String contentTypeId;

}
