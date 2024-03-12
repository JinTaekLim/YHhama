package com.YH.yeohaenghama.domain.account.dto;

import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AccountShowDTO {
    @Data
    public static class Request{
        @Schema(description = "account ID 번호")
        private Long id;
        @Schema(description = "닉네임")
        private String nickname;
        @Schema(description = "프로필 사진 URL")
        private MultipartFile photo;

    }
    @Data
    public static class Response {
        @Schema(description = "account ID 번호")
        private Long id;
        @Schema(description = "닉네임")
        private String nickname;
        @Schema(description = "프로필 사진 URL")
        private String photoUrl;


        public Response(Long id, String nickname, String photoUrl) {
            this.id = id;
            this.nickname = nickname;
            this.photoUrl = photoUrl;
        }
    }


    private AccountShowDTO.Request request;
    private AccountShowDTO.Response response;

}
