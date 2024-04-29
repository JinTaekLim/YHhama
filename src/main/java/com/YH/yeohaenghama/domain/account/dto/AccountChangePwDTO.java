package com.YH.yeohaenghama.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class AccountChangePwDTO {
    @Data @Schema(name = "AccountChangePwDTO")
    public static class Request{
        private Long accountId;
        private String OldPw;
        private String NewPw;
    }
}
