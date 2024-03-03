package com.YH.yeohaenghama.domain.diary.service;

import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.entity.DiaryPhotoUrl;
import com.YH.yeohaenghama.domain.diary.repository.DiaryPhotoUrlRepository;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final ItineraryRepository itineraryRepository;
    private final DiaryPhotoUrlRepository diaryPhotoUrlRepository;

    public DiaryDTO.Response save(DiaryDTO.Request req) {
        log.info("DiaryDTO　확인 : " + req);
        checkItinerary(req.getItinerary());

        DiaryDTO diaryDTO = new DiaryDTO(req);
        Diary diary = diaryRepository.save(diaryDTO.toEntity());

        for (String photoUrl : req.getPhotoURL()) {
            DiaryPhotoUrl diaryPhotoUrl = DiaryPhotoUrl.builder()
                    .diary(diary)
                    .photoUrl(photoUrl)
                    .build();
            diaryPhotoUrlRepository.save(diaryPhotoUrl);
        }

        return DiaryDTO.Response.fromEntity(diary);
    }

    public void delete(Long diaryId){
        log.info("Diary Id : " + diaryId);
        log.info(String.valueOf(" 확인 : " +checkDiary(diaryId)));
        diaryRepository.deleteById(diaryId);
    }

    public DiaryDTO.Response checkDiary(Long diaryId){
        Optional<Diary> diaryOptional = diaryRepository.findById(diaryId);

        if(diaryOptional.isEmpty()){
            log.info("해당 ID를 가진 일기가 존재하지 않습니다. : " + diaryId);
            throw new NoSuchElementException("해당 ID를 가진 일기가 존재하지 않습니다. : " + diaryId);
        }

        Diary diary = diaryOptional.get();

        return DiaryDTO.Response.fromEntity(diary);
    }


//    public void checkItineraryId(DiaryDTO.Request req){
//        if(itineraryRepository.findById(req.getItinerary()).isEmpty()){
//            log.info("해당 ID를 가진 일정이 존재하지 않습니다. : " + req.getItinerary());
//            throw new NoSuchElementException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + req.getItinerary());
//        };
//    }

    public boolean checkItinerary(Long itineraryId) {
        return itineraryRepository.findById(itineraryId).isPresent();
    }



}
