package com.YH.yeohaenghama.domain.diary.service;

import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
import com.YH.yeohaenghama.domain.diary.dto.DiaryShowDTO;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.entity.DiaryPhotoUrl;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.GCDImage.service.GCSService;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.review.dto.ReviewDTO;
import com.YH.yeohaenghama.domain.review.entity.Review;
import com.YH.yeohaenghama.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final ItineraryRepository itineraryRepository;
    private final ReviewRepository reviewRepository;
    private final GCSService gcsService;

    public DiaryDTO.Response save(DiaryDTO.Request diaryDTO) throws IOException {


        Diary diary = new Diary();

        List<DiaryPhotoUrl> diaryPhotoUrls = new ArrayList<>();
        List<MultipartFile> photos = diaryDTO.getPhotos();

        int i = 0;
        for (MultipartFile photo : photos) {
            String PhotoURL = gcsService.uploadPhoto(photo, String.valueOf(i),"Diary/"+diaryDTO.getItinerary());
            DiaryPhotoUrl diaryPhotoUrl = new DiaryPhotoUrl(diary,PhotoURL);
            diaryPhotoUrls.add(diaryPhotoUrl);
            i++;
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




        List<ReviewDTO.Response> reviews = new ArrayList<>();

      for(int i=0; i<itinerary.getPlaces().size(); i++) {

          Long PlaceType = Long.parseLong(itinerary.getPlaces().get(i).getPlaceType());
          Long PlaceNum = Long.parseLong(itinerary.getPlaces().get(i).getPlaceNum());

          log.info("PlaceType = " + PlaceType + "  PlaceNum = " + PlaceNum + "  Id = " + itinerary.getAccount().getId());


          List<Review> reviewOpt = reviewRepository.findByContentTypeIdAndContentIdAndAccountId(PlaceType, PlaceNum, itinerary.getAccount().getId());
//        List<Review> reviewOpt = reviewRepository.findByContentTypeIdAndContentIdAndAccountId(Long.valueOf(11),Long.valueOf(111),Long.valueOf(1));
          if (reviewOpt.get(0) != null) {
              Review review = reviewOpt.get(0);
              reviews.add(ReviewDTO.Response.fromEntity(review));
              log.info("리뷰 ID = " + review.getId());
          } else {
              log.info("리뷰가 없습니다.");
          }
      }


      log.info("총 리뷰 = " + reviews);













        DiaryShowDTO.Response showDTO = DiaryShowDTO.Response.fromEntity(diary,reviews);


        return showDTO;

    }

}
