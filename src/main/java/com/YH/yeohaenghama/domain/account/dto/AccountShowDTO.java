package com.YH.yeohaenghama.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AccountShowDTO {
    @Schema(description = "account ID 번호")
    private Long id;
    @Schema(description = "닉네임")
    private String nickname;

    public AccountShowDTO(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
