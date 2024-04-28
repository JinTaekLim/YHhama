package com.YH.yeohaenghama.domain.itinerary.dto;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.ItineraryJoinAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class ItineraryJoinAccountDTO {
    @Data
    @Schema(name = "ItineraryJoinAccountDTO_Request")
    public static class Request{
        private Long accountId;
        private Long itineraryId;
    }

    public ItineraryJoinAccount add(Account account, Itinerary itinerary){
        ItineraryJoinAccount itineraryJoinAccount = ItineraryJoinAccount.builder()
                .itinerary(itinerary)
                .account(account)
                .build();
        return itineraryJoinAccount;
    }
}
