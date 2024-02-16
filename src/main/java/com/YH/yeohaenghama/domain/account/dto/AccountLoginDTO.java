package com.YH.yeohaenghama.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountLoginDTO {

    @Schema(description = "이메일")
    private String email;
    @Schema(description = "비밀번호")
    private String pw;
}
