package com.YH.yeohaenghama.domain.chatAI.dto;

import com.YH.yeohaenghama.domain.diary.entity.Diary;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class ChatAIDiary {

    @Schema(name = "ChatAIDiary_Response") @Data
    public static class Response{
        private String keyword;
        private List<DiaryShow> diary;

        public static Response toEntity(List<Diary> diaryList,String keyword){
            List<DiaryShow> responseDiary = new ArrayList<>();
            for(Diary diary : diaryList){
                responseDiary.add(DiaryShow.toEntity(diary));
            }
            Response response = new Response();
            response.setDiary(responseDiary);
            response.setKeyword(keyword);
            return response;
        }
    }

    @Data
    public static class DiaryShow{
        private Long diaryId;
        private String title;

        public static DiaryShow toEntity(Diary diary){
            DiaryShow diaryShow = new DiaryShow();
            diaryShow.setDiaryId(diary.getId());
            diaryShow.setTitle(diary.getTitle());
            return diaryShow;
        }
    }
}
