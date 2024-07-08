package com.YH.yeohaenghama.domain.chatAI.service;

import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDiary;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatAIInfo {
    private final DiaryRepository diaryRepository;
    private final ItineraryRepository itineraryRepository;
    private final PlaceRepository placeRepository;

    public ChatAIDiary.Response showDiaryAll() {
        List<Diary> diaryList = diaryRepository.findAll();
        int size = Math.min(diaryList.size(), 3);
        return ChatAIDiary.Response.toEntity(diaryList.subList(0, size));
    }

    public ChatAIDiary.Response showDiaryTitle(String keyword){
        List<Diary> diaryList = diaryRepository.findByTitleContaining(keyword);
        int size = Math.min(diaryList.size(), 3);
        return ChatAIDiary.Response.toEntity(diaryList.subList(0, size));
    }

    public ChatAIDiary.Response showDiaryPlace(String keyword){
        List<Diary> diaryList = new ArrayList<>();
        List<Place> placeList = placeRepository.findByPlaceNameContaining(keyword);

        for(Place place : placeList){
            diaryRepository.findByItinerary(place.getItinerary().getId())
                    .ifPresent(diaryList::add);
        }

        int size = Math.min(diaryList.size(), 3);
        return ChatAIDiary.Response.toEntity(diaryList.subList(0, size));
    }
}
