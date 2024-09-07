package com.YH.yeohaenghama.domain.chatAI.service;

import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDTO;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDiary;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIItinerary;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIItinerary.ItineraryShow;
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
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
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
    int maxDistance = 3;



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
            String area = validateArea(keyword);
            if(area == null){
                response.setResult(showDiaryPlace(keyword));
            }else {
                response.setResult(showDiaryArea(keyword));
            }
        }
        else if (type.equals("showPopularArea")){
            response.setResult(showPopularArea());
        }
        else if (type.equals("showItineraryAll")){
            response.setResult(showItineraryAll());
        }
        else if (type.equals("showItineraryArea")){
            keyword = selectKeyword(question);
            response.setResult(showItineraryArea(keyword));
        }
        else if (type.equals("directions")){
            keyword = selectKeyword(question);
            response.setResult(keyword);
            response.setType("directions");

        }
        else if (type.equals("fail") || type.equals("classifying")){
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

    public ChatAIItinerary.Response showItineraryAll(){
        List<Itinerary> itineraryList = itineraryRepository.findAll();
        return ChatAIItinerary.Response.toEntity(itineraryList,"일정 전체 조회");
    }
    public ChatAIItinerary.Response showItineraryArea(String keyword){
        List<Itinerary> itineraryList = itineraryRepository.findAll();
        for (Itinerary itinerary : itineraryList) {
            if (keyword.equals(itinerary.getArea())) itineraryList.remove(itinerary);
        }
        return ChatAIItinerary.Response.toEntity(itineraryList,keyword);
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

    public String validateArea(String keyword) {

        final double THRESHOLD = 0.85;
        String response = null;


        List<String> area = Arrays.asList(
                "서울특별시", "서울",
                "부산광역시", "부산",
                "대구광역시", "대구",
                "인천광역시", "인천",
                "광주광역시", "광주",
                "대전광역시", "대전",
                "울산광역시", "울산",
                "경기도", "경기",
                "강원도", "강원",
                "충청북도", "충북",
                "충청남도", "충남",
                "전라북도", "전북",
                "전라남도", "전남",
                "경상북도", "경북",
                "경상남도", "경남",
                "제주도", "제주"
        );

        if (area.contains(keyword)) return keyword;

        JaroWinklerSimilarity jaroWinkler = new JaroWinklerSimilarity();
        for (String areaName : area) {
            double similarity = jaroWinkler.apply(keyword, areaName);
            if (similarity >= THRESHOLD) {
                response = areaName;
            }
        }

        return response;
    }

}
