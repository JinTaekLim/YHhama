package com.YH.yeohaenghama.domain.itinerary.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.itinerary.entity.ItineraryJoinAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class ItineraryJoinAccountShowDTO {
    @Data @Schema(name = "ItineraryJoinAccountShowDTO_Request")
    public static class Request{
        private Long itineraryId;
    }
    @Data
    public static class Response{
        private Long id;
        private AccountShowDTO.Response account;

        public static Response fromEntity(ItineraryJoinAccount itineraryJoinAccount){
            ItineraryJoinAccountShowDTO.Response response = new ItineraryJoinAccountShowDTO.Response();
            response.setId(itineraryJoinAccount.getId());

            Account account = itineraryJoinAccount.getAccount();
            AccountShowDTO.Response accountResponse = new AccountShowDTO.Response(account.getId(), account.getNickname(), account.getPhotoUrl(), account.getRole());
            response.setAccount(accountResponse);


            return response;
        }
    }
}
