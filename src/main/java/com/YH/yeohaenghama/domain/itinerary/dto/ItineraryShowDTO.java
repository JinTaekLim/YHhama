package com.YH.yeohaenghama.domain.itinerary.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ItineraryShowDTO {
    @Schema(description = "일정 ID")
    private Long itineraryId;
    @Schema(description = "일정 이름")
    private String name;
    @Schema(description = "교통 수단")
    private String transportation;
    @Schema(description = "지역")
    private String area;
    @Schema(description = "시작 일시")
    private String startDate;
    @Schema(description = "종료 일시")
    private String endDate;
    @Schema(description = "경비")
    private String expense;
    
    private AccountShowDTO account;
    private List<PlaceShowDTO> place;

    public ItineraryShowDTO(Itinerary itinerary, AccountShowDTO account, List<PlaceShowDTO> placeShowDTOs) {
        this.itineraryId = itinerary.getId();
        this.name = itinerary.getName();
        this.transportation = itinerary.getTransportation();
        this.area = itinerary.getArea();
        this.startDate = itinerary.getStartDate();
        this.endDate = itinerary.getEndDate();
        this.expense = itinerary.getExpense();
        this.account = account;
        this.place = placeShowDTOs;
    }

}
