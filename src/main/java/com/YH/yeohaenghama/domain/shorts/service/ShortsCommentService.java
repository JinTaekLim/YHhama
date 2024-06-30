package com.YH.yeohaenghama.domain.shorts.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.service.AccountService;
import com.YH.yeohaenghama.domain.shorts.dto.CreateCommentDTO;
import com.YH.yeohaenghama.domain.shorts.dto.ReadCommentDTO;
import com.YH.yeohaenghama.domain.shorts.entity.Shorts;
import com.YH.yeohaenghama.domain.shorts.entity.ShortsComment;
import com.YH.yeohaenghama.domain.shorts.repository.ShortsCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShortsCommentService {

    private final ShortsCommentRepository shortsCommentRepository;
    private final ShortsService shortsService;
    private final AccountService accountService;

    public CreateCommentDTO.Response createComment(CreateCommentDTO.Request req){
        Shorts shorts = shortsService.verificationShorts(req.getShortsId());
        Account account = accountService.getAccount(req.getAccountId());
        ShortsComment shortsComment = shortsCommentRepository.save(CreateCommentDTO.fromEntity(req,account,shorts));

        return CreateCommentDTO.Response.toEntity(shortsComment);
    }

    public ReadCommentDTO.AllResponse readComment(ReadCommentDTO.Request req){
        List<ShortsComment> shortsCommentList = shortsCommentRepository.findByShortsId(req.getShortsId());
        if(shortsCommentList.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 쇼츠에 댓글이 존재하지 않습니다.");


        return new ReadCommentDTO.AllResponse(shortsCommentList);
    }
}
