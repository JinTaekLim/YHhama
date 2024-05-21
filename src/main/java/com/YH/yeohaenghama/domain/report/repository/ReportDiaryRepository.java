package com.YH.yeohaenghama.domain.report.repository;

import com.YH.yeohaenghama.domain.report.entity.ReportDiary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportDiaryRepository extends JpaRepository<ReportDiary, Long> {
    Optional<ReportDiary> findByAccountIdAndDiaryId(Long accountId, Long diaryId);

    List<ReportDiary> findByDiaryId(Long diaryId);
    void deleteByAccountId(Long accountId);
}
