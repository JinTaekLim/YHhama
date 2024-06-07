package com.YH.yeohaenghama.domain.budget.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.budget.entity.ExpendituresGroup;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceShowExpendituresDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExpendituresGroupShowDTO {
    public static Integer totalAmount;
    @Data @Schema(name = "ExpendituresGroupShowDTO_Response")
    public static class Response{
        private Long id;
        private AccountShowDTO.Response accountShowDTO;
        private Integer amount;

        public static Response toEntity(ExpendituresGroup expendituresGroup){
            Response response = new Response();
            response.setId(expendituresGroup.getId());
            response.setAmount(expendituresGroup.getAmount());
            Account account = expendituresGroup.getAccount();
            response.setAccountShowDTO(new AccountShowDTO.Response(account.getId(),account.getNickname(), account.getPhotoUrl(), account.getRole()));

            return response;
        }

        public static List<Response> calculate(List<ExpendituresGroup> expendituresGroupList){
            List<Response> response = new ArrayList<>();
            totalAmount = 0;
            for(ExpendituresGroup expendituresGroup : expendituresGroupList){
                response.add(toEntity(expendituresGroup));
                totalAmount += expendituresGroup.getAmount();
            }
            return response;
        }


    }
}
