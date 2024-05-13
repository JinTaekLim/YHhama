package com.YH.yeohaenghama.domain.diary.dto;

import com.YH.yeohaenghama.domain.diary.entity.Diary;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "CommentShowDTO")
public class CommentShowDTO {
    @Schema(description = "조회할 일기")
    private Long diaryId;

    @Data
    @Schema(name = "CommentShowDTO_Response")
    public static class Response{
        @Schema(description = "댓글")
        private List<CommentDTO.Response> commentShowDTO;
    }
}