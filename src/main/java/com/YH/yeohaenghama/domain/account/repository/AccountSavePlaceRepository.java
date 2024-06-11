package com.YH.yeohaenghama.domain.account.repository;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.entity.AccountSavePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.Optional;

public interface AccountSavePlaceRepository extends JpaRepository<AccountSavePlace,Long> {

    List<AccountSavePlace> findByContentTypeIdAndPlaceNumAndAccount_IdAndItineraryId(String contentTypeId, String placeNum, Long accountId, Long itineraryId);


    List<AccountSavePlace> findByAccountId(Long accountId);
    List<AccountSavePlace> findByAccountIdAndItineraryId(Long accountId, Long itineraryId);
}
