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

    private List<String> type;    // 누구와 여행하는지
    private List<String> style;    // 여행 스타일


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
    @Schema(description = "일정을 제작한 회원 정보")
    private AccountShowDTO account;
    @Schema(description = "일정의 여행지 정보")
    private List<PlaceShowDTO> place;

    public ItineraryShowDTO(Itinerary itinerary, AccountShowDTO account, List<PlaceShowDTO> placeShowDTOs) {
        this.itineraryId = itinerary.getId();
        this.type = itinerary.getType();
        this.style = itinerary.getItineraryStyle();
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
