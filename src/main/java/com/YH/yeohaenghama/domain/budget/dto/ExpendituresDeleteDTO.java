package com.YH.yeohaenghama.domain.budget.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class ExpendituresDeleteDTO {
    @Data @Schema(name ="ExpendituresDeleteDTO_RequestDeleteOne")
    public static class RequestDeleteOne{
        @Schema(description = "지출 금액 ID")
        private Long id;
    }
    @Data @Schema(name ="ExpendituresDeleteDTO_RequestDay")
    public static class RequestDeleteDay{
        @Schema(description = "날짜")
        private Integer day;
    }

    @Data @Schema(name ="ExpendituresDeleteDTO_RequestBudget")
    public static class RequestDeleteBudget{
        @Schema(description = "가계부 ID")
        private Long BudgetId;
    }
}
