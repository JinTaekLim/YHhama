package com.YH.yeohaenghama.domain.budget.dto;


import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.diary.dto.CommentDTO;
import com.YH.yeohaenghama.domain.diary.entity.Comment;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class BudgetCreateDTO {
    @Data
    @Schema(name = "BudgetCreateDTO_Reqeust")
    public static class Request{
        @Schema(description = "일정ID")
        private Long itineraryId;
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

        public static BudgetCreateDTO.Response fromEntity(Budget budget) {
            BudgetCreateDTO.Response response = new BudgetCreateDTO.Response();
            response.setId(budget.getId());
            response.setItineraryId(budget.getItinerary().getId());
            response.setAccountId(budget.getItinerary().getAccount().getId());
            response.setTotalAmount(budget.getTotalAmount());
            return response;
        }
    }


    private Request request;

//    public CommentDTO(CommentDTO.Request request) {
//        this.request = request;
//    }

    public BudgetCreateDTO(BudgetCreateDTO.Request request) {
        this.request = request;
    }

    public Budget toEntity(Itinerary itinerary) {
        Budget budget = Budget.builder()
                .itinerary(itinerary)
                .totalAmount(request.getTotalAmount())
                .build();
        return budget;

    }
}
