package com.YH.yeohaenghama.domain.search.service;

import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.openApi.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {
    private final OpenApiService openApiService;
    private final DiaryRepository diaryRepository;
    private final ItineraryRepository itineraryRepository;

    public void federated(String keyWord){


    }
}
