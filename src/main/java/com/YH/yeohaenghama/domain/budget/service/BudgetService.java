package com.YH.yeohaenghama.domain.budget.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.budget.dto.*;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.budget.repository.BudgetRepository;
import com.YH.yeohaenghama.domain.budget.repository.ExpendituresRepository;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final ExpendituresRepository expendituresRepository;
    private final ItineraryRepository itineraryRepository;
    private final AccountRepository accountRepository;
    private final PlaceRepository placeRepository;
    public BudgetCreateDTO.Response budgetCreate(BudgetCreateDTO.Request dto){
        Optional<Itinerary> itineraryOpt = itineraryRepository.findById(dto.getItineraryId());

        if(itineraryOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 일정이 존재하지 않습니다. ");

        BudgetCreateDTO budgetCreateDTO = new BudgetCreateDTO(dto);

        Budget budget = budgetCreateDTO.toEntity(itineraryOpt.get());

        budgetRepository.save(budget);

        return BudgetCreateDTO.Response.fromEntity(budget);
    }

    public String budgetDelete(BudgetDeleteDTO.Request dto){
        if(budgetRepository.findById(dto.getBudgetId()).isEmpty()){
            throw new NoSuchElementException("해당 ID를 가진 가계부가 존재하지 않습니다. ");
        }

        budgetRepository.deleteById(dto.getBudgetId());
        return "삭제 완료";
    }

    public BudgetShowDTO.Response budgetShow(BudgetShowDTO.Request dto){
        Optional<Budget> budgetOpt = budgetRepository.findById(dto.getBudgetId());
        if(budgetOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 가계부가 존재하지 않습니다. ");
        List<Expenditures> expenditures = expendituresRepository.findByBudgetId(budgetOpt.get().getId());

        BudgetShowDTO.Response response = BudgetShowDTO.Response.fromEntity(budgetOpt.get(),expenditures);




        return response;
    }
}
