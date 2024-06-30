package com.YH.yeohaenghama.domain.shorts.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.service.AccountService;
import com.YH.yeohaenghama.domain.shorts.dto.CreateCommentDTO;
import com.YH.yeohaenghama.domain.shorts.dto.ReadCommentDTO;
import com.YH.yeohaenghama.domain.shorts.dto.UpdateCommentDTO;
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

    public ShortsComment verificationComment(Long commentId){
        return shortsCommentRepository.findById(commentId).orElseThrow(()-> new NoSuchElementException("해당 ID를 가진 댓글이 존재하지 않습니다."));
    }
    public CreateCommentDTO.Response createComment(CreateCommentDTO.Request req){
        Shorts shorts = shortsService.verificationShorts(req.getShortsId());
        Account account = accountService.getAccount(req.getAccountId());
        ShortsComment shortsComment = shortsCommentRepository.save(CreateCommentDTO.fromEntity(req,account,shorts));

        return CreateCommentDTO.Response.toEntity(shortsComment);
    }

    public ReadCommentDTO.AllResponse readComment(Long shortsId){
        List<ShortsComment> shortsCommentList = shortsCommentRepository.findByShortsId(shortsId);
        if(shortsCommentList.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 쇼츠에 댓글이 존재하지 않습니다.");

        return new ReadCommentDTO.AllResponse(shortsCommentList);
    }

    public ReadCommentDTO.Response updateComment(UpdateCommentDTO.Request req){
        ShortsComment oldShortsComment = verificationComment(req.getCommentId());
        if(!oldShortsComment.getAccount().getId().equals(req.getAccountId())) throw new NoSuchElementException("해당 댓글을 수정할 권한을 보유하고 있지 않습니다.");
        ShortsComment newShortsComment = new UpdateCommentDTO().update(oldShortsComment,req);
        shortsCommentRepository.save(newShortsComment);
        return ReadCommentDTO.Response.toEntity(newShortsComment);
    }

    public String deleteComment(Long commentId, Long accountId){
        ShortsComment shortsComment = verificationComment(commentId);
        if(!shortsComment.getAccount().getId().equals(accountId)) throw new NoSuchElementException("해당 댓글을 삭제할 권한을 보유하고 있지 않습니다.");
        shortsCommentRepository.deleteById(commentId);
        return "쇼츠 댓글 삭제 성공";
    }
}
