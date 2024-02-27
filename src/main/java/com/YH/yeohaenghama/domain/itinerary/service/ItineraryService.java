package com.YH.yeohaenghama.domain.itinerary.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryTypeJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.dto.PlaceJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
//import com.YH.yeohaenghama.domain.itinerary.entity.ItineraryType;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
//import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryTypeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor

public class ItineraryService {
    private final ItineraryRepository itineraryRepository;
    private final AccountRepository accountRepository;
//    private final ItineraryTypeRepository itineraryTypeRepository;


//    @Transactional
//    public void saveType(List<ItineraryTypeJoinDTO> req, Long itineraryId) {
//
//        Itinerary itinerary = itineraryRepository.findById(itineraryId)
//                .orElse(null);
//
//        if (itinerary == null) {
//            throw new RuntimeException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + itineraryId);
//        }
//
//        List<ItineraryType> saveTypeToDelete = itineraryTypeRepository.findByItineraryId(itineraryId);
//        if (!saveTypeToDelete.isEmpty()) {
//            itineraryTypeRepository.deleteAll(saveTypeToDelete);
//            log.info("해당 id 값을 가진 일정 타입이 모두 삭제되었습니다.");
//        }
//
//        for (ItineraryTypeJoinDTO dto : req) {
//            ItineraryType itineraryType = new ItineraryType();
//            itineraryType.setItinerary(itinerary);
//            itineraryType.setCompanion(dto.getCompanion());
//            itineraryType.setStyle(dto.getStyle());
//            itineraryTypeRepository.save(itineraryType);
//        }
//
//        log.info("여행타입 서비스 저장");
//    }

    public ItineraryJoinDTO.Response save(ItineraryJoinDTO.Request reqDTO, Long accountId) {


        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("해당 id 값을 가진 유저가 존재하지 않습니다. : " + accountId));


        ItineraryJoinDTO itineraryJoinDTO = new ItineraryJoinDTO(reqDTO);
        Itinerary itinerary = itineraryJoinDTO.toEntity();


        itinerary.setAccount(account);
        itinerary = itineraryRepository.save(itinerary);

        log.info("일정 저장 완료");

        return ItineraryJoinDTO.Response.fromEntity(itinerary);

    }

    public Itinerary show(Long itineraryId){
        Optional<Itinerary> optionalItinerary = itineraryRepository.findById(itineraryId);

        if (optionalItinerary.isPresent()) {
            return optionalItinerary.get();
        } else {
            throw new NoSuchElementException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + itineraryId);
        }
    }










}

