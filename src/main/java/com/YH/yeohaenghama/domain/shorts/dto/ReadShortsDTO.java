package com.YH.yeohaenghama.domain.shorts.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryShowAccountDTO;
import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryShowDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.shorts.entity.Shorts;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReadShortsDTO {
    @Data @Schema(name = "ReadShortsDTO_OneRequest")
    public static class OneRequest{
        private Long shortsId;
    }

    @Data @Schema(name = "ReadShortsDTO_AllRequest")
    public static class AllRequest{
        private Integer numOfRows;
        private Integer page;
    }


    @Data @Schema(name = "ReadShortsDTO_AllResponse")
    public static class AllResponse{
        private List<OneResponse> shortsList;

        public AllResponse(List<Shorts> shorts){
            List<OneResponse> response = new ArrayList<>();
            for(Shorts i : shorts){response.add(OneResponse.toEntity(i));}
            this.shortsList = response;
        }
    }

    @Data @Schema(name = "ReadShortsDTO_OneResponse")
    public static class OneResponse{
        private Long shortsId;
        private String videoUrl;
        private String title;
        private String content;
        private LocalDateTime date;
        private Integer likes;
        private Integer commentNum;
        private AccountShowDTO.Response account;
        private ItineraryShowDTO.Response itinerary;

        public static OneResponse toEntity(Shorts shorts){
            OneResponse response = new OneResponse();
            response.setShortsId(shorts.getId());
            response.setVideoUrl(shorts.getVideoUrl());
            response.setTitle(shorts.getTitle());
            response.setContent(shorts.getContent());
            response.setDate(shorts.getDate());
            response.setLikes(shorts.getShortsLikes().size());
            response.setAccount(new AccountShowDTO.Response().toEntity(shorts.getAccount()));
            response.setItinerary(shorts.getItinerary() != null ? ItineraryShowDTO.Response.toEntity(shorts.getItinerary()) : null);
            response.setCommentNum(shorts.getShortsComments().size());
            return response;
        }
    }

}
