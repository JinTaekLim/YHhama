package com.YH.yeohaenghama.domain.addPlace.repository;

import com.YH.yeohaenghama.domain.addPlace.entity.AddPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddPlaceRepository extends JpaRepository<AddPlace, Long> {
    AddPlace findByTitleAndAdd1(String title,String add1);

}
