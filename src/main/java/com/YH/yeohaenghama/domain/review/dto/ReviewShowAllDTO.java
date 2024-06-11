package com.YH.yeohaenghama.domain.review.dto;


import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.addPlace.entity.AddPlace;
import com.YH.yeohaenghama.domain.review.entity.Review;
import com.YH.yeohaenghama.domain.review.entity.ReviewPhotoURL;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jsoup.Jsoup;

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
        @Schema(description = "리뷰 ID")
        private Long reviewId;
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
        @Schema(description = "blog URL")
        private String blogURL;    // 리뷰 사진 URL
        @Schema(description = "유저 정보")
        private AccountShowDTO.Response account;

        public static ReviewShowAllDTO.Response fromEntity(Review review, AccountShowDTO.Response account) {
            ReviewShowAllDTO.Response response = new ReviewShowAllDTO.Response();
            response.setReviewId(review.getId());
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

        public static List<ReviewShowAllDTO.Response> pasing(String result) {
            List<ReviewShowAllDTO.Response> response = new ArrayList<>();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(result.toString(), JsonObject.class);
            JsonArray items = jsonObject.getAsJsonArray("items");

            System.out.println(result);
            for (int i = 0; i < items.size(); i++) {
                ReviewShowAllDTO.Response reviewShow = new Response();

                JsonObject item = items.get(i).getAsJsonObject();
//                String title = item.get("title").getAsString();
                String blogName = item.get("bloggername").getAsString();
                String description = item.get("description").getAsString();
                String blogURL = item.get("link").getAsString();
                description = Jsoup.parse(description).text();

                reviewShow.setAccount(new AccountShowDTO.Response(null,blogName,null,null));
                reviewShow.setContentTypeId(80L);
                reviewShow.setContent(description);
                reviewShow.setBlogURL(blogURL);

                response.add(reviewShow);
            }


            return response;
        }
    }
}
