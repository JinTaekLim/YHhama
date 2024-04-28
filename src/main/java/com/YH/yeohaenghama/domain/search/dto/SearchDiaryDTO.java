package com.YH.yeohaenghama.domain.search.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.diary.dto.DiaryShowDTO;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.entity.DiaryPhotoUrl;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
//@Schema(description = "SearchDiaryDTO")
public class SearchDiaryDTO {
    @Data

    public static class Response{
        private List<SearchDiaryDTO> searchDiaryDTOS;
        private Integer pageNum;
        private Integer totalPage;
    }


    @Schema(description = "일기 ID")
    private Long diary;
    @Schema(description = "일정 ID")
    private Long itinerary;
    @Schema(description = "일기 작성 일시")
    private String date;
    @Schema(description = "일기 제목")
    private String title;
    @Schema(description = "일기 내용")
    private String content;
    @Schema(description = "일기 사진 URL")
    private List<String> photos;
    @Schema(description = "작성자 정보")
    private AccountShowDTO.Response account;


    public static SearchDiaryDTO fromEntity(Diary diary, Account account) {
        SearchDiaryDTO searchDiaryDTO = new SearchDiaryDTO();
        searchDiaryDTO.setDiary(diary.getId());
        searchDiaryDTO.setItinerary(diary.getItinerary());
        searchDiaryDTO.setDate(String.valueOf(diary.getDate()));
        searchDiaryDTO.setTitle(diary.getTitle());
        searchDiaryDTO.setContent(diary.getContent());

        List<String> photoURLs = new ArrayList<>();
        List<DiaryPhotoUrl> diaryPhotoUrls = diary.getDiaryPhotoUrls();
        for (DiaryPhotoUrl photoUrl : diaryPhotoUrls) {
            photoURLs.add(photoUrl.getPhotoURL());
        }
        searchDiaryDTO.setPhotos(photoURLs);

        AccountShowDTO.Response accountResponse = new AccountShowDTO.Response(account.getId(),account.getNickname(), account.getPhotoUrl(),account.getRole());
        searchDiaryDTO.setAccount(accountResponse);


        return searchDiaryDTO;
    }
}
