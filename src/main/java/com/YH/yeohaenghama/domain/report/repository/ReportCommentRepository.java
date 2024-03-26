package com.YH.yeohaenghama.domain.report.repository;

import com.YH.yeohaenghama.domain.report.entity.ReportComment;
import com.YH.yeohaenghama.domain.report.entity.ReportDiary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportCommentRepository extends JpaRepository<ReportComment, Long> {
    Optional<ReportComment> findByAccountIdAndCommentId(Long accountId, Long commentId);

    List<ReportComment> findByCommentId(Long commentId);
}
