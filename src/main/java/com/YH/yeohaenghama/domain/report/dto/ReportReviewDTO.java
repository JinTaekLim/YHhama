package com.YH.yeohaenghama.domain.report.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.diary.dto.CommentDTO;
import com.YH.yeohaenghama.domain.review.dto.ReviewDTO;
import com.YH.yeohaenghama.domain.review.entity.Review;
import com.YH.yeohaenghama.domain.review.entity.ReviewPhotoURL;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReportReviewDTO {
    @Schema(description = "유저ID")
    private Long accountId;
    @Schema(description = "리뷰ID")
    private Long reviewId;

    @Data
    public static class Response {

        @Schema(description = "장소 번호")
        private Long contentId;     // 장소 번호
        @Schema(description = "관광 타입 번호")
        private Long contentTypeId; // 관광 타입 번호
        @Schema(description = "평점")
        private Long rating;    // 평점
        @Schema(description = "리뷰 내용")
        private String content; // 리뷰 내용
        @Schema(description = "유저 ID")
        private AccountShowDTO.Response account;
        @Schema(description = "신고당한 횟수")
        private Integer reportCount;

        public static ReportReviewDTO.Response fromEntity(Review review, Account account, Integer reportCount) {
            ReportReviewDTO.Response response = new ReportReviewDTO.Response();
            response.setContentId(review.getContentId());
            response.setContentTypeId(review.getContentTypeId());
            response.setRating(review.getRating());
            response.setContent(review.getContent());
            AccountShowDTO.Response accountShow = new AccountShowDTO.Response(account.getId(), account.getNickname(), account.getPhotoUrl(), account.getRole());
            response.setAccount(accountShow);
            response.setReportCount(reportCount);


            return response;
        }
    }
}
