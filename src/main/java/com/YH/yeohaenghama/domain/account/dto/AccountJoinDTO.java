package com.YH.yeohaenghama.domain.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountJoinDTO {


    private String email;
    private String pw;
    private String photoUrl;
    private String nickname;

}
