package com.YH.yeohaenghama.domain.budget.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.budget.dto.*;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.budget.repository.BudgetAccountRepository;
import com.YH.yeohaenghama.domain.budget.repository.BudgetRepository;
import com.YH.yeohaenghama.domain.budget.repository.ExpendituresRepository;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
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
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final ExpendituresRepository expendituresRepository;
    private final BudgetAccountRepository budgetAccountRepository;
    private final ItineraryRepository itineraryRepository;
    private final AccountRepository accountRepository;
    private final PlaceRepository placeRepository;
    public BudgetCreateDTO.Response budgetCreate(BudgetCreateDTO.Request dto){
        Optional<Itinerary> itineraryOpt = itineraryRepository.findById(dto.getItineraryId());

        if(itineraryOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 일정이 존재하지 않습니다. ");

        BudgetCreateDTO budgetCreateDTO = new BudgetCreateDTO(dto);

        return BudgetCreateDTO.Response.fromEntity(budgetRepository.save(budgetCreateDTO.toEntity(itineraryOpt.get())));
    }

    public void budgetDelete(BudgetDeleteDTO.Request dto){
        if(budgetRepository.findById(dto.getBudgetId()).isEmpty()){
            throw new NoSuchElementException("해당 ID를 가진 가계부가 존재하지 않습니다. ");
        }

        budgetRepository.deleteById(dto.getBudgetId());
    }

    public BudgetShowDTO.Response budgetShow(BudgetShowDTO.Request dto){
        Optional<Itinerary> itineraryOpt = itineraryRepository.findById(dto.getItinerary());
        if(itineraryOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 일정이 존재하지 않습니다. ");
        Optional<Budget> budgetOpt = budgetRepository.findByItinerary(itineraryOpt.get());

        List<Expenditures> expenditures = expendituresRepository.findByBudgetId(budgetOpt.get().getId());

        BudgetShowDTO.Response response = BudgetShowDTO.Response.fromEntity(budgetOpt.get());
        if(expenditures != null) response.setExpenditures(expenditures);

        return response;
    }

    public String budgetAddAccount(BudgetAccountAddDTO.Request dto){
        BudgetAccountAddDTO budgetAccountAddDTO = new BudgetAccountAddDTO();

        Optional<Account> accountOpt = accountRepository.findById(dto.getAccountId());
        Optional<Budget> budgetOpt = budgetRepository.findById(dto.getBudgetId());

        if(accountOpt == null || budgetOpt == null) throw new NoSuchElementException(" 입력된 ID를 가진 정보가 존재하지 않습니다. ");
        if(budgetOpt.get().getItinerary().getAccount() == accountOpt.get()) throw new NoSuchElementException("해당 유저는 가계부 작성자 입니다.");

        budgetAccountRepository.save(budgetAccountAddDTO.add(accountOpt.get(),budgetOpt.get()));

        return "추가 성공";
    }
}
