package com.YH.yeohaenghama.domain.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountLoginDTO {

    private String email;
    private String pw;
}
