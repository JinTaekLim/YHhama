//package com.YH.yeohaenghama.domain.budget.dto;
//
//import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
//import com.YH.yeohaenghama.domain.itinerary.dto.PlaceShowExpendituresDTO;
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.Data;
//
//public class calculateIndividualDTO {
//    @Data @Schema(name = "calculateExpendituresDTO_Response")
//    public static class Response{
//        private Long id;
//        private PlaceShowExpendituresDTO.Response place = null;
//        private Integer day;
//        private String content;
//        private String paymentMethod;
//        private String category;
//        private String name;
//        private Integer amount;
//
//        public static calculateIndividualDTO.Response fromEntity(Expenditures expenditures){
//            calculateIndividualDTO.Response response = new calculateIndividualDTO.Response();
//            response.setId(expenditures.getId());
//            response.setDay(expenditures.getDay());
//            if(expenditures.getPlace() != null) {
//                response.setPlace(PlaceShowExpendituresDTO.Response.fromEntity(expenditures.getPlace()));
//                response.setDay(expenditures.getPlace().getDay());
//            }
//
//            response.setContent(expenditures.getContent());
//            response.setPaymentMethod(expenditures.getPaymentMethod());
//            response.setCategory(expenditures.getCategory());
//            response.setName(expenditures.getName());
//            response.setAmount(expenditures.getAmount());
//
//            return response;
//        }
//
//
//    }
//
//}
