package com.YH.yeohaenghama.domain.shorts.dto;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.shorts.entity.Shorts;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class UpdateShortsDTO {
    @Data @Schema(name = "UpdateShortsDTO_Request")
    public static class Request{
        private MultipartFile video;
        private String title;
        private String content;
        private Long itineraryId;
    }

    @Data
    public static class Update{
        private String videoUrl;
        private String title;
        private String content;
        private Itinerary itinerary;

        public Update(Shorts shorts, UpdateShortsDTO.Request request){
            this.title = request.getTitle();
            this.content = request.getContent();
            this.itinerary = shorts.getItinerary();
        }
    }


    public static Shorts updateShorts(Shorts shorts, UpdateShortsDTO.Update req) {
        return Shorts.builder()
                .id(shorts.getId())
                .videoUrl(req.getVideoUrl() != null ? req.getVideoUrl() : shorts.getVideoUrl())
                .title(req.getTitle() != null ? req.getTitle() : shorts.getTitle())
                .account(shorts.getAccount())
                .content(req.getContent() != null ? req.getContent() : shorts.getContent())
                .itinerary(req.getItinerary() !=null ? req.getItinerary() : shorts.getItinerary())
                .build();
    }
}
