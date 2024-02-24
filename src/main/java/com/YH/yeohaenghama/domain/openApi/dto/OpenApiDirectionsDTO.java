package com.YH.yeohaenghama.domain.openApi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OpenApiDirectionsDTO {
    @Schema(description = "출발지 X 좌표" , defaultValue = "126.6206396146")
    private String sx;
    @Schema(description = "출발지 Y 좌표" , defaultValue = "37.4728532152")
    private String sy;
    @Schema(description = "도착지 X 좌표" , defaultValue = "126.4159494251")
    private String ex;
    @Schema(description = "도착지 Y 좌표" , defaultValue = "37.3915951817")
    private String ey;
    @Schema(description = "이동 수단( 0: 모두 , 1: 지하철 , 2: 버스 )" , defaultValue = "0")
    private String searchPathType;
}
