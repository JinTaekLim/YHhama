package com.YH.yeohaenghama.domain.report.dto;

import com.YH.yeohaenghama.domain.diary.dto.CommentDTO;
import com.YH.yeohaenghama.domain.diary.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "ReportCommentDTO")
public class ReportCommentDTO {

    @Schema(description = "유저ID")
    private Long accountId;
    @Schema(description = "댓글ID")
    private Long commentId;

    @Data
    public static class Request{
        @Schema(description = "댓글 ID")
        private Long comment;
        @Schema(description = "댓글 작성자 ID")
        private Long account;
        @Schema(description = "해당 일기 ID")
        private Long diary;
        @Schema(description = "댓글 작성자 닉네임")
        private String nickName;
        @Schema(description = "댓글 작성자 프로필 사진 URL")
        private String photoURL;
        @Schema(description = "내용")
        private String content;
        @Schema(description = "댓글 작성 일자")
        private LocalDateTime date;
    }

    @Data
    public static class Response{
        @Schema(description = "댓글 ID")
        private Long comment;
        @Schema(description = "댓글 작성자 ID")
        private Long account;
        @Schema(description = "해당 일기 ID")
        private Long diary;
        @Schema(description = "댓글 작성자 닉네임")
        private String nickName;
        @Schema(description = "댓글 작성자 프로필 사진 URL")
        private String photoURL;
        @Schema(description = "내용")
        private String content;
        @Schema(description = "댓글 작성 일자")
        private LocalDateTime date;
        @Schema(description = "신고당한 횟수")
        private int reportCount;


        public static Response fromEntity(Comment comment,int reportCount) {
            Response response = new Response();
            response.setComment(comment.getId());
            response.setAccount(comment.getAccount().getId());
            response.setDiary(comment.getDiary().getId());
            response.setNickName(comment.getAccount().getNickname());
            response.setPhotoURL(comment.getAccount().getPhotoUrl());
            response.setContent(comment.getContent());
            response.setDate(comment.getDate());
            response.setReportCount(reportCount);
            return response;
        }
    }

}
