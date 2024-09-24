package com.YH.yeohaenghama.domain.chatAI.service;

import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDTO;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDiary;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIItinerary;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIPopularArea;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIPopularPlace;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIShowDTO;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.PlaceRepository;
import com.YH.yeohaenghama.domain.openApi.dto.SearchAreaDTO;
import com.YH.yeohaenghama.domain.shorts.entity.Shorts;
import com.YH.yeohaenghama.domain.shorts.repository.ShortsRepository;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatAIInfo {
    private final DiaryRepository diaryRepository;
    private final ItineraryRepository itineraryRepository;
    private final PlaceRepository placeRepository;
    private final ShortsRepository shortsRepository;


    public ChatAIDTO.Response check(String question,String answer, String type,List<Map.Entry<String, Double>> sortedList){
        System.out.println("check");
        ChatAIDTO.Response response = ChatAIDTO.Response.toResponse(question, answer, type, sortedList);
        String keyword = "";

        System.out.println("start");
        if ("message".equals(type)) {
            response.setResult(null);
        } else if ("directions".equals(type)) {
            response.setResult(getKeyword(question));
        } else if ("popularArea".equals(type)) {
            response.setResult(popularArea());
        } else if ("popularPlace".equals(type)) {
            response.setResult(popularPlace());
        } else if ("searchShorts".equals(type)) {
            response.setResult(searchShorts(getKeyword(question)));
        } else if ("searchItinerary".equals(type)) {
            response.setResult(searchItinerary(getKeyword(question)));
        } else if ("searchDiary".equals(type)) {
            response.setResult(searchDiary(getKeyword(question)));
        } else if ("searchPlace".equals(type)) {
            response.setResult(searchPlace(getKeyword(question)));
        } else if ("randomPlace".equals(type)) {
            response.setResult(randomPlace());
        } else if ("randomShorts".equals(type)) {
            response.setResult(randomShorts());
        } else if ("randomItinerary".equals(type)) {
            response.setResult(randomItinerary());
        } else if ("randomDiary".equals(type)) {
            response.setResult(randomDiary());
        }


        return response;
    }

    public ChatAIPopularArea.Response popularArea() {
        List<Itinerary> itineraryList = itineraryRepository.findAll();
        if (itineraryList.isEmpty()) return null;
        return ChatAIPopularArea.Response.ranking(itineraryList);
    }

    public ChatAIPopularPlace.Response popularPlace() {
        List<Place> placeList = placeRepository.findAll();
        if (placeList.isEmpty()) return null;
        return ChatAIPopularPlace.Response.recommend(placeList);
    }

    public List<ChatAIShowDTO.ShortsResponse> searchShorts(String keyword){
        List<Shorts> shortsContentList = shortsRepository.findByContent(keyword);
        List<Shorts> shortsTitleLIst = shortsRepository.findByTitle(keyword);
        if( shortsContentList.isEmpty() && shortsTitleLIst.isEmpty()) return null;
        return ChatAIShowDTO.ShortsResponse.ofList(shortsContentList, shortsTitleLIst);
    }
//
    public List<ChatAIShowDTO.ItineraryResponse> searchItinerary(String keyword) {
//        List<Itinerary> itineraryAreaList = itineraryRepository.findByArea(keyword);
        List<Itinerary> itineraryNameList = itineraryRepository.findByName(keyword);
        if (itineraryNameList.isEmpty()) return null;
        return ChatAIShowDTO.ItineraryResponse.ofList(itineraryNameList);
    }

    public List<ChatAIShowDTO.DiaryResponse> searchDiary(String keyword) {
        List<Diary> diaryList = diaryRepository.findByTitleContaining(keyword);
        if (diaryList.isEmpty()) return null;
        return ChatAIShowDTO.DiaryResponse.ofList(diaryList);
    }

    public List<ChatAIShowDTO.PlaceResponse> searchPlace(String keyword) {
        List<Place> placeList = placeRepository.findByPlaceNameContaining(keyword);
        if (placeList.isEmpty()) return null;
        return ChatAIShowDTO.PlaceResponse.ofList(placeList);
    }

    private final Random random = new Random();

    public ChatAIShowDTO.PlaceResponse randomPlace(){
        List<Place> placeList = placeRepository.findAll();
        if (placeList.isEmpty()) return null;
        int randomId = random.nextInt(0,placeList.size());
        return ChatAIShowDTO.PlaceResponse.of(placeList.get(randomId));
    }

    public ChatAIShowDTO.ShortsResponse randomShorts(){
        List<Shorts> shortsList = shortsRepository.findAll();
        if (shortsList.isEmpty()) return null;
        int randomId = random.nextInt(0,shortsList.size());
        return ChatAIShowDTO.ShortsResponse.of(shortsList.get(randomId));
    }

    public ChatAIShowDTO.ItineraryResponse randomItinerary() {
        List<Itinerary> itineraryList = itineraryRepository.findAll();
        if (itineraryList.isEmpty()) return null;
        int randomId = random.nextInt(0,itineraryList.size());
        return ChatAIShowDTO.ItineraryResponse.of(itineraryList.get(randomId));
    }

    public ChatAIShowDTO.DiaryResponse randomDiary() {
        List<Diary> diaryList = diaryRepository.findAll();
        if (diaryList.isEmpty()) return null;
        int randomId = random.nextInt(0,diaryList.size());
        return ChatAIShowDTO.DiaryResponse.of(diaryList.get(randomId));

    }


    public String getKeyword(String question){

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
