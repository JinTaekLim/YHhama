package com.YH.yeohaenghama.domain.budget.repository;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.BudgetAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetAccountRepository extends JpaRepository<BudgetAccount, Long> {
    Optional<BudgetAccount> findByBudgetIdAndAccountId(Long budgetId,Long accountId);
}
