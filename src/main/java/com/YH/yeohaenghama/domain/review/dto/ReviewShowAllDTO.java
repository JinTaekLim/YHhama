package com.YH.yeohaenghama.domain.review.dto;


import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.review.entity.Review;
import com.YH.yeohaenghama.domain.review.entity.ReviewPhotoURL;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class ReviewShowAllDTO {
    @Data
    @Schema(name = "ReviewDTO_ShowAll")
    public static class Request{
        @Schema(description = "장소 번호")
        private Long contentId;     // 장소 번호
        @Schema(description = "관광 타입 번호")
        private Long contentTypeId; // 관광 타입 번호
    }

    @Data
    public static class Response{
        @Schema(description = "장소 번호")
        private Long contentId;     // 장소 번호
        @Schema(description = "관광 타입 번호")
        private Long contentTypeId; // 관광 타입 번호
        @Schema(description = "평점")
        private Long rating;    // 평점
        @Schema(description = "리뷰 내용")
        private String content; // 리뷰 내용
        @Schema(description = "사진 URL")
        private List<String> reviewPhotoURLList;    // 리뷰 사진 URL
        @Schema(description = "유저 정보")
        private AccountShowDTO.Response account;

        public static ReviewShowAllDTO.Response fromEntity(Review review, AccountShowDTO.Response account) {
            ReviewShowAllDTO.Response response = new ReviewShowAllDTO.Response();
            response.setContentId(review.getContentId());
            response.setContentTypeId(review.getContentTypeId());
            response.setRating(review.getRating());
            response.setContent(review.getContent());
//            response.setAccountId(review.getAccountId());
            response.setAccount(account);



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
