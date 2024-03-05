package com.YH.yeohaenghama.domain.rating.service;

import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.rating.dto.RatingDTO;
import com.YH.yeohaenghama.domain.rating.dto.RatingShowDTO;
import com.YH.yeohaenghama.domain.rating.entity.Rating;
import com.YH.yeohaenghama.domain.rating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        if(!ratingRepository.findByContentTypeIdAndContentIdAndAccountId(dto.getContentTypeId(),dto.getContentId(), dto.getAccountId()).isEmpty()){
            throw new DataIntegrityViolationException("이미 해당 장소의 등록된 평점이 존재합니다. ");
        }

        RatingDTO ratingDTO = new RatingDTO(dto);
        Rating rating = ratingRepository.save(ratingDTO.toEntity());
        return RatingDTO.Response.fromEntity(rating);

    }


    public RatingShowDTO.Response show(RatingShowDTO.Request dto){

        Long totalRating = Long.valueOf(0);

        List<Rating> ratings = ratingRepository.findByContentTypeIdAndContentId(dto.getContentTypeId(), dto.getContentId());
        RatingShowDTO.Response response = new RatingShowDTO.Response();

        if(ratings.isEmpty()){      // 작성된 평점이 없을 때 반환 값들
            response.setTotalRating(Long.valueOf(0));
            response.setRatingNum(Long.valueOf(0));
            return response;
        };

        response.setRatingNum(Long.valueOf(ratings.size()));

        for (Rating rating : ratings) {
            totalRating += rating.getRating();
        }

        response.setTotalRating(totalRating/ratings.size());
        log.info(String.valueOf(response));
        return response;

    }
}
