package com.YH.yeohaenghama.domain.budget.dto;


import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

import javax.annotation.Nullable;

public class BudgetCreateDTO {
    @Data
    @Schema(name = "BudgetCreateDTO_Reqeust")
    public static class Request{
        @Schema(description = "일정ID")
        private Long itineraryId;
        @Schema(description = "사용자ID")
        private Long accountId;
        @Schema(description = "총 예산")
        private Integer totalAmount;
    }

    @Data
    public static class Response{
        @Schema(description = "가계부ID")
        private Long id;
        @Schema(description = "일정ID")
        private Long itineraryId;
        @Schema(description = "사용자ID")
        private Long accountId;
        @Schema(description = "총 예산")
        private Integer totalAmount;
    }
}
