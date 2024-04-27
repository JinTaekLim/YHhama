package com.YH.yeohaenghama.domain.diary.service;

import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
import com.YH.yeohaenghama.domain.diary.dto.DiaryShowDTO;
import com.YH.yeohaenghama.domain.diary.dto.DiaryShowInPlaceDTO;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.entity.DiaryPhotoUrl;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.GCDImage.service.GCSService;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.PlaceRepository;
import com.YH.yeohaenghama.domain.itinerary.service.PlaceService;
import com.YH.yeohaenghama.domain.review.dto.ReviewDTO;
import com.YH.yeohaenghama.domain.review.entity.Review;
import com.YH.yeohaenghama.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private final PlaceService placeService;

    public DiaryDTO.Response save(DiaryDTO.Request diaryDTO) throws IOException {

        if(itineraryRepository.findById(diaryDTO.getItinerary()).isEmpty()){
           throw new NoSuchElementException("해당 ID를 가진 일정이 존재하지 않습니다.");
        }
        if(!diaryRepository.findByItinerary(diaryDTO.getItinerary()).isEmpty()){
            throw new Error("이미 등록 되어있는 일기가 존재합니다.");
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

        diary.set(diaryDTO.getTitle(), diaryDTO.getContent(), diaryDTO.getItinerary(), diaryPhotoUrls);

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

        DiaryShowDTO.Response showDTO = DiaryShowDTO.Response.fromEntity(diary, reviewsByDate,itinerary.getAccount());

        showDTO.setTag(addTag(itinerary));

        return showDTO;

    }


    public List<String> addTag(Itinerary itinerary){
        List<String> tag = new ArrayList<>();
        String month = itinerary.getStartDate().getMonthValue() + "월출발";

        long day = ChronoUnit.DAYS.between(itinerary.getStartDate(), itinerary.getEndDate());

        tag.add(itinerary.getArea());

        if (day == 1){
            tag.add("당일치기");
        }else {
            tag.add(day-1 + "박" + day +"일");
        }

        tag.add(month);
        tag.addAll(itinerary.getItineraryStyle());
        tag.addAll(itinerary.getType());

        return tag;
    }

    public List<DiaryShowDTO.AccountResponse> findAccountDiary(Long accountId){
        List<Itinerary> itineraryList = itineraryRepository.findByAccountId(accountId);

        if(itineraryList.isEmpty()){
            throw new NoSuchElementException("해당 ID를 가진 일정이 존재하지 않습니다.");
        }

        List<DiaryShowDTO.AccountResponse> response = new ArrayList<>();

        for(Itinerary itinerary : itineraryList){
            Optional<Diary> diaryOpt = diaryRepository.findByItinerary(itinerary.getId());
            if(!diaryOpt.isEmpty()){
                DiaryShowDTO.AccountResponse accountResponse = DiaryShowDTO.AccountResponse.fromEntity(diaryOpt.get(),itinerary.getAccount());
                accountResponse.setTag(addTag(itinerary));

                response.add(accountResponse);

            }
        }

        return response;


    }

    public DiaryDTO.Response updatae(Long diaryId, DiaryDTO.Request dto) throws IOException {
        Optional<Diary> diaryOpt = diaryRepository.findById(diaryId);
        Diary diary = diaryOpt.get();


        gcsService.delete("Diary/" + dto.getItinerary());

        List<DiaryPhotoUrl> diaryPhotoUrls = new ArrayList<>();
        List<MultipartFile> photos = dto.getPhotos();

        if (photos != null) {
            int i = 0;
            for (MultipartFile photo : photos) {
                String photoUrl = gcsService.uploadPhoto(photo, String.valueOf(i), "Diary/" + dto.getItinerary());
                DiaryPhotoUrl diaryPhotoUrl = new DiaryPhotoUrl(diary, photoUrl);
                diaryPhotoUrls.add(diaryPhotoUrl);
                i++;
            }
        }

        diary.update(dto.getTitle(), dto.getContent(), diaryPhotoUrls);
        diaryRepository.save(diary);

        return DiaryDTO.Response.fromEntity(diary);
    }

    public List<DiaryShowDTO.AccountResponse> findAll(){
        List<DiaryShowDTO.AccountResponse> responses = new ArrayList<>();
        List<Diary> diaryList = diaryRepository.findAll();

        for(Diary diary : diaryList){
            Optional<Itinerary> itineraryOpt = itineraryRepository.findById(diary.getItinerary());
            DiaryShowDTO.AccountResponse accountResponse = DiaryShowDTO.AccountResponse.fromEntity(diary,itineraryOpt.get().getAccount());
            if(!itineraryOpt.isEmpty()){
                accountResponse.setTag(addTag(itineraryOpt.get()));
            }
            responses.add(accountResponse);

        }

        return responses;
    }


    public List<DiaryShowInPlaceDTO.Response> findInPlace(DiaryShowInPlaceDTO.Request dto){
        List<Long> itineraryNum = placeService.checkPlace(dto.getPlaceNum(),dto.getTypeNum());

        List<DiaryShowInPlaceDTO.Response> DiaryList = new ArrayList<>();

        for(Long id : itineraryNum){

            log.info("id 값 = " + id);

            Optional<Place> placeOpt = placeRepository.findById(id);

            Optional<Diary> diaryOpt = diaryRepository.findByItinerary(placeOpt.get().getItinerary().getId());


            if(!diaryOpt.isEmpty()) {
                Diary diary = diaryOpt.get();

                String photo = "";

                if(!diary.getDiaryPhotoUrls().isEmpty()){
                    photo = String.valueOf(diary.getDiaryPhotoUrls().get(0).getPhotoURL());
                    log.info("사진 = " + photo);
                }
                DiaryShowInPlaceDTO.Response diaryShowInPlaceDTO = new DiaryShowInPlaceDTO.Response().fromEntity(diary,photo);

                DiaryList.add(diaryShowInPlaceDTO);

            }else {
                log.info("해당 일정의 일기가 존재하지 않습니다.");
            }

        }

        return DiaryList;
    }
}
