package com.YH.yeohaenghama.domain.shorts.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.diary.entity.Comment;
import com.YH.yeohaenghama.domain.shorts.entity.Shorts;
import com.YH.yeohaenghama.domain.shorts.entity.ShortsComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

public class CreateCommentDTO {

    @Data
    @Schema(name = "createCommentDTO_Request")
    public static class Request{
        private Long shortsId;
        private Long accountId;
        private String comment;
    }

    @Data @Schema(name = "createCommentDTO_Response")
    public static class Response{
        private Long commentId;
        private AccountShowDTO.Response account;
        private String comment;
        private LocalDateTime date;

        public static Response toEntity(ShortsComment shortsComment){
            Response response = new Response();
            response.setCommentId(shortsComment.getId());
            response.setAccount(new AccountShowDTO.Response().toEntity(shortsComment.getAccount()));
            response.setComment(shortsComment.getComment());
            response.setDate(shortsComment.getDate());
            return response;
        }
    }

    public static ShortsComment fromEntity(Request req, Account account, Shorts shorts){
        return ShortsComment.builder()
                .comment(req.getComment())
                .account(account)
                .shorts(shorts)
                .build();

    }
}
