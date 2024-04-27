package com.YH.yeohaenghama.domain.diary.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.entity.DiaryPhotoUrl;
import com.YH.yeohaenghama.domain.review.dto.ReviewDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class DiaryShowDTO {
    @Data
    public static class Response {
        @Schema(description = "일정 ID")
        private Long itinerary;
        @Schema(description = "태그")
        private List<String> tag;
        @Schema(description = "일기 작성 일시")
        private LocalDateTime date;
        @Schema(description = "일기 제목")
        private String title;
        @Schema(description = "일기 내용")
        private String content;
        @Schema(description = "일기 사진 URL")
        private List<String> photos;
        @Schema(description = "일기 작성자 정보")
        private AccountShowDTO.Response account;

        @Schema(description = "리뷰")
        private Map<String, List<ReviewDTO.Response>> reviews;

        public static Response fromEntity(Diary diary, Map<String, List<ReviewDTO.Response>> reviews,Account account) {
            Response response = new Response();
            response.setItinerary(diary.getItinerary());
            response.setDate(diary.getDate());
            response.setTitle(diary.getTitle());
            response.setContent(diary.getContent());

            List<String> photoURLs = new ArrayList<>();
            List<DiaryPhotoUrl> diaryPhotoUrls = diary.getDiaryPhotoUrls();
            for (DiaryPhotoUrl photoUrl : diaryPhotoUrls) {
                photoURLs.add(photoUrl.getPhotoURL());
            }
            response.setPhotos(photoURLs);

            AccountShowDTO.Response accountShowDTO = new AccountShowDTO.Response(account.getId(), account.getNickname(), account.getPhotoUrl());
            response.setAccount(accountShowDTO);



            response.setReviews(reviews);


            return response;
        }
    }
    @Data
    public static class AccountResponse {
        @Schema(description = "일정 ID")
        private Long itinerary;
        @Schema(description = "태그")
        private List<String> tag;
        @Schema(description = "일기 작성 일시")
        private LocalDateTime date;
        @Schema(description = "일기 제목")
        private String title;
        @Schema(description = "일기 내용")
        private String content;
        @Schema(description = "장소 갯수")
        private Integer placeLength;
        @Schema(description = "일기 사진 URL")
        private List<String> photos;
        @Schema(description = "작성자 정보")
        private AccountShowDTO.Response accountShowDTO;

        public static AccountResponse fromEntity(Diary diary, Account account,Integer placeLength) {
            AccountResponse response = new AccountResponse();
            response.setItinerary(diary.getItinerary());
            response.setDate(diary.getDate());
            response.setTitle(diary.getTitle());
            response.setContent(diary.getContent());
            response.setPlaceLength(placeLength);

            AccountShowDTO.Response accountShowDTO = new AccountShowDTO.Response(account.getId(),account.getNickname(),account.getPhotoUrl());
            response.setAccountShowDTO(accountShowDTO);

            List<String> photoURLs = new ArrayList<>();
            List<DiaryPhotoUrl> diaryPhotoUrls = diary.getDiaryPhotoUrls();
            for (DiaryPhotoUrl photoUrl : diaryPhotoUrls) {
                photoURLs.add(photoUrl.getPhotoURL());
            }
            response.setPhotos(photoURLs);


            return response;
        }
    }
}
