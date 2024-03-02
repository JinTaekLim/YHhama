package com.YH.yeohaenghama.domain.diary.service;

import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final ItineraryRepository itineraryRepository;

    public DiaryDTO.Response save(DiaryDTO.Request req) {

        if(itineraryRepository.findById(req.getItinerary()).isEmpty()){
            log.info("해당 ID를 가진 일정이 존재하지 않습니다. : " + req.getItinerary());
            throw new NoSuchElementException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + req.getItinerary());
        };

        DiaryDTO diaryDTO = new DiaryDTO(req);
        Diary diary = diaryRepository.save(diaryDTO.toEntity());

        return DiaryDTO.Response.fromEntity(diary);

    }
}
