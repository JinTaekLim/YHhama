package com.YH.yeohaenghama.domain.rating.dto;

import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.rating.entity.Rating;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RatingDTO {
    @Data
    public static class Request{
        @Schema(description = "장소 번호")
        private Long contentId;     // 장소 번호
        @Schema(description = "관광 타입 번호")
        private Long contentTypeId; // 관광 타입 번호
        @Schema(description = "평점")
        private Long rating;    // 평점
        @Schema(description = "유저 Id")
        private Long accountId; //　유저 ID
    }
    @Data
    public static class Response{
        @Schema(description = "장소 번호")
        private Long contentId;     // 장소 번호
        @Schema(description = "관광 타입 번호")
        private Long contentTypeId; // 관광 타입 번호
        @Schema(description = "평점")
        private Long rating;    // 평점
        @Schema(description = "유저 ID")
        private Long accountId;

        public static RatingDTO.Response fromEntity(Rating rating) {
            RatingDTO.Response response = new RatingDTO.Response();
            response.setContentId(rating.getContentId());
            response.setContentTypeId(rating.getContentTypeId());
            response.setRating(rating.getRating());
            response.setAccountId(rating.getAccountId());

            return response;
        }
    }

    private Request request;
    private Response response;

    public RatingDTO(RatingDTO.Request request) { this.request = request; }


    public Rating toEntity() {
        return Rating.builder()
                .contentId(request.getContentId())
                .contentTypeId(request.getContentTypeId())
                .rating(request.getRating())
                .accountId(request.getAccountId())
                .build();
    }
}
