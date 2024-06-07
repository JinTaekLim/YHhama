package com.YH.yeohaenghama.domain.addPlace.service;

import com.YH.yeohaenghama.domain.addPlace.dto.SaveAddPlaceDTO;
import com.YH.yeohaenghama.domain.addPlace.entity.AddPlace;
import com.YH.yeohaenghama.domain.addPlace.repository.AddPlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddPlaceService {
    private final AddPlaceRepository addPlaceRepository;

    public SaveAddPlaceDTO.Request save(SaveAddPlaceDTO.Request dto){
        AddPlace addPlace = new SaveAddPlaceDTO(dto).toEntity();
        addPlaceRepository.save(addPlace);
        return dto;
    }

    public AddPlace getAddPlace(String title,String add1, String add2, String tel, String mapX, String mapY){
        log.info("등장");
        AddPlace addPlace = addPlaceRepository.findByTitleAndAdd1(title,add1);
        if (addPlace == null) addPlace = addPlaceRepository.save(new SaveAddPlaceDTO(SaveAddPlaceDTO.Request.getRequest(title,add1,add2,tel,mapX,mapY)).toEntity());

        return addPlace;
    }



}
