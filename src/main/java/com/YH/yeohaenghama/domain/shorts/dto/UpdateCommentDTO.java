package com.YH.yeohaenghama.domain.shorts.dto;

import com.YH.yeohaenghama.domain.shorts.entity.ShortsComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class UpdateCommentDTO {
    @Data @Schema(name = "UpdateCommentDTO_Request")
    public static class Request{
        private Long commentId;
        private Long accountId;
        private String comment;
    }

    public ShortsComment update(ShortsComment shortsComment, Request req) {
        return ShortsComment.builder()
                .id(req.getCommentId())
                .account(shortsComment.getAccount())
                .shorts(shortsComment.getShorts())
                .comment(req.getComment())
                .build();
    }
}
