package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.budget.entity.ExpendituresGroup;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class ExpendituresGroupAddDTO {
    @Data
    @Schema(name = "ExpendituresGroupAddDTO_Request")
    public static class Request{
        private Long itineraryId;
        private Long accountId;
        private Long place = null;
        private Integer day = null;
        private String paymentMethod;
        private String category;
        private String name;
        private Integer amount;
    }

    private ExpendituresGroupAddDTO.Request request;

    public ExpendituresGroupAddDTO(ExpendituresGroupAddDTO.Request request) {
        this.request = request;
    }

    public ExpendituresGroup toEntity() {
        ExpendituresGroup.ExpendituresGroupBuilder builder = ExpendituresGroup.builder()
                .day(request.getDay())
                .paymentMethod(request.getPaymentMethod())
                .category(request.getCategory())
                .name(request.getName())
                .amount(request.getAmount());
        return builder.build();
    }


}
