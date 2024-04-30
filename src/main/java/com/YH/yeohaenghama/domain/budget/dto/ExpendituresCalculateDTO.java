package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

public class ExpendituresCalculateDTO {
    @Data @Schema(name = "expendituresCalculate_Request")
    public static class Request{
        private Long budgetId;
    }
    @Data
    public static class Response{
        private Long budgetId;
        private Integer totalAmount;
        private List<ExpendituresShowDTO.Response> expendituresShowDTOList;

    }
}
