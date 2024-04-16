package com.YH.yeohaenghama.domain.search.service;

import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.PlaceRepository;
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
    private final PlaceRepository placeRepository;

    public SearchDTO.Response federated(SearchDTO.Request dto){
        return SearchDTO.Response.setSearch(serachTitle(dto),serachContent(dto),serachPlace(dto));
    }

    public List<SearchDiaryDTO> serachTitle(SearchDTO.Request dto){
        List<SearchDiaryDTO> searchDiaryDTOList = new ArrayList<>();

        List<Diary> diaryList = diaryRepository.findByTitle(dto.getKeyWord());

        if (!diaryList.isEmpty()) {
            for (Diary diary : diaryList) {
                searchDiaryDTOList.add(SearchDiaryDTO.fromEntity(diary));
            }
        }

        return searchDiaryDTOList;

    }

    public List<SearchDiaryDTO> serachContent(SearchDTO.Request dto){
        List<SearchDiaryDTO> searchDiaryDTOList = new ArrayList<>();

        List<Diary> diaryList = diaryRepository.findByContentContaining(dto.getKeyWord());

        if (!diaryList.isEmpty()) {
            for (Diary diary : diaryList) {
                searchDiaryDTOList.add(SearchDiaryDTO.fromEntity(diary));
            }
        }

        return searchDiaryDTOList;

    }

    public List<SearchDiaryDTO> serachPlace(SearchDTO.Request dto){
        List<SearchDiaryDTO> searchDiaryDTOList = new ArrayList<>();

        List<Place> placeList = placeRepository.findByPlaceName(dto.getKeyWord());

        if(!placeList.isEmpty()){
            for (Place place : placeList){
                Optional<Diary> diaryOpt = diaryRepository.findByItinerary(place.getItinerary().getId());
                if(!diaryOpt.isEmpty()) {
                    searchDiaryDTOList.add(SearchDiaryDTO.fromEntity(diaryOpt.get()));
                }
            }
        }


        return searchDiaryDTOList;

    }
}
