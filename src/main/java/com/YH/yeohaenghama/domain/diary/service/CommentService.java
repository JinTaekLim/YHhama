package com.YH.yeohaenghama.domain.diary.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.diary.dto.CommentDTO;
import com.YH.yeohaenghama.domain.diary.dto.CommentShowDTO;
import com.YH.yeohaenghama.domain.diary.entity.Comment;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.repository.CommentRepository;
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
    public CommentDTO.Response save(CommentDTO.Request dto){

        if(accountRepository.findById(dto.getAccount().getId()).isEmpty()) { throw new NoSuchElementException("해당 ID를 가진 유저가 존재하지 않음"); }

        if (dto.getDiary() == null){ throw new NoSuchElementException("해당 ID를 가진 일기가 존재하지 않음"); }

        CommentDTO commentDTO = new CommentDTO(dto);
        Comment comment = commentDTO.toEntity();

        commentRepository.save(comment);

        return CommentDTO.Response.fromEntity(comment);

    }

    public CommentDTO.Response delete(Account account, Diary diary, Comment comment){
        if(accountRepository.findById(account.getId()).isEmpty()) { throw new NoSuchElementException("해당 ID를 가진 유저가 존재하지 않음"); }

        if (diary.getId() == null){ throw new NoSuchElementException("해당 ID를 가진 일기가 존재하지 않음"); }

        if (comment.getId() == null){ throw new NoSuchElementException("해당 ID를 가진 댓글이 존재하지 않음"); }

        Optional<Comment> commentOpt = commentRepository.findById(comment.getId());

        if(commentOpt.get().getAccount() != account){
            throw new NoSuchElementException("해당 댓글을 작성한 글쓴이가 아닙니다.");
        }

        commentRepository.deleteById(comment.getId());

        return CommentDTO.Response.fromEntity(comment);
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
        if (dto.getDiary() == null){ throw new NoSuchElementException("해당 ID를 가진 일기가 존재하지 않음"); }

        List<Comment> comments = commentRepository.findAllByDiaryId(dto.getDiary().getId());


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
