package com.YH.yeohaenghama.domain.report.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.diary.dto.CommentDTO;
import com.YH.yeohaenghama.domain.diary.dto.CommentShowDTO;
import com.YH.yeohaenghama.domain.diary.entity.Comment;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.repository.CommentRepository;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.report.dto.*;
import com.YH.yeohaenghama.domain.report.entity.ReportComment;
import com.YH.yeohaenghama.domain.report.entity.ReportDiary;
import com.YH.yeohaenghama.domain.report.entity.ReportReview;
import com.YH.yeohaenghama.domain.report.repository.ReportAccountRepository;
import com.YH.yeohaenghama.domain.report.repository.ReportCommentRepository;
import com.YH.yeohaenghama.domain.report.repository.ReportDiaryRepository;
import com.YH.yeohaenghama.domain.report.repository.ReportReviewRepository;
import com.YH.yeohaenghama.domain.review.entity.Review;
import com.YH.yeohaenghama.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
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
    private final ReportAccountRepository reportAccountRepository;
    private final AccountRepository accountRepository;
    private final ItineraryRepository itineraryRepository;
    private final DiaryRepository diaryRepository;
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;


    public ReportCountDTO accountReport(ReportAccountDTO.Request dto){
        if(dto.getAccountId() == dto.getReportAccountId()) throw new NoSuchElementException("잘못된 값이 입력되었습니다.");

        Account account = checkAccount(dto.getAccountId());
        Account reportAccount = checkAccount(dto.getReportAccountId());

        reportAccountRepository.save(ReportAccountDTO.fromEntity(account,reportAccount));

        return new ReportCountDTO((long) reportAccountRepository.findByReportAccountId(dto.getReportAccountId()).size());
    }


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


    public List<ReportDiaryAllDTO.Response> diaryShow(){
        List<Diary> diaryList = diaryRepository.findAll();

        List<ReportDiaryAllDTO.Response> response = new ArrayList<>();
        for(Diary diary : diaryList){
            Optional<Itinerary> itineraryOpt = itineraryRepository.findById(diary.getItinerary());
            List<ReportDiary> diaries = reportDiaryRepository.findByDiaryId(diary.getId());
            response.add(ReportDiaryAllDTO.Response.fromEntity(diary, itineraryOpt.get().getAccount() , diaries.size()));
        }

        return response;
    }

    public List<ReportReviewDTO.Response> reviewShow(){
        List<Review> reviewList = reviewRepository.findAll();

        List<ReportReviewDTO.Response> response = new ArrayList<>();
        for(Review review : reviewList){
            List<ReportReview> reviewOpt = reportReviewRepository.findByReviewId(review.getId());
            Optional<Account> accountOpt = accountRepository.findById(review.getAccountId());
            response.add(ReportReviewDTO.Response.fromEntity(review,accountOpt.get(),reviewOpt.size()));
        }

        return response;
    }

    public List<ReportCommentDTO.Response> commentShow(){
        List<Comment> commentList = commentRepository.findAll();

        List<ReportCommentDTO.Response> response = new ArrayList<>();
        for(Comment comment : commentList){
            List<ReportComment> commentOpt = reportCommentRepository.findByCommentId(comment.getId());
            response.add(ReportCommentDTO.Response.fromEntity(comment,commentOpt.size()));
        }
        return response;
    }


    public List<ReportDiaryDTO.Response> diaryReportList(){
        List<ReportDiary> reportDiaryList = reportDiaryRepository.findAll();

        List<ReportDiaryDTO.Response> reportShowDiaryDTOList = new ArrayList<>();

        Set<Long> addedDiaryIds = new HashSet<>();

        for (ReportDiary reportDiary : reportDiaryList) {
            Long diaryId = reportDiary.getDiary().getId();

            if (!addedDiaryIds.contains(diaryId)) {
                Diary diary = reportDiary.getDiary();
                ReportDiaryDTO.Response reportShowDiaryDTO = new ReportDiaryDTO.Response(
                        diaryId,
                        diary.getTitle(),
                        reportDiary.getAccount(),
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


    public List<ReportReviewDTO.Response> reviewReportList(){
        List<ReportReview> reviewList = reportReviewRepository.findAll();

        List<ReportReviewDTO.Response> responseList = new ArrayList<>();

        for(ReportReview reviewReview : reviewList){
            Review review =  reviewReview.getReview();
            Optional<Account> accountOpt = accountRepository.findById(review.getAccountId());
            ReportReviewDTO.Response response = ReportReviewDTO.Response.fromEntity(review,accountOpt.get(),reportReviewRepository.findByReviewId(review.getId()).size());

            responseList.add(response);
        }

        return responseList;


    }

    public Account checkAccount(Long accountId){
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        return accountOpt.orElseThrow(() -> new NoSuchElementException("해당 ID값을 가진 유저가 존재하지 않습니다."));
    }

}
