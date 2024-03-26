package com.YH.yeohaenghama.domain.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReportCountDTO {
    @Schema(description = "신고받는 횟수")
    private Long count;

    public ReportCountDTO(Long count){
        this.count = count;
    }
}
