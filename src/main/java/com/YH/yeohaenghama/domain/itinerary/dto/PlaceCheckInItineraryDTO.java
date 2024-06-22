package com.YH.yeohaenghama.domain.itinerary.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "PlaceCheckInItineraryDTO")
@Data
public class PlaceCheckInItineraryDTO {
    private Long itineraryId;
    private String placeType;
    private String placeNum;
}
