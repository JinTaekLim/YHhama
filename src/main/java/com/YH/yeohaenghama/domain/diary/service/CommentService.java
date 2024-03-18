package com.YH.yeohaenghama.domain.diary.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.diary.dto.CommentDTO;
import com.YH.yeohaenghama.domain.diary.entity.Comment;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentDTO.Response save(CommentDTO.Request dto){

        if (dto.getAccount() == null){ throw new NoSuchElementException("해당 ID를 가진 유저가 존재하지 않음"); }

        if (dto.getDiary() == null){ throw new NoSuchElementException("해당 ID를 가진 일기가 존재하지 않음"); }

        CommentDTO commentDTO = new CommentDTO(dto);
        Comment comment = commentDTO.toEntity();

        commentRepository.save(comment);

        return CommentDTO.Response.fromEntity(comment);

    }


    public CommentDTO.Response delete(Account account, Diary diary, Comment comment){
        if (account.getId() == null){ throw new NoSuchElementException("해당 ID를 가진 유저가 존재하지 않음"); }

        if (diary.getId() == null){ throw new NoSuchElementException("해당 ID를 가진 일기가 존재하지 않음"); }

        if (comment.getId() == null){ throw new NoSuchElementException("해당 ID를 가진 댓글이 존재하지 않음"); }

        Optional<Comment> commentOpt = commentRepository.findById(comment.getId());

        if(commentOpt.get().getAccount() != account){
            throw new NoSuchElementException("해당 댓글을 작성한 글쓴이가 아닙니다.");
        }

        commentRepository.deleteById(comment.getId());

        return CommentDTO.Response.fromEntity(comment);
    }
}
