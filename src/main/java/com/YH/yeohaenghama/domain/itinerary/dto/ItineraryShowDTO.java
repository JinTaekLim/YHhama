package com.YH.yeohaenghama.domain.itinerary.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ItineraryShowDTO {
    @Schema(description = "일정 ID")
    private Long itineraryId;

    @Schema(description = "동반자(0:기타, 1:혼자, 2:친구와, 3:연인과, 4:배우자와,5:아이와,6:부모님과)")
    private List<String> type;
    @Schema(description = "일정 스타일(1:체험－엑티비티, 2:sns－핫플레이스, 3:자연과함께, 4:유명한 관광지는 필수, 5:여유롭게 힐링, 6:문화－예술－역사, 7:여행지 느낌 물씬, 8:쇼핑은 열정적으로, 9:관광보다 먹방)")
    private List<String> style;
    @Schema(description = "일정 이름")
    private String name;
    @Schema(description = "교통 수단(0:자동차, 1:지하철, 2:버스)")
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
    private Map<String, List<PlaceShowDTO>> placesByDay; // 날짜별 장소를 저장할 Map

    public ItineraryShowDTO(Itinerary itinerary, AccountShowDTO account, Map<String, List<PlaceShowDTO>> placesByDay) {
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
        this.placesByDay = placesByDay;
    }

    public class AccountItineraryListDTO{
        @Schema(description = "일정 ID")
        private Long itineraryId;
        @Schema(description = "일정 이름")
        private String name;
        @Schema(description = "지역")
        private String area;
        @Schema(description = "시작 일시")
        private String startDate;
        @Schema(description = "종료 일시")
        private String endDate;
    }

    public class ItineraryToAccount{
        @Schema(description = "일정 ID")
        private Long itineraryId;
        @Schema(description = "일정을 제작한 회원 정보")
        private AccountShowDTO account;
    }
}
