package com.YH.yeohaenghama.domain.budget.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.budget.dto.BudgetCreateDTO;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.repository.BudgetRepository;
import com.YH.yeohaenghama.domain.budget.repository.ExpendituresRepository;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final ExpendituresRepository expendituresRepository;
    private final ItineraryRepository itineraryRepository;
    private final AccountRepository accountRepository;
    public void budgetCreate(BudgetCreateDTO.Request dto){
        Optional<Itinerary> itineraryOpt = itineraryRepository.findById(dto.getItineraryId());
        Optional<Account> accountOpt = accountRepository.findById(dto.getAccountId());

        if(itineraryOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 일정이 존재하지 않습니다. ");

        BudgetCreateDTO budgetCreateDTO = new BudgetCreateDTO(dto);

        budgetRepository.save(budgetCreateDTO.toEntity(accountOpt.get(),itineraryOpt.get()));
    }
}
