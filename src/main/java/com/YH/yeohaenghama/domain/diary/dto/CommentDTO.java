package com.YH.yeohaenghama.domain.diary.dto;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.diary.entity.Comment;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "CommentDTO")
public class CommentDTO {

    @Data
    @Schema(name = "CommentDTO_Update")
    public static class Update{
        @Schema(description = "내용", example = "수정할 내용")
        private String content;
        @Schema(description = "수정하는 계정 ID")
        private Account account;
        @Schema(description = "수정하는 댓글 ID")
        private Comment comment;
    }
    @Data
    @Schema(name = "CommentDTO_Request")
    public static class Request{
        @Schema(description = "댓글 작성자 ID")
        private Long account;
        @Schema(description = "해당 일기 ID")
        private Long diary;
        @Schema(description = "내용")
        private String content;
    }

    @Data
    @Schema(name = "CommentDTO_Response")
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



        public static CommentDTO.Response fromEntity(Comment comment) {
            CommentDTO.Response response = new CommentDTO.Response();
            response.setComment(comment.getId());
            response.setAccount(comment.getAccount().getId());
            response.setDiary(comment.getDiary().getId());
            response.setNickName(comment.getAccount().getNickname());
            response.setPhotoURL(comment.getAccount().getPhotoUrl());
            response.setContent(comment.getContent());
            response.setDate(comment.getDate());
            return response;
        }
    }


    private Request request;
    private Response response;

    public CommentDTO(CommentDTO.Request request) {
        this.request = request;
    }

    public Comment toEntity(Account account, Diary diary) {
        Comment comment = Comment.builder()
                .account(account)
                .diary(diary)
                .content(request.getContent())
                .date(LocalDateTime.now())
                .build();
        return comment;

    }
}
