package com.YH.yeohaenghama.domain.itinerary.dto;

import com.YH.yeohaenghama.domain.diary.entity.Comment;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlaceShowDTO {
    @Schema(description = "지정 날짜")
    private int day;
    @Schema(description = "장소 ID")
    private Long PlaceId;
    @Schema(description = "시작 시간")
    private String startTime;
    @Schema(description = "종료 시간")
    private String endTime;
    @Schema(description = "장소 타입 번호")
    private String placeType;
    @Schema(description = "장소 번호(코드)")
    private String placeNum;
    @Schema(description = "장소 이름")
    private String placeName;
    @Schema(description = "메모")
    private String memo;

    public static PlaceShowDTO fromEntity(Place place) {
        PlaceShowDTO dto = new PlaceShowDTO();
        dto.setPlaceId(place.getId());
        dto.setDay(place.getDay());
        dto.setStartTime(place.getStartTime());
        dto.setEndTime(place.getEndTime());
        dto.setPlaceType(place.getPlaceType());
        dto.setPlaceNum(place.getPlaceNum());
        dto.setPlaceName(place.getPlaceName());
        dto.setMemo(place.getMemo());
        return dto;
    }

}
