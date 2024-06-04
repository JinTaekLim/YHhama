package com.YH.yeohaenghama.domain.budget.repository;

import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget,Long> {

}
