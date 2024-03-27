package com.YH.yeohaenghama.domain.review.dto;

import com.YH.yeohaenghama.domain.review.entity.Review;
import com.YH.yeohaenghama.domain.review.entity.ReviewPhotoURL;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewDTO {

    @Data
    @Schema(name = "ReviewDTO_Show")
    public static class Show{
        @Schema(description = "유저 번호")
        private Long accountId;
        @Schema(description = "장소 번호")
        private Long contentId;     // 장소 번호
        @Schema(description = "관광 타입 번호")
        private Long contentTypeId; // 관광 타입 번호
    }
    @Data
    @Schema(name = "ReviewDTO_Request")
    public static class Request{
        @Schema(description = "장소 번호")
        private Long contentId;     // 장소 번호
        @Schema(description = "관광 타입 번호")
        private Long contentTypeId; // 관광 타입 번호
        @Schema(description = "평점")
        private Long rating;    // 평점
        @Schema(description = "리뷰 내용")
        private String content; // 리뷰 내용
        @Schema(description = "유저 Id")
        private Long accountId; //　유저 ID
        @Schema(description = "사진")
        private List<MultipartFile> photos;
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
        @JsonIgnore
        @Schema(description = "유저 ID")
        private Long accountId;

        public static ReviewDTO.Response fromEntity(Review review) {
            ReviewDTO.Response response = new ReviewDTO.Response();
            response.setContentId(review.getContentId());
            response.setContentTypeId(review.getContentTypeId());
            response.setRating(review.getRating());
            response.setContent(review.getContent());
            response.setAccountId(review.getAccountId());



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

    private Request request;
    private Response response;

    public ReviewDTO(ReviewDTO.Request request) { this.request = request; }


    public Review toEntity() {
        return Review.builder()
                .contentId(request.getContentId())
                .contentTypeId(request.getContentTypeId())
                .rating(request.getRating())
                .content(request.getContent())
                .accountId(request.getAccountId())
                .build();
    }
}
