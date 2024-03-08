package com.YH.yeohaenghama.domain.review.service;

import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.review.dto.ReviewDTO;
import com.YH.yeohaenghama.domain.review.dto.ReviewDeleteDTO;
import com.YH.yeohaenghama.domain.review.dto.ReviewShowDTO;
import com.YH.yeohaenghama.domain.review.entity.Review;
import com.YH.yeohaenghama.domain.review.entity.ReviewPhotoURL;
import com.YH.yeohaenghama.domain.review.repository.ReviewPhotoURLRepository;
import com.YH.yeohaenghama.domain.review.repository.ReviewRepository;
import com.YH.yeohaenghama.domain.uploadImage.service.GCSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewPhotoURLRepository reviewPhotoURLRepository;
    private final AccountRepository accountRepository;
    private final GCSService gcsService;

    public ReviewDTO.Response join(ReviewDTO.Request dto, List<MultipartFile> photos){
        if(accountRepository.findById(Long.valueOf(dto.getAccountId())).isEmpty()){
            throw new NoSuchElementException("해당 ID값을 가진 유저가 존재하지 않습니다. : " + dto.getAccountId());
        }
        if(!reviewRepository.findByContentTypeIdAndContentIdAndAccountId(dto.getContentTypeId(),dto.getContentId(), dto.getAccountId()).isEmpty()){
            throw new DataIntegrityViolationException("이미 해당 장소의 등록된 평점이 존재합니다. ");
        }

        dto.setReviewPhotoURLList(Collections.singletonList(photos.toString()));
        log.info("유알엘 : " + Collections.singletonList(photos.toString()).toString());

        ReviewDTO ratingDTO = new ReviewDTO(dto);

        Review review = reviewRepository.save(ratingDTO.toEntity());
        log.info("id : " + review.getId());


        String filename = dto.getContentId()+"_"+dto.getContentTypeId();
        int fileNum = 1;

        for (MultipartFile photo : photos) {
            try {
                String photoUrl = gcsService.uploadPhoto(photo, String.valueOf(fileNum), "Review/"+filename);
                log.info("업로드 사진 위치 : " + photoUrl);

                ReviewPhotoURL reviewPhotoURL = new ReviewPhotoURL(review,photoUrl);
                reviewPhotoURLRepository.save(reviewPhotoURL);

                fileNum++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return ReviewDTO.Response.fromEntity(review);

    }



    public ReviewShowDTO.Response show(ReviewShowDTO.Request dto){

        log.info("DTO확인 :" + dto);
        Long totalRating = Long.valueOf(0);

        List<Review> reviews = reviewRepository.findByContentTypeIdAndContentId(dto.getContentTypeId(), dto.getContentId());
        ReviewShowDTO.Response response = new ReviewShowDTO.Response();

        if(reviews.isEmpty()){      // 작성된 평점이 없을 때 반환 값들
            log.info("작성된 값이 없음");
            response.setTotalRating(Long.valueOf(0));
            response.setRatingNum(Long.valueOf(0));
            return response;
        };

        response.setRatingNum(Long.valueOf(reviews.size()));

        for (Review review : reviews) {
            totalRating += review.getRating();
        }

        response.setTotalRating(totalRating/ reviews.size());
        log.info(String.valueOf(response));
        return response;

    }

    public ReviewDeleteDTO.Request delete(ReviewDeleteDTO.Request dto){
        List<Review> reviews = reviewRepository.findByContentTypeIdAndContentIdAndAccountId(dto.getContentTypeId(), dto.getContentId(), dto.getAccountId());

        log.info(reviews.toString());
        if(!reviews.isEmpty()){
            reviewRepository.deleteById(reviews.get(0).getId());
            return dto;
        }
        else {
            throw new NoSuchElementException(" 해당 유저가 작성한 장소의 저장된 평점이 존재하지 않습니다. : " + dto);
        }
    }
}
