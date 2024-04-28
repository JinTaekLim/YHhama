package com.YH.yeohaenghama.domain.account.dto;

import com.YH.yeohaenghama.domain.account.entity.AccountRole;
import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AccountShowDTO {
    @Data
    @Schema(name = "AccountShowDTO_Request")
    public static class Request{
        @Schema(description = "account ID 번호")
        private Long id;
        @Schema(description = "닉네임")
        private String nickname;
        @Schema(description = "프로필 사진 URL")
        private MultipartFile photo;

    }
    @Data
    @Schema(name = "AccountShowDTO_Response")
    public static class Response {
        @Schema(description = "account ID 번호")
        private Long id;
        @Schema(description = "닉네임")
        private String nickname;
        @Schema(description = "프로필 사진 URL")
        private String photoUrl;
        @Schema(description = "계정 권한")
        private AccountRole accountRole;


        public Response(Long id, String nickname, String photoUrl,AccountRole accountRole) {
            this.id = id;
            this.nickname = nickname;
            this.photoUrl = photoUrl;
            this.accountRole = accountRole;
        }
    }


    private AccountShowDTO.Request request;
    private AccountShowDTO.Response response;

}
