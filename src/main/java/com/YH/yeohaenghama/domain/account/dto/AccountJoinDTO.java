package com.YH.yeohaenghama.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountJoinDTO {


    @Schema(description = "이메일")
    private String email;
    @Schema(description = "비밀번호")
    private String pw;
    @Schema(description = "이미지 URL")
    private String photoUrl;
    @Schema(description = "닉네임")
    private String nickname;

}
