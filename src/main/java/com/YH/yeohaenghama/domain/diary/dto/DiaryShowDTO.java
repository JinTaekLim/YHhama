package com.YH.yeohaenghama.domain.diary.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.entity.DiaryPhotoUrl;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.review.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class DiaryShowDTO {
    @Data
    @Schema(name = "DiaryShowDTO_Response")
    public static class Response {
        @Schema(description = "일기 ID")
        private Long id;
        @Schema(description = "일기 태그")
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
        @Schema(description = "일기에 포함된 일정")
        private DiaryItineraryShowDTO.Response itinerary;

        public static Response fromEntity(Diary diary, Itinerary itinerary, List<Review> review) {
            Response response = new Response();
            response.setId(diary.getId());
            response.setDate(diary.getDate());
            response.setTitle(diary.getTitle());
            response.setContent(diary.getContent());
            response.setPhotos(getPhotoUrls(diary.getDiaryPhotoUrls()));
            response.setAccount(getAccountInfo(itinerary.getAccount()));
            response.setItinerary(DiaryItineraryShowDTO.Response.fromEntity(itinerary,review));
            return response;
        }

        private static List<String> getPhotoUrls(List<DiaryPhotoUrl> diaryPhotoUrls) {
            List<String> photoURLs = new ArrayList<>();
            for (DiaryPhotoUrl photoUrl : diaryPhotoUrls) {
                photoURLs.add(photoUrl.getPhotoURL());
            }
            return photoURLs;
        }

        private static AccountShowDTO.Response getAccountInfo(Account account) {
            return new AccountShowDTO.Response(
                    account.getId(),
                    account.getNickname(),
                    account.getPhotoUrl(),
                    account.getRole()
            );
        }
    }

    @Data
    @Schema(name = "DiaryShowDTO_AccountResponse")
    public static class AccountResponse {
        @Schema(description = "일정 ID")
        private Long itineraryId;
        @Schema(description = "일기 ID")
        private Long diaryId;
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

        public static AccountResponse fromEntity(Diary diary, Account account, Integer placeLength) {
            AccountResponse response = new AccountResponse();
            response.setItineraryId(diary.getItinerary());
            response.setDiaryId(diary.getId());
            response.setDate(diary.getDate());
            response.setTitle(diary.getTitle());
            response.setContent(diary.getContent());
            response.setPlaceLength(placeLength);
            response.setAccountShowDTO(getAccountInfo(account));
            response.setPhotos(getPhotoUrls(diary.getDiaryPhotoUrls()));
            return response;
        }

        private static List<String> getPhotoUrls(List<DiaryPhotoUrl> diaryPhotoUrls) {
            List<String> photoURLs = new ArrayList<>();
            for (DiaryPhotoUrl photoUrl : diaryPhotoUrls) {
                photoURLs.add(photoUrl.getPhotoURL());
            }
            return photoURLs;
        }

        private static AccountShowDTO.Response getAccountInfo(Account account) {
            return new AccountShowDTO.Response(
                    account.getId(),
                    account.getNickname(),
                    account.getPhotoUrl(),
                    account.getRole()
            );
        }
    }
}
