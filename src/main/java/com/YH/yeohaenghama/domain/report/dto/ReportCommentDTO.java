package com.YH.yeohaenghama.domain.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReportCommentDTO {

    @Schema(description = "유저ID")
    private Long accountId;
    @Schema(description = "댓글ID")
    private Long commentId;


}
