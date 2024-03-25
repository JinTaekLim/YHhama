package com.YH.yeohaenghama.domain.report.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.report.dto.ReportDiaryDTO;
import com.YH.yeohaenghama.domain.report.entity.Report;
import com.YH.yeohaenghama.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final ReportRepository reportRepository;
    private final AccountRepository accountRepository;
    private final DiaryRepository diaryRepository;

    public void report(ReportDiaryDTO dto) {
        if (reportRepository.findByTypeIdAndAccountIdAndDiaryId(dto.getTypeId(),dto.getAccountId(), dto.getDiaryId()).isPresent()) {
            throw new NoSuchElementException("2회 이상 신고할 수 없습니다.");
        }

        Optional<Account> accountOpt = accountRepository.findById(dto.getAccountId());
        Account account = accountOpt.orElseThrow(() -> new NoSuchElementException("해당 ID값을 가진 유저가 존재하지 않습니다."));

        Optional<Diary> diaryOpt = diaryRepository.findById(dto.getDiaryId());
        Diary diary = diaryOpt.orElseThrow(() ->  new NoSuchElementException("해당 ID값을 가진 일기가 존재하지 않습니다."));

        Report reportDiary = new Report(dto.getTypeId(), account,diary);

        reportRepository.save(reportDiary);
    }
}
