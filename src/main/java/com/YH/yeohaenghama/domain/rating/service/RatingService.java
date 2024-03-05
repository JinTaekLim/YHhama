package com.YH.yeohaenghama.domain.rating.service;

import com.YH.yeohaenghama.domain.rating.dto.RatingDTO;
import com.YH.yeohaenghama.domain.rating.entity.Rating;
import com.YH.yeohaenghama.domain.rating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingDTO.Response join(RatingDTO.Request dto){
        RatingDTO ratingDTO = new RatingDTO(dto);
        Rating rating = ratingRepository.save(ratingDTO.toEntity());
        return RatingDTO.Response.fromEntity(rating);

    }
}
