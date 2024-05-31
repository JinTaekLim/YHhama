package com.YH.yeohaenghama.domain.budget.service;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.budget.dto.*;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.budget.entity.ExpendituresGroup;
import com.YH.yeohaenghama.domain.budget.repository.BudgetRepository;
import com.YH.yeohaenghama.domain.budget.repository.ExpendituresGroupRepository;
import com.YH.yeohaenghama.domain.budget.repository.ExpendituresRepository;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.ItineraryJoinAccount;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryJoinAccountRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpendituresService {
    private final BudgetRepository budgetRepository;
    private final ExpendituresRepository expendituresRepository;
    private final ItineraryRepository itineraryRepository;
    private final ItineraryJoinAccountRepository itineraryJoinAccountRepository;
    private final PlaceRepository placeRepository;
    private final ExpendituresGroupRepository expendituresGroupRepository;
    private final AccountRepository accountRepository;

    public ExpendituresAddDTO.Response expendituresAdd(ExpendituresAddDTO.Request dto){
        Optional<Budget> budgetOpt = budgetRepository.findByItineraryId(dto.getItineraryId());
        if(budgetOpt.isEmpty()) { throw new NoSuchElementException("해당 ID를 가진 가계부가 존재하지 않습니다. "); }
        Optional<Place> placeOpt = null;
        if(dto.getPlace() != null) {
            placeOpt = placeRepository.findById(dto.getPlace());
            if (placeOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 장소가 존재하지 않습니다. ");
        }
        Optional<Account> accountOpt = accountRepository.findById(dto.getAccountId());
        if(accountOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 유저가 존재하지 않습니다.");

        Expenditures expenditures = new ExpendituresAddDTO(dto).toEntity();
        expenditures.setAccount(accountOpt.get());
        expenditures.setBudget(budgetOpt.get());
        if(dto.getPlace() != null) expenditures.setPlace(placeOpt.get());
        expendituresRepository.save(expenditures);

        ExpendituresAddDTO.Response response = ExpendituresAddDTO.Response.fromEntity(expenditures,accountOpt.get());

        if(placeOpt != null) response.setPlace(placeOpt.get());
        return response;
    }


    public String expendituresGroupAdd(ExpendituresGroupAddDTO.Request dto){
        Optional<ItineraryJoinAccount> itineraryJoinAccountOpt = itineraryJoinAccountRepository.findByItineraryIdAndAccountId(dto.getItineraryId(),dto.getAccountId());

//        itineraryJoinAccountOpt.get().getItinerary().getPlaces().get()
        if(itineraryJoinAccountOpt.isEmpty()) throw new NoSuchElementException("해당 가계부에 속해있는 유저 정보를 찾을 수 없습니다. ");
        Optional<Place> placeOpt = null;
        if(dto.getPlace() != null) {
            placeOpt = placeRepository.findById(dto.getPlace());
            if (placeOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 장소가 존재하지 않습니다. ");
        }

        ExpendituresGroup expendituresGroup = new ExpendituresGroupAddDTO(dto).toEntity();
        expendituresGroup.setItineraryAccount(itineraryJoinAccountOpt.get());
//        expendituresGroup.setBudget(itineraryJoinAccountOpt.get().getItinerary().getBudget());
        if(placeOpt != null){
            expendituresGroup.setPlace(placeOpt.get());
        }

        expendituresGroup.setBudget(budgetRepository.findByItineraryId(dto.getItineraryId()).get());
        expendituresGroupRepository.save(expendituresGroup);
        return "성공";
    }

    public List<ExpendituresShowDTO.Response> expendituresShow(ExpendituresShowDTO.Request dto){
        List<Expenditures> expendituresList = expendituresRepository.findByBudgetId(dto.getBudgetId());
        if(expendituresList.isEmpty()) { throw new NoSuchElementException("해당 가계부에는 존재하는 지출 금액이 없습니다. "); }
        Optional<Account> accountOpt = accountRepository.findById(dto.getAccountId());
        if(accountOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 유저가 존재하지 않습니다.");

        List<ExpendituresShowDTO.Response> response = new ArrayList<>();

        for(Expenditures expenditures : expendituresList){
            if (expenditures.getAccount() == accountOpt.get()) response.add(ExpendituresShowDTO.Response.fromEntity(expenditures));
        }

        return response;
    }

    public List<ExpendituresGroupShowDTO.Response> expendituresGroupAllShow(ExpendituresGroupShowDTO.Request dto){
        List<ExpendituresGroup> expendituresList = expendituresGroupRepository.findByBudgetId(dto.getBudgetId());
        if(expendituresList.isEmpty()) { throw new NoSuchElementException("해당 가계부에는 존재하는 지출 금액이 없습니다. "); }
        List<ExpendituresGroupShowDTO.Response> response = new ArrayList<>();

        for(ExpendituresGroup expendituresGroup : expendituresList){
            response.add(ExpendituresGroupShowDTO.Response.fromEntity(expendituresGroup));
        }

        return response;
    }


    public List<ExpendituresGroupShowDTO.Response> expendituresGroupAccountShow(ExpendituresGroupShowDTO.AccountRequest dto) {
        Optional<ItineraryJoinAccount> itineraryJoinAccountOpt = itineraryJoinAccountRepository.findByItineraryIdAndAccountId(dto.getItineraryId(), dto.getAccountId());
        List<ExpendituresGroup> expendituresList = expendituresGroupRepository.findByItineraryJoinAccountId(itineraryJoinAccountOpt.get().getId());
        if (expendituresList.isEmpty()) {
            throw new NoSuchElementException("해당 가계부에는 존재하는 지출 금액이 없습니다. ");
        }
        List<ExpendituresGroupShowDTO.Response> response = new ArrayList<>();

        for (ExpendituresGroup expendituresGroup : expendituresList) {
            response.add(ExpendituresGroupShowDTO.Response.fromEntity(expendituresGroup));
        }
        return response;
    }

    public ExpendituresCalculateDTO.Response expendituresCalculate(ExpendituresCalculateDTO.Request dto){
        Optional<Budget> budgetOpt = budgetRepository.findById(dto.getBudgetId());
        if(budgetOpt.get().getExpenditures().isEmpty()) { throw new NoSuchElementException("해당 ID를 가진 가계부에 지출 정보가 존재하지 않습니다. "); }

        List<ExpendituresShowDTO.Response> expendituresReponse = new ArrayList<>();
        Integer totalAmount = budgetOpt.get().getTotalAmount();

        for(Expenditures expenditures : budgetOpt.get().getExpenditures()){
            totalAmount -= expenditures.getAmount();
            expendituresReponse.add(ExpendituresShowDTO.Response.fromEntity(expenditures));
        }

        ExpendituresCalculateDTO.Response response = new ExpendituresCalculateDTO.Response();
        response.setBudgetId(budgetOpt.get().getId());
        response.setTotalAmount(totalAmount);
        response.setExpendituresShowDTOList(expendituresReponse);

        return response;
    }

    public List<ExpendituresGroupCalculateDTO.Reponse> expendituresGroupCalculate(ExpendituresGroupCalculateDTO.Request dto){
        List<Long> accountList = dto.getAccountId();
        List<Integer> ratioList = dto.getRatio();

        List<ExpendituresGroup> budgetList = expendituresGroupRepository.findByBudgetId(dto.getBudgetId());



        Integer totalAmount = (int) budgetList.stream()
                .mapToDouble(ExpendituresGroup::getAmount)
                .sum();

        Integer ratioSum = ratioList.stream().mapToInt(Integer::intValue).sum();

        List<ExpendituresGroupCalculateDTO.Reponse> response = new ArrayList<>();

        for(int i=0; i<accountList.size(); i++){

            Optional<ItineraryJoinAccount> itineraryJoinAccountOpt = itineraryJoinAccountRepository.findByItineraryIdAndAccountId(dto.getItineraryId(),accountList.get(i));


            Account account = itineraryJoinAccountOpt.get().getAccount();
            AccountShowDTO.Response accountResponse = new AccountShowDTO.Response(account.getId(), account.getNickname(), account.getPhotoUrl(), account.getRole());

            Integer amount = totalAmount * ratioList.get(i) / ratioSum;


            response.add(ExpendituresGroupCalculateDTO.Reponse.setAccountAndAmount(accountResponse,amount));

        }

        return response;
    }

    public String expendituresDeleteOne(ExpendituresDeleteDTO.RequestDeleteOne dto){
        Optional<Expenditures> expendituresOpt = expendituresRepository.findById(dto.getId());
        if(expendituresOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 지출 금액이 존재하지 않습니다. ");

        expendituresRepository.deleteById(dto.getId());

        return "삭제 완료";
    }

    public String expendituresGroupDeleteOne(ExpendituresDeleteDTO.RequestDeleteOne dto){
        Optional<ExpendituresGroup> expendituresGroupOpt = expendituresGroupRepository.findById(dto.getId());
        if(expendituresGroupOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 지출 금액이 존재하지 않습니다. ");

        expendituresGroupRepository.deleteById(dto.getId());

        return "삭제 완료";
    }

    public String expendituresDeleteDay(ExpendituresDeleteDTO.RequestDeleteDay dto){
        List<Expenditures> expendituresOpt = expendituresRepository.findByDay(dto.getDay());
        if(expendituresOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 지출 금액이 존재하지 않습니다. ");

        expendituresRepository.deleteAll(expendituresOpt);

        return "삭제 완료";
    }

    public String expendituresDeleteBudget(ExpendituresDeleteDTO.RequestDeleteBudget dto){
        List<Expenditures> expendituresOpt = expendituresRepository.findByBudgetId(dto.getBudgetId());
        if(expendituresOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 지출 금액이 존재하지 않습니다. ");

        expendituresRepository.deleteAll(expendituresOpt);

        return "삭제 완료";
    }
}
