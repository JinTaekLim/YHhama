package com.YH.yeohaenghama.domain.chatAI.dto;

import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Data;

public class ChatAIPopularPlace {
    @Data
    public static class Response {
        private Set<getPlace> placeList;

        public static Response recommend(List<Place> placeList) {

            Set<getPlace> setPlace = new HashSet<>();
            Collections.shuffle(placeList);

            placeList.stream()
                .limit(20)
                .map(getPlace::of)
                .forEach(setPlace::add);

            Response response = new Response();
            response.setPlaceList(setPlace);
            return response;
        }
    }

    @Data
    public static class getPlace{
        private String name;
        private String placeType;
        private String placeNum;

        static getPlace of(Place place){
            getPlace response = new getPlace();
            response.setName(place.getPlaceName());
            response.setPlaceType(place.getPlaceType());
            response.setPlaceNum(place.getPlaceNum());
            return response;
        }
    }
}