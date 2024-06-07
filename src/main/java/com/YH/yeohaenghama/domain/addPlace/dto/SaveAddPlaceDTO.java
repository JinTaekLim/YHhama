package com.YH.yeohaenghama.domain.addPlace.dto;

import com.YH.yeohaenghama.domain.addPlace.entity.AddPlace;
import com.YH.yeohaenghama.domain.budget.dto.BudgetCreateDTO;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Objects;

public class SaveAddPlaceDTO {
    @Schema(name = "saveAddPlaceDTO_Request") @Data
    public static class Request{
        private String title;
        private String tel;
        private String add1;
        private String add2;
        private String mapX;
        private String mapY;

        public static Request getRequest(String title,String add1, String add2, String tel, String mapX, String mapY){
            SaveAddPlaceDTO.Request request = new Request();
            request.setTitle(title);
            request.setAdd1(add1);
            request.setAdd2(add2);
            request.setTel(tel);
            request.setMapX(mapX);
            request.setMapY(mapY);
            return request;
        }

    }
    private SaveAddPlaceDTO.Request request;

    public SaveAddPlaceDTO(SaveAddPlaceDTO.Request request) {
        this.request = request;
    }

    public AddPlace toEntity() {
        AddPlace addPlace = AddPlace.builder()
                .title(request.getTitle())
                .tel(request.getTel())
                .add1(request.getAdd1())
                .add2(request.getAdd2())
                .mapX(request.getMapX())
                .mapY(request.getMapY())
                .build();
        return addPlace;

    }
//    @Schema(name = "saveAddPlaceDTO_Response") @Data
//    public static class Response{
//    }
}
