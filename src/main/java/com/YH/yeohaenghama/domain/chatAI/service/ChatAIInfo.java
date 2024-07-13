package com.YH.yeohaenghama.domain.chatAI.service;

import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDTO;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDiary;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIPopularArea;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.PlaceRepository;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatAIInfo {
    private final DiaryRepository diaryRepository;
    private final ItineraryRepository itineraryRepository;
    private final PlaceRepository placeRepository;

    LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
    int maxDistance = 3; // 허용할 최대 Levenshtein 거리



    public ChatAIDTO.Response check(String question,String answer, String type,List<Map.Entry<String, Double>> sortedList){
        ChatAIDTO.Response response = ChatAIDTO.Response.toResponse(question, answer, type, sortedList);
        String keyword = "";


        if(type == null){}
        else if (type.equals("showDiaryAll")) {
            response.setResult(showDiaryAll());
        }
        else if (type.equals("showDiaryTitle")){
            keyword = selectKeyword(question);
            System.out.println("Keyword : " +keyword);
            response.setResult(showDiaryTitle((keyword)));
        }
        else if (type.equals("showDiaryPlace")){
            keyword = selectKeyword(question);
            response.setResult(showDiaryPlace(keyword));
        }
        else if (type.equals("showDiaryArea")){
            keyword = selectKeyword(question);
            response.setResult(showDiaryArea(keyword));
        }
        else if (type.equals("showPopularArea")){
            response.setResult(showPopularArea());
        }
        else if (type.equals("fail")){
            response.fail();
        }

        return response;
    }



    public ChatAIDiary.Response showDiaryAll() {
        List<Diary> diaryList = diaryRepository.findAll();
        int size = Math.min(diaryList.size(), 3);
        return ChatAIDiary.Response.toEntity(diaryList.subList(0, size),"일기 전체 검색");
    }

    public ChatAIDiary.Response showDiaryTitle(String keyword){
        List<Diary> diaryList = diaryRepository.findByTitleContaining(keyword);
        int size = Math.min(diaryList.size(), 3);
        return ChatAIDiary.Response.toEntity(diaryList.subList(0, size),keyword);
    }

    public ChatAIDiary.Response showDiaryPlace(String keyword){
        List<Diary> diaryList = new ArrayList<>();
        List<Place> placeList = placeRepository.findByPlaceNameContaining(keyword);

        for(Place place : placeList){
            diaryRepository.findByItinerary(place.getItinerary().getId())
                    .ifPresent(diaryList::add);
        }

        int size = Math.min(diaryList.size(), 3);
        return ChatAIDiary.Response.toEntity(diaryList.subList(0, size),keyword);
    }

    public ChatAIDiary.Response showDiaryArea(String keyword){
        List<Diary> diaryList = diaryRepository.findAll();
        List<Diary> response = new ArrayList<>();

        for(Diary diary : diaryList){
            Itinerary itinerary = itineraryRepository.findById(diary.getItinerary()).orElse(null);
            if(itinerary == null) continue;
            int distance = getDistance(itinerary.getArea(),keyword);
            if(distance <= maxDistance) response.add(diary);
        }
        int size = Math.min(response.size(), 3);
        return ChatAIDiary.Response.toEntity(response.subList(0, size),keyword);
    }

    public ChatAIPopularArea.Response showPopularArea(){
        List<Itinerary> itineraryList = itineraryRepository.findAll();
        return ChatAIPopularArea.Response.ranking(itineraryList);
    }


    public int getDistance(String keyword1, String keyword2){
        return levenshteinDistance.apply(keyword1, keyword2);
    }

    public String selectKeyword(String question){

        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
        KomoranResult analyzeResultList = komoran.analyze(question);
        List<Token> tokenList = analyzeResultList.getTokenList();

        String keyword = "";
        for (Token token : tokenList) {
            if (token.getPos().equals("NNP")) {
                System.out.format(token.getMorph());
                keyword = token.getMorph();
            }
        }

        return keyword;
    }
}
