package com.YH.yeohaenghama.domain.chatAI.dto;

import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDiary.DiaryShow;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDiary.Response;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

public class ChatAIItinerary {
  @Schema(name = "ChatAIDiary_Response") @Data
  public static class Response{
    private String keyword;
    private List<ChatAIItinerary.ItineraryShow> itinerary;

    public static ChatAIItinerary.Response toEntity(List<Itinerary> itineraryList,String keyword){
      List<ChatAIItinerary.ItineraryShow> responseItinerary = new ArrayList<>();
      for(Itinerary itinerary : itineraryList){
        responseItinerary.add(ChatAIItinerary.ItineraryShow.toEntity(itinerary));
      }
      ChatAIItinerary.Response response = new ChatAIItinerary.Response();
      response.setItinerary(responseItinerary);
      response.setKeyword(keyword);
      return response;
    }
  }

  @Data
  public static class ItineraryShow{
    private Long itineraryId;
    private String title;

    public static ChatAIItinerary.ItineraryShow toEntity(Itinerary itinerary){
      ChatAIItinerary.ItineraryShow itineraryShow = new ChatAIItinerary.ItineraryShow();
      itineraryShow.setItineraryId(itinerary.getId());
      itineraryShow.setTitle(itinerary.getName());
      return itineraryShow;
    }
  }
}
