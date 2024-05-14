package com.YH.yeohaenghama.domain.account.dto;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.entity.AccountRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
public class AccountLoginDTO {

    @Schema(description = "이메일")
    private String email;
    @Schema(description = "비밀번호")
    private String pw;

    @Data @Schema(name = "AccountLoginDTO_Response")
    public static class Response{
        private Long id;
        private String email;
        private String photoUrl;
        private String nickname;
        private AccountRole role;
        private Date stop;

        public static AccountLoginDTO.Response fromEntity(Account account){
            AccountLoginDTO.Response response = new Response();
            response.setId(account.getId());
            response.setEmail(account.getEmail());
            response.setPhotoUrl(account.getPhotoUrl());
            response.setNickname(account.getNickname());
            response.setRole(account.getRole());
            response.setStop(account.getStop());
            return response;
        }
    }
}
