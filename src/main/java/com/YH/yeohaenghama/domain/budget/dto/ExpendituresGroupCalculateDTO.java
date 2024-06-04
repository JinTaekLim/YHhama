//package com.YH.yeohaenghama.domain.budget.dto;
//
//import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.Data;
//
//import java.util.List;
//
//public class ExpendituresGroupCalculateDTO {
//    @Data @Schema(name = "ExpendituresGroupCalculateDTO_Request")
//    public static class Request{
//        private Long itineraryId;
//        private Long budgetId;
//        private List<Long> accountId;
//        private List<Integer> ratio;
//    }
//    @Data
//    public static class Reponse{
//        private AccountShowDTO.Response account;
//        private Integer amount;
//
//        public static ExpendituresGroupCalculateDTO.Reponse setAccountAndAmount(AccountShowDTO.Response account,Integer amount){
//            ExpendituresGroupCalculateDTO.Reponse response = new ExpendituresGroupCalculateDTO.Reponse();
//            response.setAccount(account);
//            response.setAmount(amount);
//            return response;
//        }
//    }
//}
