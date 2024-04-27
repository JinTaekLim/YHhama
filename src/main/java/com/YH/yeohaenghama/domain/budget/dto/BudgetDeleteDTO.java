package com.YH.yeohaenghama.domain.budget.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class BudgetDeleteDTO {
    @Data
    @Schema(name = "BudgetDeleteDTO_Reqeust")
    public static class Request{
        @Schema(description = "가계부ID")
        private Long budgetId;
    }
}
