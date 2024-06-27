package com.YH.yeohaenghama.domain.shorts.dto;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.shorts.entity.Shorts;
import com.YH.yeohaenghama.domain.shorts.entity.ShortsInItinerary;
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

        public static Response toEntity(Shorts shorts){
            Response response = new Response();
            response.setTitle(shorts.getTitle());
            response.setContent(shorts.getContent());
            response.setVideoUrl(shorts.getVideoUrl());
            response.setAccount(UploadShortsDTO.getAccount(shorts.getAccount()));
            return response;
        }
    }

    public static Shorts toShorts(Shorts shorts, Account account, String videoUrl, Request req, List<ShortsInItinerary> shortsInItinerary){
        Shorts response = Shorts.builder()
                .id(shorts.getId())
                .videoUrl(videoUrl)
                .title(req.getTitle())
                .account(account)
                .content(req.getContent())
                .shortsInItinerary(shortsInItinerary)
                .build();

        return response;
    }

    public static AccountShowDTO.Response getAccount(Account account){
        return new AccountShowDTO.Response(account.getId(),account.getNickname(), account.getNickname(), account.getRole());
    }
}
