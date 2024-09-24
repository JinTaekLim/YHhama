package com.YH.yeohaenghama.domain.chatAI.dto;

import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatAIPopularArea {
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
    public static class Response{
        private List<AreaResponse> topAreas;

        public static Response ranking(List<Itinerary> itineraryList){
            Response response = new Response();
            Map<String, Integer> areaCountMap = new HashMap<>();

            for (Itinerary itinerary : itineraryList){
                String area = itinerary.getArea();

                areaCountMap.put(area, areaCountMap.getOrDefault(area, 0) + 1);
            }

            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(areaCountMap.entrySet());
            sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

            List<AreaResponse> topAreas = new ArrayList<>();
            for (int i = 0; i < Math.min(20, sortedEntries.size()); i++) {
                String area = sortedEntries.get(i).getKey();
                topAreas.add(new AreaResponse(area));
            }

            response.setTopAreas(topAreas);

            return response;
        }
    }
}