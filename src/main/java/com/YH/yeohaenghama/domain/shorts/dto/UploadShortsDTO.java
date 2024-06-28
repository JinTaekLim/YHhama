package com.YH.yeohaenghama.domain.shorts.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryShowDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.shorts.entity.Shorts;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UploadShortsDTO {
    @Data @Schema(name = "createShortsDTO_Request")
    public static class Request{
        private Long accountId;
        private MultipartFile video;
        private String title;
        private String content;
        private Long itineraryId;
    }

    @Data @Schema(name = "createShortsDTO_Response")
    public static class Response{
        private AccountShowDTO.Response account;
        private String videoUrl;
        private String title;
        private String content;
        private ItineraryShowDTO.Response itinerary;

        public static Response toEntity(Shorts shorts){
            Response response = new Response();
            response.setTitle(shorts.getTitle());
            response.setContent(shorts.getContent());
            response.setVideoUrl(shorts.getVideoUrl());
            response.setAccount(UploadShortsDTO.getAccount(shorts.getAccount()));


//            if(shorts.getShortsInItinerary() != null) response.setItinerary(ItineraryShowDTO.Response.toEntity(shorts.getShortsInItinerary().get(0).getItinerary()));

            return response;
        }
    }

    public static Shorts toShorts(Shorts shorts, Account account, String videoUrl, Request req,Itinerary itinerary){
        Shorts response = Shorts.builder()
                .id(shorts.getId())
                .videoUrl(videoUrl)
                .title(req.getTitle())
                .account(account)
                .content(req.getContent())
                .itinerary(itinerary)
                .build();

//        if(itinerary != null) Shorts.builder().itinerary(itinerary);

        return response;
    }

    public static AccountShowDTO.Response getAccount(Account account){
        return new AccountShowDTO.Response(account.getId(),account.getNickname(), account.getNickname(), account.getRole());
    }
}
