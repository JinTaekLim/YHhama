package com.YH.yeohaenghama.domain.budget.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class ExpendituresDeleteDTO {
    @Data @Schema(name ="ExpendituresDeleteDTO_Request")
    public static class RequestDeleteOne{
        @Schema(description = "지출 금액 ID")
        private Long id;
    }
}
