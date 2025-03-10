package com.YH.yeohaenghama.domain.review.service;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.addPlace.entity.AddPlace;
import com.YH.yeohaenghama.domain.addPlace.service.AddPlaceService;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.PlaceRepository;
import com.YH.yeohaenghama.domain.openApi.service.OpenApiService;
import com.YH.yeohaenghama.domain.review.dto.*;
import com.YH.yeohaenghama.domain.review.entity.Review;
import com.YH.yeohaenghama.domain.review.entity.ReviewPhotoURL;
import com.YH.yeohaenghama.domain.review.repository.ReviewPhotoURLRepository;
import com.YH.yeohaenghama.domain.review.repository.ReviewRepository;
import com.YH.yeohaenghama.domain.GCDImage.service.GCSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewPhotoURLRepository reviewPhotoURLRepository;
    private final AccountRepository accountRepository;
    private final GCSService gcsService;
    private final OpenApiService openApiService;
    private final AddPlaceService addPlaceService;
    private final PlaceRepository placeRepository;

    public ReviewDTO.Response join(ReviewDTO.Request dto){
        if(accountRepository.findById(Long.valueOf(dto.getAccountId())).isEmpty()){
            throw new NoSuchElementException("해당 ID값을 가진 유저가 존재하지 않습니다. : " + dto.getAccountId());
        }
        if(!reviewRepository.findByContentTypeIdAndContentIdAndAccountId(dto.getContentTypeId(),dto.getContentId(), dto.getAccountId()).isEmpty()){
            throw new DataIntegrityViolationException("이미 해당 장소의 등록된 평점이 존재합니다. ");
        }

        List<MultipartFile> photoList = dto.getPhotos();
        if (photoList == null) {
            photoList = Collections.emptyList();
        }

        ReviewDTO ratingDTO = new ReviewDTO(dto);

        Review review = reviewRepository.save(ratingDTO.toEntity());
        log.info("id : " + review.getId());


        String filename = dto.getContentId()+"_"+dto.getContentTypeId()+"/"+dto.getAccountId();
        int fileNum = 1;

        List<String>photoUrlList = new ArrayList<>();

        for (MultipartFile photo : photoList ) {
            try {
                String photoUrl = gcsService.uploadPhoto(photo, String.valueOf(fileNum), "Review/"+filename);
                log.info("업로드 사진 위치 : " + photoUrl);

                ReviewPhotoURL reviewPhotoURL = new ReviewPhotoURL();
                reviewPhotoURL.ReviewPhotoURL(review,photoUrl);
                reviewPhotoURLRepository.save(reviewPhotoURL);
                log.info("저장");
                photoUrlList.add(reviewPhotoURL.getPhotoUrl());

                fileNum++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



        ReviewDTO.Response response = ReviewDTO.Response.fromEntity(review);
        response.setReviewPhotoURLList(photoUrlList);

        return response;

    }




    public ReviewDTO.Response reviewShow(ReviewDTO.Show dto){
        log.info("DTO = " + dto);

        List<Review> reviews = reviewRepository.findByContentTypeIdAndContentIdAndAccountId(dto.getContentTypeId(), dto.getContentId(), dto.getAccountId());
        if (reviews.isEmpty()) throw new NoSuchElementException("해당 유저가 작성한 장소의 저장된 평점이 존재하지 않습니다. : " + dto);


        ReviewDTO.Response response = ReviewDTO.Response.fromEntity(reviews.get(0));


        return response;
    }

    public List<ReviewShowAllDTO.Response> reviewShowAll(ReviewShowAllDTO.Request dto) throws Exception {

        List<Review> reviewList = reviewRepository.findByContentTypeIdAndContentId(dto.getContentTypeId(),dto.getContentId());
        List<ReviewShowAllDTO.Response> responseList = new ArrayList<>();

        for(Review r : reviewList){
            log.info("Reivew - : " + r.getId());
        }

        if(!reviewList.isEmpty()){

            for(int i = reviewList.size() - 1; i >= 0; i--) {

                Optional<Account> accountOptional = accountRepository.findById(reviewList.get(i).getAccountId());
                Account account = accountOptional.get();
                AccountShowDTO.Response accountShowDTO = new AccountShowDTO.Response(account.getId(),account.getNickname(), account.getPhotoUrl(), account.getRole());
                ReviewShowAllDTO.Response response = new ReviewShowAllDTO.Response().fromEntity(reviewList.get(i),accountShowDTO);
                responseList.add(response);
            }
        }

        String keyword = "";
            if(dto.getContentTypeId() == 80){
                AddPlace addPlace = addPlaceService.getAddPlaceInfo(String.valueOf(dto.getContentId()));
                keyword = addPlace.getTitle();
            } else {
                List<Place> placeList = placeRepository.findByPlaceNumAndPlaceType(String.valueOf(dto.getContentId()),String.valueOf(dto.getContentTypeId()));
//                if(placeList.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 Place가 존재하지 않습니다.");
                if(!placeList.isEmpty()){
                    keyword = placeList.get(0).getPlaceName();
                }
            }

            if(!keyword.equals("")){
                String naverReview = openApiService.searchReview(keyword);

                for(ReviewShowAllDTO.Response reivewShow : ReviewShowAllDTO.Response.pasing(naverReview)){
                    responseList.add(reivewShow);
                }
            }



        return responseList;
    }

    public List<ReviewAccountShowDTO.Response> reviewAccountShow(ReviewAccountShowDTO.Request dto){
        List<Review> reviewsList = reviewRepository.findByAccountId(dto.getAccountId());

        if (reviewsList.isEmpty()) { throw new NoSuchElementException("해당 ID를 가진 유저의 작성된 리뷰가 존재하지 않습니다.");}

        List<ReviewAccountShowDTO.Response> responseList = new ArrayList<>();

        for(Review review : reviewsList){
            responseList.add(ReviewAccountShowDTO.Response.fromEntity(review));
        }

        return responseList;

    }




    public ReviewShowDTO.Response ratingShow(ReviewShowDTO.Request dto){

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

    public String delete(ReviewDeleteDTO.Request dto) throws IOException {

        List<Review> reviews = reviewRepository.findByContentTypeIdAndContentIdAndAccountId(dto.getContentTypeId(), dto.getContentId(), dto.getAccountId());
        if (reviews.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 리뷰가 존재하지 않습니다.");

        if (!reviews.get(0).getReviewPhotoURLS().isEmpty()){
            log.info("사진 URL : " + reviews.get(0).getReviewPhotoURLS().get(0).getPhotoUrl());   // 사진 URL

            String fileUrl = reviews.get(0).getReviewPhotoURLS().get(0).getPhotoUrl();
            String fileName = "";
            int startIndex = fileUrl.indexOf("Review");
            if (startIndex != -1) {
                int secondSlashIndex = fileUrl.indexOf('/', startIndex + 7);
                if (secondSlashIndex != -1) {
                    fileName = fileUrl.substring(startIndex, secondSlashIndex);
                }
            }

            gcsService.delete(fileName);
        }

        reviewRepository.deleteById(reviews.get(0).getId());
        return "삭제 완료";
    }


    public ReviewDTO.Response update(Long reviewId, ReviewDTO.Request dto) throws IOException {
        Optional<Review> reviewOpt = reviewRepository.findById(reviewId);
        if (reviewOpt.isEmpty()) {
            throw new NoSuchElementException("해당 ID값을 가진 리뷰가 존재하지 않습니다. ");
        }

        Review review = reviewOpt.get();

        gcsService.delete("Review/"+dto.getContentId() + "_" + dto.getContentTypeId() + "/" + dto.getAccountId());

        List<ReviewPhotoURL> reviewPhotoURLs = new ArrayList<>();
        List<MultipartFile> photoList = dto.getPhotos();
        ReviewPhotoURL reviewPhotoURL = new ReviewPhotoURL();
        if (photoList != null && !photoList.isEmpty()) {
            String filename = dto.getContentId() + "_" + dto.getContentTypeId() + "/" + dto.getAccountId();
            int fileNum = 1;

            for (MultipartFile photo : photoList) {
                try {
                    String photoUrl = gcsService.uploadPhoto(photo, String.valueOf(fileNum), "Review/" + filename);
                    log.info("업로드 사진 위치: " + photoUrl);

                    reviewPhotoURL.ReviewPhotoURL(review, photoUrl);
                    reviewPhotoURLs.add(reviewPhotoURL);
                    fileNum++;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        review.update(dto.getPlaceName(), dto.getContentId(),dto.getContentTypeId(),dto.getRating(),dto.getContent(),reviewPhotoURLs);

        Review updatedReview = reviewRepository.save(review);



        log.info("리뷰 수정완료");
        return ReviewDTO.Response.fromEntity(updatedReview);
    }


    public boolean check(ReviewDTO.Show dto) {
        return !reviewRepository.findByContentTypeIdAndContentIdAndAccountId(dto.getContentTypeId(), dto.getContentId(), dto.getAccountId()).isEmpty();
    }

    public List<Review> getReview(Long contentId,Long typeId){
        List<Review> reviewList = reviewRepository.findByContentTypeIdAndContentId(typeId,contentId);
        return reviewList;
    }


}
