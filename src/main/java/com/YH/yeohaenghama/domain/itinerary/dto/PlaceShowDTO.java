package com.YH.yeohaenghama.domain.itinerary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceShowDTO {
//    @Schema(description = "지정 날짜")
//    private int day;
    @Schema(description = "장소 번호(코드)")
    private String placeNum;
    @Schema(description = "장소 이름")
    private String placeName;

}
