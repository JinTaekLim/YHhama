package com.YH.yeohaenghama.domain.itinerary.service;

import com.YH.yeohaenghama.domain.account.dto.AccountShowDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.entity.AccountRole;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.repository.BudgetRepository;
import com.YH.yeohaenghama.domain.chat.service.ChatService;
import com.YH.yeohaenghama.domain.diary.dto.DiaryItineraryShowDTO;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.diary.service.DiaryService;
import com.YH.yeohaenghama.domain.itinerary.dto.*;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
//import com.YH.yeohaenghama.domain.itinerary.entity.ItineraryType;
import com.YH.yeohaenghama.domain.itinerary.entity.ItineraryJoinAccount;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryJoinAccountRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
//import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryTypeRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.PlaceRepository;
import com.YH.yeohaenghama.domain.notification.service.NotificationService;
import com.YH.yeohaenghama.domain.openApi.dto.OpenApiDirectionsDTO;
import com.YH.yeohaenghama.domain.openApi.dto.OpenApiGetXY;
import com.YH.yeohaenghama.domain.openApi.service.OpenApiService;
import com.YH.yeohaenghama.domain.review.entity.Review;
import com.YH.yeohaenghama.domain.review.repository.ReviewRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Slf4j
@RequiredArgsConstructor

public class ItineraryService {
    private final ItineraryRepository itineraryRepository;
    private final AccountRepository accountRepository;
    private final BudgetRepository budgetRepository;
    private final DiaryRepository diaryRepository;
    private final DiaryService diaryService;
    private final OpenApiService openApiService;
    private final ItineraryJoinAccountRepository itineraryJoinAccountRepository;
    private final ReviewRepository reviewRepository;
    private final ChatService chatService;
    private final NotificationService notificationService;

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

    public ItineraryJoinDTO.Response update(Long itineraryId, ItineraryJoinDTO.Request reqDTO, Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("해당 id 값을 가진 유저가 존재하지 않습니다. : " + accountId));

        Itinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElseThrow(() -> new NoSuchElementException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + itineraryId));

        if (!itinerary.getAccount().getId().equals(account.getId())) {
            throw new IllegalArgumentException("해당 계정에 속한 일정이 아닙니다.");
        }

        itinerary.update(reqDTO);

        itinerary = itineraryRepository.save(itinerary);

        log.info("일정 업데이트 완료");

        return ItineraryJoinDTO.Response.fromEntity(itinerary);
    }


    public ItineraryShowDTO getItineraryInfo(Long itineraryId) {
        Optional<Itinerary> optionalItinerary = itineraryRepository.findById(itineraryId);
        if (!optionalItinerary.isPresent())    throw new NoSuchElementException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + itineraryId);
        Itinerary itinerary = optionalItinerary.get();
        ItineraryShowDTO itineraryShowDTO = new ItineraryShowDTO(itinerary);

        List<Budget> budgetList = budgetRepository.findByItineraryId(itineraryId);
        if(!budgetList.isEmpty()) itineraryShowDTO.setBudgetId(budgetList.get(0).getId());
        return itineraryShowDTO;
    }


    public ItineraryShowDTO copy(ItineraryCopyDTO.Request dto){
        Optional<Itinerary> itineraryOpt = itineraryRepository.findById(dto.getItineraryId());
        Optional<Account> accountOpt = accountRepository.findById(dto.getAccountId());
        if(itineraryOpt.isEmpty() || accountOpt.isEmpty()) throw new NoSuchElementException("입력 받은 ID를 가진 데이터가 존재하지 않습니다. ");
        Itinerary itinerary = itineraryOpt.get();
        Account account = accountOpt.get();

        log.info("account = " + account.getId());

        ItineraryCopyDTO.itinerary copyItinerary = ItineraryCopyDTO.itinerary.fromEntity(itinerary,account);


        Itinerary newItinerary = ItineraryCopyDTO.toEntity(copyItinerary);

        itineraryRepository.save(newItinerary);


        List<Place> placeList = new ArrayList<>();

        for(Place place : itinerary.getPlaces()){
            Place newPlace = new Place();
            newPlace.setItinerary(newItinerary);
            newPlace.setDay(place.getDay());
            newPlace.setStartTime(place.getStartTime());
            newPlace.setEndTime(place.getEndTime());
            newPlace.setPlaceType(place.getPlaceType());
            newPlace.setPlaceNum(place.getPlaceNum());
            newPlace.setPlaceName(place.getPlaceName());
            newPlace.setAddr1(place.getAddr1());
            newPlace.setMapx(place.getMapx());
            newPlace.setMapy(place.getMapy());
            newPlace.setMemo(place.getMemo());

            placeRepository.save(newPlace);
            placeList.add(newPlace);
        }

        newItinerary.setPlaceItinerary(placeList);
        ItineraryShowDTO response = new ItineraryShowDTO(newItinerary);
        return response;

    }



    public void deleteItinerary(Long itineraryId,Long accountId) throws IOException {
        Optional<Itinerary> optionalItinerary = itineraryRepository.findById(itineraryId);

        if(optionalItinerary.isEmpty()){
            throw new NoSuchElementException("존재하지 않는 일정입니다.");
        }

        if(accountId != optionalItinerary.get().getAccount().getId() && accountRepository.findById(accountId).get().getRole() == AccountRole.ACCOUNT){
            throw new NoSuchElementException("해당 일정을 작성한 유저가 아닙니다.");
        }

        if (optionalItinerary.isPresent()) {
            log.info(String.valueOf("deleteItinerary : " + itineraryId));


            Optional<Diary> diaryOptional = diaryRepository.findByItinerary(itineraryId);
            if (diaryOptional.isPresent()) {
                diaryService.delete(itineraryId);
                log.info("일기 삭제 완");
            }

            log.info("일정 삭제 완");
            itineraryRepository.deleteById(itineraryId);
        } else {
            throw new NoSuchElementException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + itineraryId);
        }
    }

    public List<ItineraryShowAccountDTO.Response> showAccount(Long accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("해당 id 값을 가진 유저가 존재하지 않습니다. : " + accountId));

        List<Itinerary> itineraryList = itineraryRepository.findByAccountId(accountId);

        List<ItineraryShowAccountDTO.Response> responses = new ArrayList<>();
        for(Itinerary itinerary : itineraryList) {
            boolean diary = false;
            Optional<Diary> diaryOpt = diaryRepository.findByItinerary(itinerary.getId());
            if(!diaryOpt.isEmpty()){
                log.info(diaryOpt.get().getTitle());
                diary = true;
            };
            responses.add(ItineraryShowAccountDTO.Response.fromEntity(itinerary,diary));
        }

        return responses;
    }


    public List<ItineraryShowAccountDTO.Response> showShareAccount(Long accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("해당 id 값을 가진 유저가 존재하지 않습니다. : " + accountId));

        List<ItineraryJoinAccount> itineraryAccountList = itineraryJoinAccountRepository.findByAccountId(accountId);

        if (itineraryAccountList.isEmpty()) throw new NoSuchElementException("해당 ID값을 가진 유저가 공유받은 일정이 존재하지 않습니다.");

        List<ItineraryShowAccountDTO.Response> responses = new ArrayList<>();

        for (ItineraryJoinAccount joinAccount : itineraryAccountList){
            Optional<Itinerary> itineraryOpt = itineraryRepository.findById(joinAccount.getItinerary().getId());
            boolean diary = false;
            Optional<Diary> diaryOpt = diaryRepository.findByItinerary(joinAccount.getItinerary().getId());
            if(!diaryOpt.isEmpty()){ diary = true; };
            responses.add(ItineraryShowAccountDTO.Response.fromEntity(itineraryOpt.get(),diary));
        }


        return responses;
    }

        public final PlaceRepository placeRepository;

    public int getPlaceLength(Long ItineraryId){
        log.info(String.valueOf(placeRepository.findByItineraryId(ItineraryId).size()));
        return placeRepository.findByItineraryId(ItineraryId).size();
    }



    public ItineraryShowMain.Response showMain(ItineraryShowMain.Request dto) throws IOException {

        Optional<Itinerary> itineraryOpt = itineraryRepository.findByIdAndAccountId(dto.getItineraryId(), dto.getAccountId());
        if(itineraryOpt.isEmpty()){ throw new NoSuchElementException("유저가 해당 ID를 가진 일정을 가지고 있지 않습니다. "); }
        Itinerary itinerary = itineraryOpt.get();

        ItineraryShowMain.Response response = new ItineraryShowMain.Response(openApiService);
        response.insertItinerary(itinerary);


        return response;
    }



    @Transactional
    public String itineraryJoinAccount(ItineraryJoinAccountDTO.Request dto) throws Exception {
        ItineraryJoinAccountDTO itineraryJoinAccountDTO = new ItineraryJoinAccountDTO();

        Optional<Account> accountOpt = accountRepository.findById(dto.getAccountId());
        Optional<Itinerary> itineraryOpt = itineraryRepository.findById(dto.getItineraryId());
        if(!itineraryJoinAccountRepository.findByItineraryIdAndAccountId(itineraryOpt.get().getId(),dto.getAccountId()).isEmpty()) throw new NoSuchElementException(" 해당 유저는 이미 일정을 공유 받았습니다.");

        if(accountOpt == null || itineraryOpt == null) throw new NoSuchElementException(" 입력된 ID를 가진 정보가 존재하지 않습니다. ");
        if(itineraryOpt.get().getAccount() == accountOpt.get()) throw new NoSuchElementException("해당 유저는 일정 주인 입니다.");

        String roomId = String.valueOf(itineraryOpt.get().getId());

        if(itineraryJoinAccountRepository.findByItineraryIdAndAccountId(itineraryOpt.get().getId(),itineraryOpt.get().getAccount().getId()).isEmpty()) {
            itineraryJoinAccountRepository.save(itineraryJoinAccountDTO.add(itineraryOpt.get().getAccount(), itineraryOpt.get()));
            chatService.createChatRomm(roomId);
        }

        itineraryJoinAccountRepository.save(itineraryJoinAccountDTO.add(accountOpt.get(),itineraryOpt.get()));
        chatService.addUserToChatRoom(roomId, String.valueOf(accountOpt.get().getId()));

        notificationService.sendToClient(dto.getAccountId(), "01", "itineraryId : " + itineraryOpt.get().getId());
        return "추가 성공";
    }

    public String itineraryDeleteAccount(ItineraryJoinAccountDTO.Request dto){
        Optional<ItineraryJoinAccount> itineraryJoinAccountOpt = itineraryJoinAccountRepository.findByItineraryIdAndAccountId(dto.getItineraryId(),dto.getAccountId());
        if(itineraryJoinAccountOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 유저가 일정에 존재하지 않습니다. ");

        itineraryJoinAccountRepository.delete(itineraryJoinAccountOpt.get());

        return "삭제 성공";
    }

    public List<ItineraryJoinAccountShowDTO.Response> itineraryShowAccount(ItineraryJoinAccountShowDTO.Request dto){
        List<ItineraryJoinAccount> itineraryJoinAccountOpt = itineraryJoinAccountRepository.findByItineraryId(dto.getItineraryId());

        if(itineraryJoinAccountOpt.isEmpty()) { throw new NoSuchElementException("해당 일정에 추가된 유저 정보가 존재하지 않습니다. "); }

        List<ItineraryJoinAccountShowDTO.Response> responses = new ArrayList<>();

        for(ItineraryJoinAccount itineraryJoinAccount : itineraryJoinAccountOpt){
            responses.add(ItineraryJoinAccountShowDTO.Response.fromEntity(itineraryJoinAccount));
        }
        return responses;
    }


    public DiaryItineraryShowDTO.Response diaryForm(Long itineraryId){
        Optional<Itinerary> itineraryOpt = itineraryRepository.findById(itineraryId);
        Itinerary itinerary = itineraryOpt.get();
        Account account = itinerary.getAccount();
        List<Review> review = reviewRepository.findByAccountId(account.getId());
        return DiaryItineraryShowDTO.Response.fromEntity(itinerary,review);
    }
}




