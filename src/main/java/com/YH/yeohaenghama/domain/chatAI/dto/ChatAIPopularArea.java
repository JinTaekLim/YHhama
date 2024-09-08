package com.YH.yeohaenghama.domain.chatAI.dto;

import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatAIPopularArea {
    @Data
    public static class Response{
        private List<String> topAreas;

        public static Response ranking(List<Itinerary> itineraryList){
            Response response = new Response();
            Map<String, Integer> areaCountMap = new HashMap<>();

            for (Itinerary itinerary : itineraryList){
                String area = itinerary.getArea();

                areaCountMap.put(area, areaCountMap.getOrDefault(area, 0) + 1);
            }

            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(areaCountMap.entrySet());
            sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

            List<String> topAreas = new ArrayList<>();
            for (int i = 0; i < Math.min(20, sortedEntries.size()); i++) {
                topAreas.add(sortedEntries.get(i).getKey());
                System.out.println((i + 1) + ". " + sortedEntries.get(i).getKey() + " : " + sortedEntries.get(i).getValue());
            }

            response.setTopAreas(topAreas);

            return response;
        }
    }
}