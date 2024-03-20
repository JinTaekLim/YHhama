package com.YH.yeohaenghama.domain.account.service;

import com.YH.yeohaenghama.domain.account.dto.AccountSavePlaceDTO;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.entity.AccountSavePlace;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.account.repository.AccountSavePlaceRepository;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountSavePlaceService {
    private final AccountSavePlaceRepository accountSavePlaceRepository;
    private final AccountRepository accountRepository;

    public boolean SavePlace(AccountSavePlaceDTO req, Long accountId){

        Account account = findAccountById(accountId);

        log.info("[SavePlace] 유저 조회 성공");

        List<AccountSavePlace> accountData = accountSavePlaceRepository.findByContentTypeIdAndPlaceNumAndAccount_Id(req.getContentTypeId(), req.getPlaceNum(), accountId);

        if (accountData.isEmpty()) {
            AccountSavePlace accountSavePlace = new AccountSavePlace(req.getPlaceNum(), req.getContentTypeId(), account);
            accountSavePlaceRepository.save(accountSavePlace);
            log.info("장소 저장 완료");

        } else {
            for (AccountSavePlace accountSavePlace : accountData) {
                log.info("이미 저장되어 있는 장소입니다. : " + accountSavePlace);
                return false;
            }
        }
        return true;
    }


    public List<AccountSavePlaceDTO> ViewSavePlace(Long accountId){
        findAccountById(accountId);

        log.info("[ViewSavePlace] 유저 조회 성공");

        List<AccountSavePlace> accountData = accountSavePlaceRepository.findByAccountId(accountId);


        List<AccountSavePlaceDTO> filteredAccountData = accountData.stream()
                .filter(place -> place.getAccount().getId().equals(accountId))
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return filteredAccountData;
    }

    public void DeletePlace(Long accountId, AccountSavePlaceDTO dto) {
        findAccountById(accountId);
        log.info("[DeletePlace] 유저 조회 성공");


        log.info("user: " +String.valueOf(accountId));
        log.info("dto" +String.valueOf(dto));

        List<AccountSavePlace> placeToDelete = accountSavePlaceRepository.findByContentTypeIdAndPlaceNumAndAccount_Id(dto.getContentTypeId(),dto.getPlaceNum(),accountId);

        log.info(placeToDelete.toString());
        if (!placeToDelete.isEmpty()) {
            accountSavePlaceRepository.deleteById(placeToDelete.get(0).getId());
            log.info("[DeletePlace] 해당 장소 저장 정보 삭제 완료");
        } else {
            log.info("[DeletePlace] 해당하는 장소 저장 정보가 없거나 유저에게 속하지 않습니다.");
            throw new NoSuchElementException("[DeletePlace] 해당하는 장소 저장 정보가 없거나 유저에게 속하지 않습니다.");
        }
    }

    public boolean checkSavePlace(Long accountId,AccountSavePlaceDTO dto){
        findAccountById(accountId);
        log.info("[DeletePlace] 유저 조회 성공");

        List<AccountSavePlace> placeToDelete = accountSavePlaceRepository.findByContentTypeIdAndPlaceNumAndAccount_Id(dto.getContentTypeId(),dto.getPlaceNum(),accountId);

        if (placeToDelete.isEmpty()){
            return false;
        }
        return true;
    }

    public Account findAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    log.info("해당 id 값을 가진 유저가 존재하지 않습니다. : ");
                    return new IllegalArgumentException("해당 id 값을 가진 유저가 존재하지 않습니다. : ");
                });
    }

    private AccountSavePlaceDTO mapToDto(AccountSavePlace place) {
        AccountSavePlaceDTO dto = new AccountSavePlaceDTO();
        dto.setPlaceNum(place.getPlaceNum());
        dto.setContentTypeId(place.getContentTypeId());
        return dto;
    }

}
