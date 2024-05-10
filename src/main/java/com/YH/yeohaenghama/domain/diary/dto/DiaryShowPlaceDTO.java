package com.YH.yeohaenghama.domain.diary.dto;


import com.YH.yeohaenghama.domain.itinerary.dto.PlaceShowDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.review.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

public class DiaryShowPlaceDTO {
    @Data @Schema(name = "DiaryShowPlaceDTO_Response")
    public static class Response{
        @Schema(description = "지정 날짜")
        private Integer day;
        @Schema(description = "장소 ID")
        private Long PlaceId;
        @Schema(description = "시작 시간")
        private String startTime;
        @Schema(description = "종료 시간")
        private String endTime;
        @Schema(description = "장소 타입 번호")
        private String placeType;
        @Schema(description = "장소 번호(코드)")
        private String placeNum;
        @Schema(description = "장소 이름")
        private String placeName;
        @Schema(description = "장소 주소")
        private String addr1;
        @Schema(description = "X　좌표")
        private double mapx;
        @Schema(description = "Y　좌표")
        private double mapy;
        @Schema(description = "메모")
        private String memo;
        @Schema(description = "리뷰")
        private DiaryShowReviewlDTO.Response review;

        public static DiaryShowPlaceDTO.Response fromEntity(Place place, List<Review> reviews) {
            DiaryShowPlaceDTO.Response response = new DiaryShowPlaceDTO.Response();
            System.out.println("placeNum = " + place.getPlaceNum() + "   placeType = " + place.getPlaceType());
            for(Review review : reviews){
                System.out.println("ReivewplaceNum = " + review.getContentId() + "   ReivewplaceType = " + review.getContentTypeId());
                if (place.getPlaceNum() != null
                        && review.getContentId() != null
                        && place.getPlaceNum().equals(String.valueOf(review.getContentId()))
                        && place.getPlaceType().equals(String.valueOf(review.getContentTypeId()))
                ) {
                    response.setReview(DiaryShowReviewlDTO.Response.fromEntity(review));
                }

            }

            response.setPlaceId(place.getId());
            response.setDay(place.getDay());
            response.setStartTime(place.getStartTime());
            response.setEndTime(place.getEndTime());
            response.setPlaceType(place.getPlaceType());
            response.setPlaceNum(place.getPlaceNum());
            response.setPlaceName(place.getPlaceName());
            response.setAddr1(place.getAddr1());
            response.setMapx(place.getMapx());
            response.setMapy(place.getMapy());
            response.setMemo(place.getMemo());
            return response;
        }
    }
}
