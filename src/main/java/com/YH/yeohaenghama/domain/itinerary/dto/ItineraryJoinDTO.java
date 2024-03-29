package com.YH.yeohaenghama.domain.itinerary.dto;

import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ItineraryJoinDTO {

    @Schema(description = "일정 이름", example = "일정 이름")
    private String name;
    @Schema(description = "동반자(0:기타, 1:혼자, 2:친구와, 3:연인과, 4:배우자와,5:아이와,6:부모님과)" , example = "동반자(0:기타, 1:혼자, 2:친구와, 3:연인과, 4:배우자와,5:아이와,6:부모님과)")
    private List<String> type;
    @Schema(description = "일정 스타일(1:체험－엑티비티, 2:sns－핫플레이스, 3:자연과함께, 4:유명한 관광지는 필수, 5:여유롭게 힐링, 6:문화－예술－역사, 7:여행지 느낌 물씬, 8:쇼핑은 열정적으로, 9:관광보다 먹방)" , example = "일정 스타일(1:체험－엑티비티, 2:sns－핫플레이스, 3:자연과함께, 4:유명한 관광지는 필수, 5:여유롭게 힐링, 6:문화－예술－역사, 7:여행지 느낌 물씬, 8:쇼핑은 열정적으로, 9:관광보다 먹방)" +
            "\n 여러 개의 타입을 지정할땐 , 으로 구분하여 한 줄로 작성 \n ex) 0 , 1 , 2 ,3 ")
    private List<String> style;
    @Schema(description = "교통 수단(0:자동차, 1:지하철, 2:버스)" , example = "교통 수단(0:자동차, 1:지하철, 2:버스)")
    private String transportation;
    @Schema(description = "여행 지역" , example = "여행 지역")
    private String area;
    @Schema(description = "일정 시작 일시", example = "일정 시작 일시")
    private String startDate;
    @Schema(description = "일정 종료 일시" , example = "일정 종료 일시")
    private String endDate;
    @Schema(description = "경비 관리" , example = "경비 관리")
    private String expense;


    @Data
    @Schema(name = "ItineraryRequestDTO", description = " type과 style은 2개 이상의 값을 작성할 때는 , 로 구분하여 한 줄로 전달 <br> ex) 0, 1, 2")
    public static class Request {
        @Schema(description = "일정 이름", example = "일정 이름")
        private String name;
        @Schema(description = "동반자(0:기타, 1:혼자, 2:친구와, 3:연인과, 4:배우자와,5:아이와,6:부모님과)" , example = "동반자(0:기타, 1:혼자, 2:친구와, 3:연인과, 4:배우자와,5:아이와,6:부모님과)")
        private List<String> type;
        @Schema(description = "일정 스타일(1:체험－엑티비티, 2:sns－핫플레이스, 3:자연과함께, 4:유명한 관광지는 필수, 5:여유롭게 힐링, 6:문화－예술－역사, 7:여행지 느낌 물씬, 8:쇼핑은 열정적으로, 9:관광보다 먹방)" ,
                example = "1:체험－엑티비티, 2:sns－핫플레이스, 3:자연과함께, 4:유명한 관광지는 필수, 5:여유롭게 힐링, 6:문화－예술－역사, 7:여행지 느낌 물씬, 8:쇼핑은 열정적으로, 9:관광보다 먹방")
        private List<String> style;
        @Schema(description = "교통 수단(0:자동차, 1:지하철, 2:버스)")
        private String transportation;
        @Schema(description = "여행 지역")
        private String area;
        @Schema(description = "일정 시작 일시")
        private LocalDate startDate;
        @Schema(description = "일정 종료 일시")
        private LocalDate endDate;
        @Schema(description = "경비 관리")
        private String expense = "";
    }

    @Data
    @Schema(name = "ItineraryReponseDTO")
    public static class Response {
        @Schema(description = "일정 ID")
        private Long id;
        @Schema(description = "일정 이름")
        private String name;
        @Schema(description = "동반자(0:기타, 1:혼자, 2:친구와, 3:연인과, 4:배우자와,5:아이와,6:부모님과)")
        private List<String> type;
        @Schema(description = "일정 스타일(1:체험－엑티비티, 2:sns－핫플레이스, 3:자연과함께, 4:유명한 관광지는 필수, 5:여유롭게 힐링, 6:문화－예술－역사, 7:여행지 느낌 물씬, 8:쇼핑은 열정적으로, 9:관광보다 먹방)")
        private List<String> style;
        @Schema(description = "교통 수단(0:자동차, 1:지하철, 2:버스)")
        private String transportation;
        @Schema(description = "여행 지역")
        private String area;
        @Schema(description = "일정 시작 일시")
        private LocalDate startDate;
        @Schema(description = "일정 종료 일시")
        private LocalDate endDate;
        @Schema(description = "경비 관리")
        private String expense;

        public static Response fromEntity(Itinerary itinerary) {
            Response response = new Response();
            response.setId(itinerary.getId());
            response.setName(itinerary.getName());
            response.setType(itinerary.getType());
            response.setTransportation(itinerary.getTransportation());
            response.setArea(itinerary.getArea());
            response.setStartDate(itinerary.getStartDate());
            response.setEndDate(itinerary.getEndDate());
            response.setExpense(itinerary.getExpense());
            response.setStyle(itinerary.getItineraryStyle());
            return response;
        }
    }

    private Request request;
    private Response response;

    public ItineraryJoinDTO(Request request) {
        this.request = request;
    }

    public ItineraryJoinDTO(Response response) {
        this.response = response;
    }

    public Itinerary toEntity() {
        Itinerary itinerary = Itinerary.builder()
                .name(request.getName())
                .type(request.getType())
                .transportation(request.getTransportation())
                .area(request.getArea())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .expense(request.getExpense())
                .itineraryStyle(request.getStyle())
                .build();
        return itinerary;
    }
}
