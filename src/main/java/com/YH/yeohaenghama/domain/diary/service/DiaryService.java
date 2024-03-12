package com.YH.yeohaenghama.domain.diary.service;

import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
import com.YH.yeohaenghama.domain.diary.dto.DiaryShowDTO;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.entity.DiaryPhotoUrl;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.GCDImage.service.GCSService;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.PlaceRepository;
import com.YH.yeohaenghama.domain.review.dto.ReviewDTO;
import com.YH.yeohaenghama.domain.review.entity.Review;
import com.YH.yeohaenghama.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final ItineraryRepository itineraryRepository;
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final GCSService gcsService;

    public DiaryDTO.Response save(DiaryDTO.Request diaryDTO) throws IOException {
        if(itineraryRepository.findById(diaryDTO.getItinerary()).isEmpty()){
           throw new NoSuchElementException("해당 ID를 가진 일정이 존재하지 않습니다.");
        }
        Diary diary = new Diary();

        List<DiaryPhotoUrl> diaryPhotoUrls = new ArrayList<>();
        List<MultipartFile> photos = diaryDTO.getPhotos();

        if (photos != null) {
            int i = 0;
            for (MultipartFile photo : photos) {
                String photoUrl = gcsService.uploadPhoto(photo, String.valueOf(i), "Diary/" + diaryDTO.getItinerary());
                DiaryPhotoUrl diaryPhotoUrl = new DiaryPhotoUrl(diary, photoUrl);
                diaryPhotoUrls.add(diaryPhotoUrl);
                i++;
            }
        }

        diary.set(diaryDTO.getDate(), diaryDTO.getTitle(), diaryDTO.getContent(), diaryDTO.getItinerary(), diaryPhotoUrls);

        diaryRepository.save(diary);

        return DiaryDTO.Response.fromEntity(diary);
    }



        public void delete(Long diaryId) throws IOException {
        if(diaryRepository.findById(diaryId).isEmpty()){
            throw new NoSuchElementException("해당 ID를 가진 일기가 존재하지 않습니다.");
        }
        Optional<Diary> optionalDiary = diaryRepository.findById(diaryId);

        gcsService.delete("Diary/"+optionalDiary.get().getItinerary());
        diaryRepository.deleteById(diaryId);

    }

    public DiaryShowDTO.Response show(Long diaryId){
        Optional<Diary> diaryOpt = diaryRepository.findById(diaryId);

        if (diaryOpt.isEmpty()) {
            throw new NoSuchElementException("해당 ID를 가진 일기가 존재하지 않습니다.");
        }

        Diary diary = diaryOpt.get();



        Optional<Itinerary> itineraryOpt = itineraryRepository.findById(diary.getItinerary());
        Itinerary itinerary = itineraryOpt.get();

        List<Place> placeOpt = placeRepository.findByItineraryId(itinerary.getId());




        Map<String, List<ReviewDTO.Response>> reviewsByDate = new HashMap<>();

        for (int i = 0; i < itinerary.getPlaces().size(); i++) {
            Long PlaceType = Long.parseLong(itinerary.getPlaces().get(i).getPlaceType());
            Long PlaceNum = Long.parseLong(itinerary.getPlaces().get(i).getPlaceNum());

            log.info("PlaceType = " + PlaceType + "  PlaceNum = " + PlaceNum + "  Id = " + itinerary.getAccount().getId());

            List<Review> reviewOpt = reviewRepository.findByContentTypeIdAndContentIdAndAccountId(PlaceType, PlaceNum, itinerary.getAccount().getId());

            String reviewDate = String.valueOf(placeOpt.get(i).getDay());

            if (!reviewsByDate.containsKey(reviewDate)) {
                reviewsByDate.put(reviewDate, new ArrayList<>());
            }

            if (!reviewOpt.isEmpty()) {
                Review review = reviewOpt.get(0);
                ReviewDTO.Response reviewResponse = ReviewDTO.Response.fromEntity(review);
                reviewsByDate.get(reviewDate).add(reviewResponse);
                log.info("리뷰 ID = " + review.getId());
            } else {

                reviewsByDate.put(reviewDate, new ArrayList<>());
                log.info("리뷰가 없습니다.");
            }
        }


        log.info("총 리뷰 = " + reviewsByDate);

        DiaryShowDTO.Response showDTO = DiaryShowDTO.Response.fromEntity(diary, reviewsByDate);



        return showDTO;

    }






    public List<Diary> findAll(){
        return diaryRepository.findAll();
    }

}
