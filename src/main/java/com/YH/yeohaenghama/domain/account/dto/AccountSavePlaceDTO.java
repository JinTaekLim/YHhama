package com.YH.yeohaenghama.domain.account.dto;

import com.YH.yeohaenghama.domain.account.entity.AccountSavePlace;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AccountSavePlaceDTO {
    @Schema(description = "일정ID")
    private Long itineraryId = null;
    @Schema(description = "장소 번호(코드)")
    private String placeNum;
    @Schema(description = "관광 타입 번호(코드)")
    private String contentTypeId;


}
