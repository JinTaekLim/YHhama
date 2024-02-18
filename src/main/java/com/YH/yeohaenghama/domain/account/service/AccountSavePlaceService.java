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

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountSavePlaceService {
    private final AccountSavePlaceRepository accountSavePlaceRepository;
    private final AccountRepository accountRepository;

    public void SavePlace(AccountSavePlaceDTO req, Long accountId){
        Account account = accountRepository.findById(accountId)
                .orElse(null);

        if (account == null) {
            log.info("해당 id 값을 가진 유저가 존재하지 않습니다. : ");
            throw new RuntimeException("해당 id 값을 가진 유저가 존재하지 않습니다. : " + account);
        }

        log.info("유저 조회 성공");

        List<AccountSavePlace> accountData = accountSavePlaceRepository.findByContentTypeIdAndPlaceNumAndAccount_Id(req.getContentTypeId(), req.getPlaceNum(), accountId);

        if (accountData.isEmpty()) {
            AccountSavePlace accountSavePlace = new AccountSavePlace(req.getPlaceNum(), req.getContentTypeId(), account);
            accountSavePlaceRepository.save(accountSavePlace);
            log.info("장소 저장 완료");

        } else {
            for (AccountSavePlace accountSavePlace : accountData) {
                log.info("이미 저장되어 있는 장소입니다. : " + accountSavePlace);
            }
        }
    }

    public void DeletePlace(Long accountId){}
}
