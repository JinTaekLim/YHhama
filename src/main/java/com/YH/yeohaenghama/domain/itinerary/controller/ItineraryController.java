package com.YH.yeohaenghama.domain.itinerary.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.itinerary.dto.*;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
//import com.YH.yeohaenghama.domain.itinerary.entity.ItineraryType;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.itinerary.service.ItineraryService;
import com.YH.yeohaenghama.domain.itinerary.service.PlaceService;
import com.google.protobuf.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/itinerary")
public class ItineraryController {
    private final ItineraryService itineraryService;
    private final PlaceService placeService;

    @Operation(summary = "일정 생성")
    @PostMapping("/join/{accountId}") //
    public ApiResult<ItineraryJoinDTO.Response> saveItinerary(@RequestBody ItineraryJoinDTO.Request reqDTO, @PathVariable Long accountId) {
        try {
            ItineraryJoinDTO.Response response = itineraryService.save(reqDTO, accountId);
            return ApiResult.success(response);
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "일정의 장소 추가")
    @PostMapping("/joinPlaces/{itineraryId}")
    public ApiResult<List<PlaceJoinDTO>> createPlaces(@RequestBody List<PlaceJoinDTO> placeDTOs, @PathVariable Long itineraryId) {
        try {
            placeService.createPlaces(placeDTOs, itineraryId);
            return ApiResult.success(placeDTOs,"장소 추가 성공");
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e) {
            return ApiResult.fail("[일정]장소 추가 실패 : " + e.getMessage());
        }
    }

    @Operation(summary = "일정의 장소 개별 추가")
    @PostMapping("/joinPlace/{itineraryId}")
    public ApiResult<List<PlaceShowDTO>> createPlaces(@RequestBody PlaceJoinDTO placeDTO, @PathVariable Long itineraryId) {
        try {
            if (placeDTO.getDay() == null ||
                    placeDTO.getStartTime() == null ||
                    placeDTO.getEndTime() == null ||
                    placeDTO.getPlaceType() == null ||
                    placeDTO.getPlaceNum() == null ||
                    placeDTO.getPlaceName() == null ||
                    placeDTO.getAdd1() == null) {
                return ApiResult.fail("[일정]장소 추가 실패 : 필수 정보가 누락되었습니다.");
            }

            return ApiResult.success(placeService.createPlace(placeDTO, itineraryId),"장소 추가 성공");
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e) {
            return ApiResult.fail("[일정]장소 추가 실패 : " + e.getMessage());
        }
    }

    @Operation(summary = "일정의 장소 개별 삭제")
    @PostMapping("/deletePlace")
    public ApiResult<List<PlaceShowDTO>> createPlaces(@RequestBody ItineraryDeletePlaceDTO.Request dto) {
        try {
            return ApiResult.success(placeService.deletePlace(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e) {
            return ApiResult.fail("[일정]장소 삭제 실패 : " + e.getMessage());
        }
    }
//
//    @Operation(summary = "제작된 일정 확인")
//    @GetMapping("/showItinerary/{itineraryId}")
//    public ResponseEntity<Itinerary> showItinerary(@PathVariable Long itineraryId){
//        try {
//            return ResponseEntity.ok(itineraryService.show(itineraryId));
//        }
//        catch (Exception e){
//            e.getMessage();
//            return null;
//        }
//    }


    @Operation(summary = "제작된 일정 장소 확인")
    @GetMapping("/showPlace/{itineraryId}")
    public ApiResult<List<PlaceShowDTO>> showPlace(@PathVariable Long itineraryId) {
        try {
//            List<Place> places = placeService.show(itineraryId);
//            List<PlaceShowDTO> placeDTOs = places.stream()
//                    .map(place -> {
//                        PlaceShowDTO dto = new PlaceShowDTO();
//                        dto.setStartTime(place.getStartTime());
//                        dto.setEndTime(place.getEndTime());
//                        dto.setPlaceType(place.getPlaceType());
//                        dto.setPlaceNum(place.getPlaceNum());
//                        dto.setPlaceName(place.getPlaceName());
//                        dto.setAdd1(place.getAdd1());
//                        dto.setPlaceId(place.getId());
//                        dto.setMemo(place.getMemo());
//                        return dto;
//                    })
//                    .collect(Collectors.toList());
            return ApiResult.success(placeService.show(itineraryId));
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "제작된 일정 확인")
    @GetMapping("/{itineraryId}")
    public ApiResult getItineraryInfo(@PathVariable Long itineraryId) {
        try {
            ItineraryShowDTO itineraryInfo = itineraryService.getItineraryInfo(itineraryId);
            return ApiResult.success(itineraryInfo);
        } catch (NoSuchElementException e) {
            return ApiResult.success(e.getMessage());
        } catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "유저의 모든 일정 조회")
    @GetMapping("/itineraryShow/{accountId}")
    public ApiResult<List<Map<String, Object>>> getItinerariesByAccountId(@PathVariable Long accountId) {
        try {
            List<ItineraryRepository.ItineraryProjection> itineraries = itineraryService.getItinerariesByAccountId(accountId);

            List<Map<String, Object>> itineraryData = itineraries.stream()
                    .map(itineraryProjection -> {
                        Map<String, Object> data = new HashMap<>();

                        data.put("itineraryId", itineraryProjection.getId());
                        data.put("name", itineraryProjection.getName());
                        data.put("startDate", itineraryProjection.getStartDate());
                        data.put("endDate", itineraryProjection.getEndDate());
                        data.put("placeLength", itineraryService.getPlaceLength(itineraryProjection.getId()));
                        return data;
                    })
                    .collect(Collectors.toList());

            return ApiResult.success(itineraryData);
        }
        catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "일정 삭제")
    @PostMapping("/delete")
    public ApiResult itineraryDelete(@RequestParam Long itineraryId,@RequestParam Long accountId) {
        try {
            itineraryService.deleteItinerary(itineraryId,accountId);
            return ApiResult.success("일정 삭제 성공");
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e) {
            return ApiResult.fail("[일정]장소 삭제 실패 : " + e.getMessage());
        }
    }


    @Operation(summary = "일정 수정")
    @PostMapping("/update")
    public ApiResult itineraryUpdate(@RequestBody ItineraryJoinDTO.Request req, @RequestParam Long accountId, @RequestParam Long itineraryId) {
        try {
            ItineraryJoinDTO.Response response = itineraryService.update(itineraryId, req, accountId);
            return ApiResult.success(response);
        } catch (NoSuchElementException e) {
            return ApiResult.success("[일정]수정 실패: " + e.getMessage());
        } catch (Exception e) {
            return ApiResult.fail("");
        }
    }

    @Operation(summary = "일정 확인 ( 메인 화면 )")
    @PostMapping("/showMain")
    public ApiResult itineraryShowMain(@RequestBody ItineraryShowMain.Request dto) {
        try {
            return ApiResult.success(itineraryService.showMain(dto));
        } catch (NoSuchElementException e) {
            return ApiResult.success("[일정]수정 실패: " + e.getMessage());
        } catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
    }



}