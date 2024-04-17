package com.YH.yeohaenghama.domain.budget.repository;

import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpendituresRepository extends JpaRepository<Expenditures,Long> {
}
