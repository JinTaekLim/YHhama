package com.YH.yeohaenghama.domain.shorts.dto;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.shorts.entity.Shorts;
import com.YH.yeohaenghama.domain.shorts.entity.ShortsLike;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class LikesDTO {

    @Data @Schema(name = "LikesDTO_Request", description = "state 값 0은 해당 쇼츠에 해당 유저가 좋아요를 클릭했는지 확인, 이 외의값(null,공백 허용) 좋아요 등록/취소")
    public static class Request{
        private Long shortsId;
        private Long accountId;
        private Integer state;
    }

    @Data @Schema(name = "LikesDTO_Request", description = "좋아요 추가/삭제 한 번에 확인 가능. 만약 해당 쇼츠에 좋아요가 존재한다면 Enable값이 true로 반환, 아니라면 false반환")
    public static class Response{
        private boolean Enable;
        private ReadShortsDTO.OneResponse shorts;

        public static Response toEntity(Shorts shorts,boolean enable){
            Response response = new Response();
            response.setEnable(enable);
            response.setShorts(ReadShortsDTO.OneResponse.toEntity(shorts));
            return response;
        }
    }

    public static ShortsLike fromEntity(Shorts shorts, Account account){
        return ShortsLike.builder()
                .account(account)
                .shorts(shorts)
                .build();
    }
}
