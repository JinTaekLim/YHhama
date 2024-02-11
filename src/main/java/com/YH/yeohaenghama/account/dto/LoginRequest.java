package com.YH.yeohaenghama.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

    private String email;
    private String pw;
    private String photoUrl;
    private String nickname;

}
