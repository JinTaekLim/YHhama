package com.YH.yeohaenghama.domain.itinerary.controller;

import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.diary.dto.DiaryItineraryShowDTO;
import com.YH.yeohaenghama.domain.itinerary.dto.*;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
//import com.YH.yeohaenghama.domain.itinerary.entity.ItineraryType;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.itinerary.service.ItineraryService;
import com.YH.yeohaenghama.domain.itinerary.service.PlaceService;
import com.YH.yeohaenghama.domain.report.dto.ReportCountDTO;
import com.YH.yeohaenghama.domain.report.dto.ReportDiaryDTO;
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
    public ApiResult<List<PlaceShowDTO>> createPlaces(@ModelAttribute PlaceJoinDTO placeDTO, @PathVariable Long itineraryId) {
        try {
            if (placeDTO.getDay() == null ||
                    placeDTO.getStartTime() == null ||
                    placeDTO.getEndTime() == null ||
                    placeDTO.getPlaceType() == null ||
                    placeDTO.getPlaceNum() == null ||
                    placeDTO.getPlaceName() == null ||
                    placeDTO.getAddr1() == null) {
                return ApiResult.success(placeService.show(itineraryId),"장소 조회 실패");
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


    @Operation(summary = "제작된 일정 장소 확인")
    @GetMapping("/showPlace/{itineraryId}")
    public ApiResult<List<PlaceShowDTO>> showPlace(@PathVariable Long itineraryId) {
        try {
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
    public ApiResult<ItineraryShowDTO> getItineraryInfo(@PathVariable Long itineraryId) {
        try {
            return ApiResult.success(itineraryService.getItineraryInfo(itineraryId));
        } catch (NoSuchElementException e) {
            return ApiResult.success(null,e.getMessage());
        } catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "유저의 모든 일정 조회")
    @GetMapping("/itineraryShow/{accountId}")
    public ApiResult<List<ItineraryShowAccountDTO.Response>> showAccount(@PathVariable Long accountId) {
        try {
            return ApiResult.success(itineraryService.showAccount(accountId));
        }
        catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "유저의 공유 받은 모든 일정 조회")
    @GetMapping("/itineraryShareShow/{accountId}")
    public ApiResult<List<ItineraryShowAccountDTO.Response>> showShareAccount(@PathVariable Long accountId) {
        try {
            return ApiResult.success(itineraryService.showShareAccount(accountId));
        }
        catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "일정 가져오기")
    @PostMapping("/copy")
    public ApiResult<ItineraryShowDTO> copy(@RequestBody ItineraryCopyDTO.Request dto){
        try{
            return ApiResult.success(itineraryService.copy(dto));
        }catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }catch (Exception e){
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

    @Operation(summary = "일기 작성 양식")
    @PostMapping("/diaryForm")
    public ApiResult<DiaryItineraryShowDTO.Response> diaryForm(@RequestParam Long itineraryId) {
        try {
            return ApiResult.success(itineraryService.diaryForm(itineraryId));
        } catch (NoSuchElementException e){
            return ApiResult.notFound(e.getMessage());
        }
        catch (Exception e) {
            return ApiResult.fail(e.getMessage());
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




    @Operation(summary = "일정 유저 추가")
    @PostMapping("/addAccount")
    public ApiResult<String> addAccount(ItineraryJoinAccountDTO.Request dto){
        try{
            log.info("DTO = " + dto);
            return ApiResult.success(itineraryService.itineraryJoinAccount(dto));
        } catch (NoSuchElementException e){
            return ApiResult.notFound(e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "일정에 참가한 유저 전체 조회")
    @PostMapping("/showItineraryAccount")
    public ApiResult<List<ItineraryJoinAccountShowDTO.Response>> showItineraryAccount(ItineraryJoinAccountShowDTO.Request dto){
        try{
            return ApiResult.success(itineraryService.itineraryShowAccount(dto));
        } catch (NoSuchElementException e){
            return ApiResult.notFound(e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "일정 유저 삭제")
    @PostMapping("/deleteAccount")
    public ApiResult<String> deleteAccount(ItineraryJoinAccountDTO.Request dto){
        try{
            return ApiResult.success(itineraryService.itineraryDeleteAccount(dto));
        } catch (NoSuchElementException e){
            return ApiResult.notFound(e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

    @Operation(summary = "일정에 포함된 장소 확인")
    @PostMapping("/checkItineraryInPlace")
    public ApiResult<Boolean> checkItineraryInPlace(PlaceCheckInItineraryDTO dto){
        try{
            return ApiResult.success(placeService.checkItineraryInPlace(dto));
        } catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }



}