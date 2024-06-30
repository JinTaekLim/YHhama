package com.YH.yeohaenghama.domain.shorts.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.shorts.entity.ShortsComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadCommentDTO {

    @Data @Schema(name = "ReadCommentDTO_Request")
    public static class Request{
        private Long shortsId;
    }

    @Data @Schema(name = "ReadCommentDTO_Response")
    public static class Response{
        private Long commentId;
        private AccountShowDTO.Response account;
        private String comment;
        private LocalDateTime date;

        public static ReadCommentDTO.Response toEntity(ShortsComment shortsComment){
            ReadCommentDTO.Response response = new ReadCommentDTO.Response();
            response.setCommentId(shortsComment.getId());
            response.setAccount(new AccountShowDTO.Response().toEntity(shortsComment.getAccount()));
            response.setComment(shortsComment.getComment());
            response.setDate(shortsComment.getDate());
            return response;
        }
    }

    @Data @Schema(name = "ReadCommentDTO_AllResponse")
    public static class AllResponse{
        private List<Response> comment;

        public AllResponse(List<ShortsComment> shortsComments){
            List<Response> response = new ArrayList<>();
            for(ShortsComment comment : shortsComments){
                response.add(Response.toEntity(comment));
            }
            this.comment = response;
        }
    }
}
