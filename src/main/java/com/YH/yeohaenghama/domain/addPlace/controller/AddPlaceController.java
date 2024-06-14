package com.YH.yeohaenghama.domain.addPlace.controller;


import com.YH.yeohaenghama.common.apiResult.ApiResult;
import com.YH.yeohaenghama.domain.addPlace.dto.SaveAddPlaceDTO;
import com.YH.yeohaenghama.domain.addPlace.dto.UpdateAddPlaceDTO;
import com.YH.yeohaenghama.domain.addPlace.service.AddPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/addPlace")
@RequiredArgsConstructor
public class AddPlaceController {
    private final AddPlaceService addPlaceService;

    @Operation(summary = "장소 추가")
    @PostMapping("/save")
    public ApiResult<SaveAddPlaceDTO.Request> save(@RequestBody SaveAddPlaceDTO.Request dto){
        try{
            log.info("dto = "+dto);
            return ApiResult.success(addPlaceService.save(dto));
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }


    @Operation(summary = "장소 수정")
    @PostMapping("/update")
    public ApiResult<UpdateAddPlaceDTO.Request> save(@RequestBody UpdateAddPlaceDTO.Request dto){
        try{
            log.info("dto = "+dto);
            return ApiResult.success(addPlaceService.update(dto));
        } catch (NoSuchElementException e){
            return ApiResult.success(null,e.getMessage());
        }
        catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }


}
