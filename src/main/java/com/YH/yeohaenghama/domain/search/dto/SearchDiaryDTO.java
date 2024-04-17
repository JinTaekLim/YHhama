package com.YH.yeohaenghama.domain.search.dto;

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


    public static SearchDiaryDTO fromEntity(Diary diary) {
        SearchDiaryDTO searchDiaryDTO = new SearchDiaryDTO();
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


        return searchDiaryDTO;
    }
}
