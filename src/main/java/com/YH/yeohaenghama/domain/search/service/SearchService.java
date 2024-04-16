package com.YH.yeohaenghama.domain.search.service;

import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.openApi.service.OpenApiService;
import com.YH.yeohaenghama.domain.search.dto.SearchDTO;
import com.YH.yeohaenghama.domain.search.dto.SearchDiaryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {
    private final OpenApiService openApiService;
    private final DiaryRepository diaryRepository;
    private final ItineraryRepository itineraryRepository;

    public void federated(SearchDTO.Request dto){

    }

    public List<SearchDiaryDTO> serachTitle(SearchDTO.Request dto){
        List<SearchDiaryDTO> searchDiaryDTOList = new ArrayList<>();

        List<Diary> diaryOpt = diaryRepository.findByTitle(dto.getKeyWord());

        if (!diaryOpt.isEmpty()) {
            for (Diary diary : diaryOpt) {
                searchDiaryDTOList.add(SearchDiaryDTO.fromEntity(diary));
            }
        }

        return searchDiaryDTOList;

    }
}
