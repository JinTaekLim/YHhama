package com.YH.yeohaenghama.domain.account.dto;

import lombok.Data;

@Data
public class AccountShowDTO {
    private Long id;
    private String nickname;

    public AccountShowDTO(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
