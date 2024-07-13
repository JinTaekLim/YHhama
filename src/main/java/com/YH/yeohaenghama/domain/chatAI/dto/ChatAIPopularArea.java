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
        private String first;
        private String second;
        private String third;

        public static Response ranking(List<Itinerary> itineraryList){
            Response response = new Response();
            Map<String, Integer> areaCountMap = new HashMap<>();

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
                response.setFirst(sortedEntries.get(0).getKey());
                System.out.println("1. " + sortedEntries.get(0).getKey() + " : " + sortedEntries.get(0).getValue());
                if (sortedEntries.size() > 1) {
                    System.out.println("2. " + sortedEntries.get(1).getKey() + " : " + sortedEntries.get(1).getValue());
                    response.setSecond(sortedEntries.get(1).getKey());
                }
                if (sortedEntries.size() > 2) {
                    System.out.println("3. " + sortedEntries.get(2).getKey() + " : " + sortedEntries.get(2).getValue());
                    response.setThird(sortedEntries.get(2).getKey());
                }
            } else {
                System.out.println("일정이 없거나 모든 일정의 지역이 동일한 경우");
            }




            return response;
        }

    }
}
