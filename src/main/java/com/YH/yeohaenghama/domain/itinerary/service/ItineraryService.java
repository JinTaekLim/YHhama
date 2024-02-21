package com.YH.yeohaenghama.domain.itinerary.service;

import com.YH.yeohaenghama.domain.account.dto.AccountLoginDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.itinerary.dto.ItineraryJoinDTO;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor

public class ItineraryService {
    private final ItineraryRepository itineraryRepository;
    private final AccountRepository accountRepository;

    public ItineraryJoinDTO.Response save(ItineraryJoinDTO.Request reqDTO, Long accountId) {


        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("해당 id 값을 가진 유저가 존재하지 않습니다. : " + accountId));


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
            // 조회된 일정이 없을 경우에는 RuntimeException을 던집니다.
            throw new RuntimeException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + itineraryId);
        }
    }

}

