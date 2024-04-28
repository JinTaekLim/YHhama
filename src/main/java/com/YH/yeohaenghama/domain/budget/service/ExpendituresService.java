package com.YH.yeohaenghama.domain.budget.service;

import com.YH.yeohaenghama.domain.budget.dto.ExpendituresAddDTO;
import com.YH.yeohaenghama.domain.budget.dto.ExpendituresDeleteDTO;
import com.YH.yeohaenghama.domain.budget.dto.ExpendituresShowDTO;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.budget.repository.BudgetRepository;
import com.YH.yeohaenghama.domain.budget.repository.ExpendituresRepository;
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
public class ExpendituresService {
    private final BudgetRepository budgetRepository;
    private final ExpendituresRepository expendituresRepository;
    private final ItineraryRepository itineraryRepository;
    private final PlaceRepository placeRepository;

    public ExpendituresAddDTO.Response expendituresAdd(ExpendituresAddDTO.Request dto){
        Optional<Budget> budgetOpt = budgetRepository.findById(dto.getBudget());
        if(budgetOpt.isEmpty()) { throw new NoSuchElementException("해당 ID를 가진 가계부가 존재하지 않습니다. "); }
        Optional<Place> placeOpt = null;
        if(dto.getPlace() != null) {
            placeOpt = placeRepository.findById(dto.getPlace());
            if (placeOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 장소가 존재하지 않습니다. ");
        }

        Expenditures expenditures = new ExpendituresAddDTO(dto).toEntity();
        expenditures.setBudget(budgetOpt.get());
        if(dto.getPlace() != null) expenditures.setPlace(placeOpt.get());
        expendituresRepository.save(expenditures);

        ExpendituresAddDTO.Response response = ExpendituresAddDTO.Response.fromEntity(expenditures);

        if(placeOpt != null) response.setPlace(placeOpt.get());
        return response;
    }

    public List<ExpendituresShowDTO.Response> expendituresShow(ExpendituresShowDTO.Request dto){
        List<Expenditures> expendituresList = expendituresRepository.findByBudgetId(dto.getId());
        if(expendituresList.isEmpty()) { throw new NoSuchElementException("해당 가계부에는 존재하는 지출 금액이 없습니다. "); }
        List<ExpendituresShowDTO.Response> response = new ArrayList<>();

        for(Expenditures expenditures : expendituresList){
            response.add(ExpendituresShowDTO.Response.fromEntity(expenditures));
        }

        return response;
    }

    public String expendituresDeleteOne(ExpendituresDeleteDTO.RequestDeleteOne dto){
        Optional<Expenditures> expendituresOpt = expendituresRepository.findById(dto.getId());
        if(expendituresOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 지출 금액이 존재하지 않습니다. ");

        expendituresRepository.deleteById(dto.getId());

        return "삭제 완료";
    }

    public String expendituresDeleteDay(ExpendituresDeleteDTO.RequestDeleteDay dto){
        List<Expenditures> expendituresOpt = expendituresRepository.findByDay(dto.getDay());
        if(expendituresOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 지출 금액이 존재하지 않습니다. ");

        expendituresRepository.deleteAll(expendituresOpt);

        return "삭제 완료";
    }
}
