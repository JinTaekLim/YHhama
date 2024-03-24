package com.YH.yeohaenghama.domain.report.repository;

import com.YH.yeohaenghama.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByTypeIdAndAccountIdAndDiaryId(Long typeId , Long accountId, Long diaryId);

}
