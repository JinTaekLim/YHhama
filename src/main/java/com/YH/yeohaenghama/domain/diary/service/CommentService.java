package com.YH.yeohaenghama.domain.diary.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.entity.AccountRole;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.diary.dto.CommentDTO;
import com.YH.yeohaenghama.domain.diary.dto.CommentShowDTO;
import com.YH.yeohaenghama.domain.diary.entity.Comment;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.repository.CommentRepository;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.report.entity.ReportComment;
import com.YH.yeohaenghama.domain.report.repository.ReportCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final AccountRepository accountRepository;
    private final DiaryRepository diaryRepository;
    private final ReportCommentRepository reportCommentRepository;
    public CommentDTO.Response save(CommentDTO.Request dto){

        log.info(String.valueOf(dto));
        Optional<Account> accountOpt = accountRepository.findById(dto.getAccount());
        if(accountOpt.isEmpty()) { throw new NoSuchElementException("해당 ID를 가진 유저가 존재하지 않음"); }
        Optional<Diary> diaryOpt = diaryRepository.findById(dto.getDiary());
        if (diaryOpt.isEmpty()){ throw new NoSuchElementException("해당 ID를 가진 일기가 존재하지 않음"); }

        Account account = accountOpt.get();
        Diary diary = diaryOpt.get();


        CommentDTO commentDTO = new CommentDTO(dto);
        Comment comment = commentDTO.toEntity(account,diary);

        commentRepository.save(comment);

        return CommentDTO.Response.fromEntity(comment);

    }

    public CommentDTO.Response delete(Long accountId, Long diaryId, Long commentId){
        Optional<Account> account = accountRepository.findById(accountId);
        Optional<Diary> diary = diaryRepository.findById(diaryId);
        Optional<Comment> comment = commentRepository.findById(commentId);

        account.orElseThrow(() -> new NoSuchElementException("해당 ID를 가진 유저가 존재하지 않음"));

        if (!diary.isPresent()) {
            throw new NoSuchElementException("해당 ID를 가진 일기가 존재하지 않음");
        }

        if (!comment.isPresent()) {
            throw new NoSuchElementException("해당 ID를 가진 댓글이 존재하지 않음");
        }



        if (!comment.get().getAccount().getId().equals(accountId) && account.get().getRole() == AccountRole.ACCOUNT) {
            throw new NoSuchElementException("해당 댓글을 작성한 글쓴이가 아닙니다.");
        }

        List<ReportComment> commentList = reportCommentRepository.findByCommentId(commentId);
        if(!commentList.isEmpty()){
            reportCommentRepository.deleteAll(commentList);
        }


        commentRepository.deleteById(commentId);

        return CommentDTO.Response.fromEntity(comment.get());
    }


    public CommentDTO.Response update(CommentDTO.Update dto){
        if(accountRepository.findById(dto.getAccount().getId()).isEmpty()) { throw new NoSuchElementException("해당 ID를 가진 유저가 존재하지 않음"); }

        if (dto.getComment() == null){ throw new NoSuchElementException("해당 ID를 가진 댓글이 존재하지 않음"); }

        Optional<Comment> commentOpt = commentRepository.findById(dto.getComment().getId());

        Comment comment = commentOpt.get();

        if(commentOpt.get().getAccount() != dto.getAccount()){
            throw new NoSuchElementException("해당 댓글을 작성한 글쓴이가 아닙니다.");
        }

        comment.update(dto.getContent());

        commentRepository.save(comment);

        return CommentDTO.Response.fromEntity(comment);
    }



    public CommentShowDTO.Response show(CommentShowDTO dto){

        List<Comment> comments = commentRepository.findAllByDiaryId(dto.getDiaryId());

        if (comments.isEmpty()){ throw new NoSuchElementException("해당 ID를 가진 일기에 댓글이 존재하지 않습니다."); }

        List<CommentDTO.Response> commentDTO = new ArrayList<>();

        for (Comment comment : comments) {
            commentDTO.add(CommentDTO.Response.fromEntity(comment));
            log.info(String.valueOf(comment.getId()));
        }

        CommentShowDTO.Response commentShow = new CommentShowDTO.Response();
        commentShow.setCommentShowDTO(commentDTO);


        return commentShow;
    }

    public CommentShowDTO.Response show(Long accountId){

        if(accountRepository.findById(accountId).isEmpty()) { throw new NoSuchElementException("해당 ID를 가진 유저가 존재하지 않음"); }

        List<Comment> comments = commentRepository.findAllByAccountId(accountId);

        if(comments.isEmpty()) { throw new NoSuchElementException("해당 유저가 작성한 댓글이 존재하지 않습니다."); }


        CommentShowDTO.Response commentShowDTO = new CommentShowDTO.Response();

        List<CommentDTO.Response> commentDTO = new ArrayList<>();

        for (Comment comment : comments) {
            commentDTO.add(CommentDTO.Response.fromEntity(comment));
        }
        commentShowDTO.setCommentShowDTO(commentDTO);

        return commentShowDTO;
    }
}
