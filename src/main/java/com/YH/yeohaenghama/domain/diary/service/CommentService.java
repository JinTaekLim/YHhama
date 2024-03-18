package com.YH.yeohaenghama.domain.diary.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.diary.dto.CommentDTO;
import com.YH.yeohaenghama.domain.diary.entity.Comment;
import com.YH.yeohaenghama.domain.diary.repository.CommentRepository;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryJoinDTO;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
}
