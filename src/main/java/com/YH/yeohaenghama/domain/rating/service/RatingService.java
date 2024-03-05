package com.YH.yeohaenghama.domain.rating.service;

import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.rating.dto.RatingDTO;
import com.YH.yeohaenghama.domain.rating.entity.Rating;
import com.YH.yeohaenghama.domain.rating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final AccountRepository accountRepository;

    public RatingDTO.Response join(RatingDTO.Request dto){
        if(accountRepository.findById(Long.valueOf(dto.getAccountId())).isEmpty()){
            throw new NoSuchElementException("해당 ID값을 가진 유저가 존재하지 않습니다. : " + dto.getAccountId());
        }


        RatingDTO ratingDTO = new RatingDTO(dto);
        Rating rating = ratingRepository.save(ratingDTO.toEntity());
        return RatingDTO.Response.fromEntity(rating);

    }
}
