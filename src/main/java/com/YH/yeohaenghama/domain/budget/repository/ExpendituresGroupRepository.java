package com.YH.yeohaenghama.domain.budget.repository;

import com.YH.yeohaenghama.domain.budget.entity.ExpendituresGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpendituresGroupRepository extends JpaRepository<ExpendituresGroup, Long> {
    List<ExpendituresGroup> findByExpendituresIdAndAccountId(Long expendituresId, Long accountId);
}
