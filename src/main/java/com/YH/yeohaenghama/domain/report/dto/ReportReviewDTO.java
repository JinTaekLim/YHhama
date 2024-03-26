package com.YH.yeohaenghama.domain.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReportReviewDTO {
    @Schema(description = "유저ID")
    private Long accountId;
    @Schema(description = "리뷰ID")
    private Long reviewId;
}
