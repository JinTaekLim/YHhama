package com.YH.yeohaenghama.domain.search.service;

import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.diary.service.DiaryService;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.PlaceRepository;
import com.YH.yeohaenghama.domain.openApi.dto.OpenApiAreaDTO;
import com.YH.yeohaenghama.domain.openApi.service.OpenApiService;
import com.YH.yeohaenghama.domain.search.dto.SearchDTO;
import com.YH.yeohaenghama.domain.search.dto.SearchDiaryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final DiaryService diaryService;

    public SearchDTO.Response federated(SearchDTO.Request dto) throws Exception {

        OpenApiAreaDTO openApiAreaDTO = new OpenApiAreaDTO();
        openApiAreaDTO.setPage(String.valueOf(dto.getPage()));
        openApiAreaDTO.setKeyword(dto.getKeyWord());
        openApiAreaDTO.setMobileOS("ETC");
        openApiAreaDTO.setContentTypeId("12");
        openApiAreaDTO.setNumOfRows(String.valueOf(dto.getNumOfRows()));


        List<OpenApiAreaDTO.Response.Body.Items.Item> searchPlace =
                openApiService.searchAreaAndGetResponse(openApiAreaDTO);

        return SearchDTO.Response.setSearch(
                searchTitle(dto),
                searchContent(dto),
                serachPlace(dto),
                searchArea(dto),
                searchPlace != null? searchPlace : new ArrayList<>()
        );
    }

    public SearchDiaryDTO.Response searchTitle(SearchDTO.Request dto){
        Pageable pageable = PageRequest.of(dto.getPage(),dto.getNumOfRows(), Sort.by("id").ascending());
        SearchDiaryDTO.Response searchDiaryDTO = new SearchDiaryDTO.Response();
        List<SearchDiaryDTO> searchDiaryDTOList = new ArrayList<>();

        Page<Diary> diaryList = diaryRepository.findByTitleContaining(dto.getKeyWord(),pageable);

        if (!diaryList.isEmpty()) {
            for (Diary diary : diaryList) {
                Optional<Itinerary> itineraryOpt = itineraryRepository.findById(diary.getItinerary());
                SearchDiaryDTO searchReponse = SearchDiaryDTO.fromEntity(diary, itineraryOpt.get().getAccount());
                searchReponse.setTag(diaryService.addTag(itineraryOpt.get()));
                searchDiaryDTOList.add(searchReponse);
            }
        }

        searchDiaryDTO.setSearchDiaryDTOS(searchDiaryDTOList);
        searchDiaryDTO.setPageNum(pageable.getPageNumber());
        searchDiaryDTO.setTotalPage(diaryList.getTotalPages());
        return searchDiaryDTO;

    }

    public SearchDiaryDTO.Response searchContent(SearchDTO.Request dto){
        Pageable pageable = PageRequest.of(dto.getPage(),dto.getNumOfRows(), Sort.by("id").ascending());
        SearchDiaryDTO.Response searchDiaryDTO = new SearchDiaryDTO.Response();
        List<SearchDiaryDTO> searchDiaryDTOList = new ArrayList<>();

        Page<Diary> diaryList = diaryRepository.findByContentContaining(dto.getKeyWord(),pageable);

        if (!diaryList.isEmpty()) {
            for (Diary diary : diaryList) {
                Optional<Itinerary> itineraryOpt = itineraryRepository.findById(diary.getItinerary());
                SearchDiaryDTO searchReponse = SearchDiaryDTO.fromEntity(diary, itineraryOpt.get().getAccount());
                searchReponse.setTag(diaryService.addTag(itineraryOpt.get()));
                searchDiaryDTOList.add(searchReponse);
            }
        }

        searchDiaryDTO.setSearchDiaryDTOS(searchDiaryDTOList);
        searchDiaryDTO.setPageNum(pageable.getPageNumber());
        searchDiaryDTO.setTotalPage(diaryList.getTotalPages());


        return searchDiaryDTO;

    }

    public SearchDiaryDTO.Response serachPlace(SearchDTO.Request dto){
        Pageable pageable = PageRequest.of(dto.getPage(),dto.getNumOfRows(), Sort.by("id").ascending());
        SearchDiaryDTO.Response searchDiaryDTO = new SearchDiaryDTO.Response();
        List<SearchDiaryDTO> searchDiaryDTOList = new ArrayList<>();

        Page<Place> placeList = placeRepository.findByPlaceNameContaining(dto.getKeyWord(),pageable);

//        log.info(placeList.toString());
        if(!placeList.isEmpty()){
            for (Place place : placeList){
//                log.info(String.valueOf(place.getId()));
                Optional<Diary> diaryOpt = diaryRepository.findByItinerary(place.getItinerary().getId());
                if(!diaryOpt.isEmpty()) {
                    SearchDiaryDTO searchReponse = SearchDiaryDTO.fromEntity(diaryOpt.get(), place.getItinerary().getAccount());
                    searchReponse.setTag(diaryService.addTag(place.getItinerary()));
                    searchDiaryDTOList.add(searchReponse);
//                    log.info(String.valueOf(diaryOpt.get().getId()));
                }
            }
        }

        searchDiaryDTO.setSearchDiaryDTOS(searchDiaryDTOList);
        searchDiaryDTO.setPageNum(pageable.getPageNumber());
        searchDiaryDTO.setTotalPage(placeList.getTotalPages());


        return searchDiaryDTO;

    }


    public SearchDiaryDTO.Response searchArea(SearchDTO.Request dto){
        Pageable pageable = PageRequest.of(dto.getPage(),dto.getNumOfRows(), Sort.by("id").ascending());

        SearchDiaryDTO.Response searchDiaryDTO = new SearchDiaryDTO.Response();
        List<SearchDiaryDTO> searchDiaryDTOList = new ArrayList<>();

        Page<Itinerary> itineraryList = itineraryRepository.findByArea(dto.getKeyWord(),pageable);

//        log.info(itineraryList.toString());
        if(!itineraryList.isEmpty()){
            for (Itinerary itinerary : itineraryList){
//                log.info(String.valueOf(itinerary.getId()));
                Optional<Diary> diaryOpt = diaryRepository.findByItinerary(itinerary.getId());
                if(!diaryOpt.isEmpty()){
                    SearchDiaryDTO searchReponse = SearchDiaryDTO.fromEntity(diaryOpt.get(), itinerary.getAccount());
                    searchReponse.setTag(diaryService.addTag(itinerary));
                    searchDiaryDTOList.add(searchReponse);
                }
            }
        }

        searchDiaryDTO.setSearchDiaryDTOS(searchDiaryDTOList);
        searchDiaryDTO.setPageNum(pageable.getPageNumber());
        searchDiaryDTO.setTotalPage(itineraryList.getTotalPages());

        return searchDiaryDTO;

    }

    public SearchDiaryDTO.Response findAll() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("id").ascending());

        SearchDiaryDTO.Response searchDiaryDTO = new SearchDiaryDTO.Response();
        List<SearchDiaryDTO> searchDiaryDTOList = new ArrayList<>();
        Page<Itinerary> itineraries = itineraryRepository.findAll(pageable);

        for(Itinerary itinerary : itineraries){
            log.info(String.valueOf(itinerary.getId()));
            Optional<Diary> diaryOpt = diaryRepository.findByItinerary(itinerary.getId());
            if(!diaryOpt.isEmpty()) {
                SearchDiaryDTO searchReponse = SearchDiaryDTO.fromEntity(diaryOpt.get(), itinerary.getAccount());
                searchReponse.setTag(diaryService.addTag(itinerary));
                searchDiaryDTOList.add(searchReponse);
            }
        }

        searchDiaryDTO.setSearchDiaryDTOS(searchDiaryDTOList);

        return searchDiaryDTO;
    }
}
