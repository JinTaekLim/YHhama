package com.YH.yeohaenghama.domain.addPlace.service;

import com.YH.yeohaenghama.domain.addPlace.dto.SaveAddPlaceDTO;
import com.YH.yeohaenghama.domain.addPlace.entity.AddPlace;
import com.YH.yeohaenghama.domain.addPlace.repository.AddPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddPlaceService {
    private final AddPlaceRepository addPlaceRepository;

    public SaveAddPlaceDTO.Request save(SaveAddPlaceDTO.Request dto){
        AddPlace addPlace = new SaveAddPlaceDTO(dto).toEntity();
        addPlaceRepository.save(addPlace);
        return dto;
    }


}
