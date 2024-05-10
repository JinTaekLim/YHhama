package com.YH.yeohaenghama.domain.diary.dto;


import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.review.entity.Review;
import com.YH.yeohaenghama.domain.review.entity.ReviewPhotoURL;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class DiaryShowReviewlDTO {
    @Data
    public static class Response{
        @Schema(description = "ID")
        private Long reviewId;
        @Schema(description = "평점")
        private Long rating;    // 평점
        @Schema(description = "리뷰 내용")
        private String content; // 리뷰 내용
        @Schema(description = "사진 URL")
        private List<String> reviewPhotoURLList;

        public static DiaryShowReviewlDTO.Response fromEntity(Review review) {
            DiaryShowReviewlDTO.Response response = new DiaryShowReviewlDTO.Response();
            response.setReviewId(review.getId());
            response.setRating(review.getRating());
            response.setContent(review.getContent());

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
