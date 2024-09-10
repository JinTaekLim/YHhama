package com.YH.yeohaenghama.domain.chatAI.dto;

import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.shorts.entity.Shorts;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;

public class ChatAIShowDTO {

  @Data
  public static class AreaResponse{
    private String area;
    private String url;

    public AreaResponse(String area){
      this.area = area;

      if (area.equals("서울특별시") || area.equals("서울")){
        this.url = "https://storage.googleapis.com/yhhama_sjg/Area/%E1%84%89%E1%85%A5%E1%84%8B%E1%85%AE%E1%86%AF.png";
      }
      else if (area.equals("대구광역시") || area.equals("대구")){
        this.url = "https://storage.googleapis.com/yhhama_sjg/Area/daegu.jpeg";
      }
      else if (area.equals("부산광역시") || area.equals("부산")){
        this.url = "https://storage.googleapis.com/yhhama_sjg/Area/%E1%84%87%E1%85%AE%E1%84%89%E1%85%A1%E1%86%AB.jpeg";
      }
      else if (area.equals("인천광역시") || area.equals("인천")){
        this.url = "https://storage.googleapis.com/yhhama_sjg/Area/incheon.jpg";
      }
      else if (area.equals("광주광역시") || area.equals("광주")){
        this.url = "https://storage.googleapis.com/yhhama_sjg/Area/%E1%84%80%E1%85%AA%E1%86%BC%E1%84%8C%E1%85%AE.jpeg";
      }
      else if (area.equals("대전광역시") || area.equals("대전")){
        this.url = "https://storage.googleapis.com/yhhama_sjg/Area/%E1%84%83%E1%85%A2%E1%84%8C%E1%85%A5%E1%86%AB.jpeg";
      }
      else if (area.equals("울산광역시") || area.equals("울산")){
        this.url = "https://storage.googleapis.com/yhhama_sjg/Area/%E1%84%8B%E1%85%AE%E1%86%AF%E1%84%89%E1%85%A1%E1%86%AB.png";
      }
      else if (area.equals("경기도") || area.equals("경기")){
        this.url = "https://storage.googleapis.com/yhhama_sjg/Area/%E1%84%80%E1%85%A7%E1%86%BC%E1%84%80%E1%85%B5.jpg";
      }
      else if (area.equals("강원도") || area.equals("강원")){
        this.url = "https://storage.googleapis.com/yhhama_sjg/Area/%E1%84%80%E1%85%A1%E1%86%BC%E1%84%8B%E1%85%AF%E1%86%AB.jpg";
      }
      else if (area.equals("충청북도") || area.equals("충북")){
        this.url = "https://storage.googleapis.com/yhhama_sjg/Area/%E1%84%8E%E1%85%AE%E1%86%BC%E1%84%87%E1%85%AE%E1%86%A8.jpg";
      }
      else if (area.equals("충청남도") || area.equals("충남")){
        this.url = "https://storage.googleapis.com/yhhama_sjg/Area/%E1%84%8E%E1%85%AE%E1%86%BC%E1%84%82%E1%85%A1%E1%86%B7.jpg";
      }
      else if (area.equals("전라북도") || area.equals("전북")){
        this.url = "https://storage.googleapis.com/yhhama_sjg/Area/%E1%84%8C%E1%85%A5%E1%86%AB%E1%84%87%E1%85%AE%E1%86%A8.jpg";
      }
      else if (area.equals("전라남도") || area.equals("전남")){
        this.url = "https://storage.googleapis.com/yhhama_sjg/Area/%E1%84%8C%E1%85%A5%E1%86%AB%E1%84%82%E1%85%A1%E1%86%B7.jpg";
      }
      else if (area.equals("제주도") || area.equals("제주")){
        this.url = "https://storage.googleapis.com/yhhama_sjg/Area/%E1%84%8C%E1%85%A6%E1%84%8C%E1%85%AE.png";
      }
    }

  }
  @Data
  public static class ShortsResponse{
    private Long id;
    private String title;
    private String video_url;

    public static ShortsResponse of(Shorts shorts){
      ShortsResponse response = new ShortsResponse();
      response.setId(shorts.getId());
      response.setTitle(shorts.getTitle());
      response.setVideo_url(shorts.getVideoUrl());
      return response;
    }
    public static List<ShortsResponse> ofList(List<Shorts> shorts1 , List<Shorts> shorts2) {
      List<ShortsResponse> response = new ArrayList<>();
      Set<Shorts> uniqueShorts = new HashSet<>();
      uniqueShorts.addAll(shorts1);
      uniqueShorts.addAll(shorts2);

      for (Shorts shortItem : uniqueShorts) {
        ShortsResponse shorts = new ShortsResponse();
        shorts.setId(shortItem.getId());
        shorts.setTitle(shortItem.getTitle());
        shorts.setVideo_url(shortItem.getVideoUrl());
        response.add(shorts);
      }
      return response;
    }

  }

  @Data
  public static class ItineraryResponse{
    private Long id;
    private String area;
    private String name;

    public static ItineraryResponse of(Itinerary itinerary) {
      ItineraryResponse itineraryResponse = new ItineraryResponse();
      itineraryResponse.setId(itinerary.getId());
      itineraryResponse.setArea(itinerary.getArea());
      itineraryResponse.setName(itinerary.getName());
      return itineraryResponse;
    }

    public static List<ItineraryResponse> ofList(List<Itinerary> itinerary1 , List<Itinerary> itinerary2) {
      List<ItineraryResponse> response = new ArrayList<>();
      Set<Itinerary> uniqueItinerary = new HashSet<>();
      uniqueItinerary.addAll(itinerary1);
      uniqueItinerary.addAll(itinerary2);

      for (Itinerary itinerary : uniqueItinerary) {
        ItineraryResponse itineraryResponse = new ItineraryResponse();
        itineraryResponse.setId(itinerary.getId());
        itineraryResponse.setArea(itinerary.getArea());
        itineraryResponse.setName(itinerary.getName());
        response.add(itineraryResponse);
      }
      return response;
    }
  }
  @Data
  public static class DiaryResponse{
    private Long id;
    private String title;
    private String content;

    public static DiaryResponse of(Diary diary) {
      DiaryResponse diaryResponse = new DiaryResponse();
      diaryResponse.setId(diary.getId());
      diaryResponse.setTitle(diary.getTitle());
      diaryResponse.setContent(diary.getContent());
      return diaryResponse;
    }

    public static List<DiaryResponse> ofList(List<Diary> diaryList) {
      List<DiaryResponse> responses = new ArrayList<>();

      for(Diary diary : diaryList) {
        DiaryResponse diaryResponse = new DiaryResponse();
        diaryResponse.setId(diary.getId());
        diaryResponse.setTitle(diary.getTitle());
        diaryResponse.setContent(diary.getContent());
        responses.add(diaryResponse);
      }
      return responses;
    }

  }
  @Data
  public static class PlaceResponse{
    private String playNum;
    private String playType;
    private String addr1;
    private String name;
    private String image;

    public static PlaceResponse of(Place place){
      PlaceResponse placeResponse = new PlaceResponse();
      placeResponse.setPlayNum(place.getPlaceNum());
      placeResponse.setPlayType(place.getPlaceType());
      placeResponse.setAddr1(place.getAddr1());
      placeResponse.setName(place.getPlaceName());
      placeResponse.setImage(place.getImage());

      return placeResponse;
    }

    public static List<PlaceResponse> ofList(List<Place> placeList) {
      List<PlaceResponse> responses = new ArrayList<>();

      for(Place place : placeList) {
        PlaceResponse placeResponse = new PlaceResponse();
        placeResponse.setPlayNum(place.getPlaceNum());
        placeResponse.setPlayType(place.getPlaceType());
        placeResponse.setAddr1(place.getAddr1());
        placeResponse.setName(place.getPlaceName());
        placeResponse.setImage(place.getImage());
        responses.add(placeResponse);
      }

      Set<String> seenNames = new HashSet<>();

      return responses.stream()
          .filter(response -> seenNames.add(response.getName()))
          .toList();
    }
  }

}
