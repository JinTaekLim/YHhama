package com.YH.yeohaenghama.domain.review.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.review.entity.Review;
import com.YH.yeohaenghama.domain.review.entity.ReviewPhotoURL;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(name = "ReviewAccountShowDTO")
public class ReviewAccountShowDTO {

    @Data
    @Schema(name = "ReviewAccountShowDTO_Request")
    public static class Request {
        private Long accountId;
    }

    @Data
    public static class Response {
        @Schema(description = "장소 번호")
        private Long reviewId;
        @Schema(description = "장소명")
        private String placeName;
        @Schema(description = "평점")
        private Long rating;    // 평점
        @Schema(description = "리뷰 내용")
        private String content; // 리뷰 내용
        @Schema(description = "작성 일시")
        private LocalDateTime date;
        @Schema(description = "사진 URL")
        private List<String> reviewPhotoURLList;    // 리뷰 사진 URL

        public static ReviewAccountShowDTO.Response fromEntity(Review review) {
            ReviewAccountShowDTO.Response response = new ReviewAccountShowDTO.Response();

            response.setReviewId(review.getId());
            response.setPlaceName(review.getPlaceName());
            response.setRating(review.getRating());
            response.setContent(review.getContent());
            response.setDate(review.getDate());

            List<String> photoURLs = new ArrayList<>();
            List<ReviewPhotoURL> reviewPhotoURLs = review.getReviewPhotoURLS();
            if (reviewPhotoURLs != null) {
                for (ReviewPhotoURL photoURL : reviewPhotoURLs) {
                    photoURLs.add(photoURL.getPhotoUrl());
                }
            }
            response.setReviewPhotoURLList(photoURLs);


            return response;
        }
    }
}
