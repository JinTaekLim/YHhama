package com.YH.yeohaenghama.domain.chatAI.service;

import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDTO;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDiary;
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

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatAIInfo {
    private final DiaryRepository diaryRepository;
    private final ItineraryRepository itineraryRepository;
    private final PlaceRepository placeRepository;

    LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
    int maxDistance = 3; // 허용할 최대 Levenshtein 거리



    public ChatAIDTO.Response check(String question,String answer,List<Map.Entry<String, Double>> sortedList){
        ChatAIDTO.Response response = ChatAIDTO.Response.toResponse(question, answer, sortedList);
        String keyword = "";



        if (answer.equals("showDiaryAll")) {
            response.setTypeAndResult(answer,showDiaryAll(),"전체 일기 조회");
        }
        else if (answer.equals("showDiaryTitle")){
            keyword = selectKeyword(question);
            System.out.println("Keyword : " +keyword);
            response.setTypeAndResult(answer,showDiaryTitle(keyword),"["+ keyword + "] 제목이 포함된 일기 검색");
        }
        else if (answer.equals("showDiaryPlace")){
            keyword = selectKeyword(question);
            response.setTypeAndResult(answer,showDiaryPlace(keyword),"[" + keyword + "] 장소가 포함된 일기 검색");
        }
        else if (answer.equals("showDiaryArea")){
            keyword = selectKeyword(question);
            response.setTypeAndResult(answer,showDiaryArea(keyword),"[" + keyword + "] 지역의 일기 검색");
        }
        else if (answer.equals("showPopularArea")){
            response.setTypeAndResult(answer,showPopularArea(), "인기 지역 조회");
        }
        else if (answer.equals("fail")){
            response.fail();
        }

        return response;
    }



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
        return ChatAIDiary.Response.toEntity(response.subList(0, size));
    }

    public List<String> showPopularArea(){
        List<String> response = new ArrayList<>();

        Map<String, Integer> areaCountMap = new HashMap<>();
        List<Itinerary> itineraryList = itineraryRepository.findAll();
        for (Itinerary itinerary : itineraryList){
            String area = itinerary.getArea();

            if (areaCountMap.containsKey(area)) {
                int count = areaCountMap.get(area);
                areaCountMap.put(area, count + 1);
            } else {
                areaCountMap.put(area, 1);
            }
        }

        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(areaCountMap.entrySet());
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        if (!sortedEntries.isEmpty()) {
            response.add(sortedEntries.get(0).getKey());
            System.out.println("1. " + sortedEntries.get(0).getKey() + " : " + sortedEntries.get(0).getValue());
            if (sortedEntries.size() > 1) {
                System.out.println("2. " + sortedEntries.get(1).getKey() + " : " + sortedEntries.get(1).getValue());
                response.add(sortedEntries.get(1).getKey());
            }
            if (sortedEntries.size() > 2) {
                System.out.println("3. " + sortedEntries.get(2).getKey() + " : " + sortedEntries.get(2).getValue());
                response.add(sortedEntries.get(2).getKey());
            }
        } else {
            System.out.println("일정이 없거나 모든 일정의 지역이 동일한 경우");
        }

        return response;
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
