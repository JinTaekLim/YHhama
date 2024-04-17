package com.YH.yeohaenghama.domain.budget.repository;

import com.YH.yeohaenghama.domain.budget.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget,Long> {
}
