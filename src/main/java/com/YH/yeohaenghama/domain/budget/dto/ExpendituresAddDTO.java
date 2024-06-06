package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.budget.entity.ExpendituresGroup;
import com.YH.yeohaenghama.domain.budget.entity.ExpendituresSharedAccount;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceShowExpendituresDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExpendituresAddDTO {

    private Place place;

    @Data @Schema(name = "ExpendituresAddDTO_Request")
    public static class Request{
        private Long expendituresId;
        private Long budgetId;
        private List<Long> payerId;
        private List<Integer> amount;
        private List<Long> accountId;
        private Long place = null;
        private Integer day = null;
        private String paymentMethod;
        private String content;
        private String category;
        private boolean individual;
    }
    @Data @Schema(name = "ExpendituresAddDTO_Response")
    public static class Response{
        private Long id;
//        private AccountShowDTO.Response account;
        @JsonIgnore
        private Budget budget;
        @JsonIgnore
        private Place placeEntity = null;
        private PlaceShowExpendituresDTO.Response place = null;
        private int day;
        private String content;
        private String paymentMethod;
        private String category;
        private String name;
        private boolean individual;
        public static ExpendituresAddDTO.Response fromEntity(Expenditures expenditures){
            ExpendituresAddDTO.Response response = new ExpendituresAddDTO.Response();
            response.setId(expenditures.getId());
//            response.setAccount(accountShow(expenditures.getAccount()));
            response.setBudget(expenditures.getBudget());
//            if(place != null){
//                response.setPlaceEntity(place);
//                PlaceShowExpendituresDTO.Response placeResponse = PlaceShowExpendituresDTO.Response.fromEntity(place);
//                response.setPlace(placeResponse);
//            }
            response.setContent(expenditures.getContent());
            response.setPaymentMethod(expenditures.getPaymentMethod());
            response.setCategory(expenditures.getCategory());
            response.setIndividual(expenditures.isIndividual());

            return response;
        }
//        public ExpendituresAddDTO.Response setPlace(Place place) {
//            this.placeEntity = place;
//            PlaceShowExpendituresDTO.Response placeResponse = PlaceShowExpendituresDTO.Response.fromEntity(place);
//            this.place = placeResponse;
//            return this;
//        }


    }

    private ExpendituresAddDTO.Request request;
    private ExpendituresAddDTO.Response response;

    public ExpendituresAddDTO(Request request) {
        this.request = request;
    }

//    public ExpendituresAddDTO(Response response) {
//        this.response = response;
//    }


    public Expenditures toEntity(Budget budget,List<Account> payerList,List<Account> accountList) {
        Expenditures.ExpendituresBuilder expendituresBuilder = Expenditures.builder()
                .budget(budget)
                .day(request.getDay())
                .paymentMethod(request.getPaymentMethod())
                .content(request.getContent())
                .category(request.getCategory())
                .individual(request.isIndividual());

        if (place != null) {
            expendituresBuilder.place(place);
        }

//        expendituresBuilder.expendituresGroups(toEntityExpendituresGroup(accountList,expendituresBuilder.build()));

        Expenditures expenditures = expendituresBuilder.build();

        List<ExpendituresGroup> expendituresGroups = toEntityExpendituresGroup(payerList, expenditures);
        expenditures.setExpendituresGroups(expendituresGroups);
        List<ExpendituresSharedAccount> expendituresSharedAccounts = toEntityExpendituresSharedAccount(accountList, expenditures);
        expenditures.setExpendituresSharedAccounts(expendituresSharedAccounts);

        return expenditures;
    }

    public List<ExpendituresGroup> toEntityExpendituresGroup(List<Account> accountList,Expenditures expenditures){
        List<ExpendituresGroup> response = new ArrayList<>();

        for(int i=0; i<accountList.size(); i++){
            ExpendituresGroup.ExpendituresGroupBuilder expendituresGroupBuilder = ExpendituresGroup.builder()
                    .expenditures(expenditures)
                    .account(accountList.get(i))
                    .amount(request.getAmount().get(i));
               response.add(expendituresGroupBuilder.build());
        }

        return response;
    }

    public List<ExpendituresSharedAccount> toEntityExpendituresSharedAccount(List<Account> sharedAccountList,Expenditures expenditures){
        List<ExpendituresSharedAccount> response = new ArrayList<>();

        for(Account account : sharedAccountList){
            ExpendituresSharedAccount expendituresSharedAccount = ExpendituresSharedAccount.builder()
                    .account(account)
                    .expenditures(expenditures)
                    .build();

            response.add(expendituresSharedAccount);
        }

        return response;
    }




    public static AccountShowDTO.Response accountShow(Account account){
        AccountShowDTO.Response response = new AccountShowDTO.Response(account.getId(), account.getNickname(), account.getPhotoUrl(), account.getRole());
        return response;
    }
}
