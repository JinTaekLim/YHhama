package com.YH.yeohaenghama.domain.report.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.diary.dto.CommentDTO;
import com.YH.yeohaenghama.domain.diary.dto.CommentShowDTO;
import com.YH.yeohaenghama.domain.diary.entity.Comment;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.repository.CommentRepository;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.report.dto.*;
import com.YH.yeohaenghama.domain.report.entity.ReportComment;
import com.YH.yeohaenghama.domain.report.entity.ReportDiary;
import com.YH.yeohaenghama.domain.report.entity.ReportReview;
import com.YH.yeohaenghama.domain.report.repository.ReportCommentRepository;
import com.YH.yeohaenghama.domain.report.repository.ReportDiaryRepository;
import com.YH.yeohaenghama.domain.report.repository.ReportReviewRepository;
import com.YH.yeohaenghama.domain.review.entity.Review;
import com.YH.yeohaenghama.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final ReportDiaryRepository reportDiaryRepository;
    private final ReportReviewRepository reportReviewRepository;
    private final ReportCommentRepository reportCommentRepository;
    private final AccountRepository accountRepository;
    private final DiaryRepository diaryRepository;
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;

    public ReportCountDTO diaryReport(ReportDiaryDTO dto) {
        Account account = checkAccount(dto.getAccountId());

        Optional<Diary> diaryOpt = diaryRepository.findById(dto.getDiaryId());
        Diary diary = diaryOpt.orElseThrow(() ->  new NoSuchElementException("해당 ID값을 가진 일기가 존재하지 않습니다."));

        if (reportDiaryRepository.findByAccountIdAndDiaryId(dto.getAccountId(), dto.getDiaryId()).isPresent()) {
            throw new NoSuchElementException("2회 이상 신고할 수 없습니다.");
        }

        ReportDiary reportDiary = new ReportDiary(account,diary);

        reportDiaryRepository.save(reportDiary);

        return new ReportCountDTO((long) reportDiaryRepository.findByDiaryId(dto.getDiaryId()).size());
    }

    public ReportCountDTO reviewReport(ReportReviewDTO dto){
        Account account = checkAccount(dto.getAccountId());

        Optional<Review> reviewOpt = reviewRepository.findById(dto.getReviewId());
        Review review = reviewOpt.orElseThrow(() -> new NoSuchElementException("해당 ID값을 가진 리뷰가 존재하지 않습니다."));

        if (reportReviewRepository.findByAccountIdAndReviewId(dto.getAccountId(), dto.getReviewId()).isPresent()) {
            throw new NoSuchElementException("2회 이상 신고할 수 없습니다.");
        }

        ReportReview reportReview = new ReportReview(account,review);

        reportReviewRepository.save(reportReview);

        return new ReportCountDTO((long) reportReviewRepository.findByReviewId(dto.getReviewId()).size());
    }

    public ReportCountDTO commentReport(ReportCommentDTO dto){
        Account account = checkAccount(dto.getAccountId());

        Optional<Comment> coomentOpt = commentRepository.findById(dto.getCommentId());
        Comment comment = coomentOpt.orElseThrow(() -> new NoSuchElementException("해당 ID값을 가진 댓글이 존재하지 않습니다."));

        if (reportCommentRepository.findByAccountIdAndCommentId(dto.getAccountId(), dto.getCommentId()).isPresent()) {
            throw new NoSuchElementException("2회 이상 신고할 수 없습니다.");
        }

        ReportComment reportComment = new ReportComment(account,comment);

        reportCommentRepository.save(reportComment);

        return new ReportCountDTO((long) reportCommentRepository.findByCommentId(dto.getCommentId()).size());

    }



    public List<ReportDiaryDTO.Request> diaryReportList(){
        List<ReportDiary> reportDiaryList = reportDiaryRepository.findAll();

        List<ReportDiaryDTO.Request> reportShowDiaryDTOList = new ArrayList<>();

        Set<Long> addedDiaryIds = new HashSet<>();

        for (ReportDiary reportDiary : reportDiaryList) {
            Long diaryId = reportDiary.getDiary().getId();

            if (!addedDiaryIds.contains(diaryId)) {
                Diary diary = reportDiary.getDiary();
                ReportDiaryDTO.Request reportShowDiaryDTO = new ReportDiaryDTO.Request(
                        diaryId,
                        diary.getTitle(),
                        reportDiary.getAccount().getNickname(),
                        reportDiaryRepository.findByDiaryId(diaryId).size(),
                        diary.getDate());

                reportShowDiaryDTOList.add(reportShowDiaryDTO);

                addedDiaryIds.add(diaryId);
            }
        }

        return reportShowDiaryDTOList;
    }


    public List<ReportCommentDTO.Response> commentReportList(){
        List<ReportComment> commentList = reportCommentRepository.findAll();

        List<ReportCommentDTO.Response> responses = new ArrayList<>();

        for(ReportComment reportComment : commentList){
            Comment comment = reportComment.getComment();
            ReportCommentDTO.Response response = ReportCommentDTO.Response.fromEntity(comment,reportCommentRepository.findByCommentId(comment.getId()).size());
            responses.add(response);
        }
        return responses;

    }

    public Account checkAccount(Long accountId){
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        return accountOpt.orElseThrow(() -> new NoSuchElementException("해당 ID값을 가진 유저가 존재하지 않습니다."));
    }

}
