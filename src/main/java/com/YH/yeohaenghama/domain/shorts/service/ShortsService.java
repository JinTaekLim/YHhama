package com.YH.yeohaenghama.domain.shorts.service;

import com.YH.yeohaenghama.domain.GCDImage.service.GCSService;
import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.service.AccountService;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.service.ItineraryService;
import com.YH.yeohaenghama.domain.shorts.dto.*;
import com.YH.yeohaenghama.domain.shorts.entity.Shorts;
import com.YH.yeohaenghama.domain.shorts.entity.ShortsLike;
import com.YH.yeohaenghama.domain.shorts.repository.ShortsLikeRepository;
import com.YH.yeohaenghama.domain.shorts.repository.ShortsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShortsService {

    private final ShortsRepository shortsRepository;
    private final ShortsLikeRepository shortsLikeRepository;
    private final GCSService gcsService;
    private final AccountService accountService;
    private final ItineraryService itineraryService;

    public void test(){}

    public Shorts verificationShorts(Long shortsId){
        return shortsRepository.findById(shortsId).orElseThrow(() -> new NoSuchElementException("해당 ID를 가진 쇼츠가 존재하지 않습니다."));
    }

    @Transactional
    public UploadShortsDTO.Response uploadShorts(UploadShortsDTO.Request req) throws Exception {
        Shorts shorts = shortsRepository.save(new Shorts());
        String videoUrl = gcsService.uploadPhoto(req.getVideo(), String.valueOf(shorts.getId()),"Shorts/"+shorts.getId());


        shortsRepository.save(UploadShortsDTO.toShorts(
                shorts,
                accountService.getAccount(req.getAccountId()),
                videoUrl,
                req,
                itineraryService.getItinerary(req.getItineraryId())));

        return UploadShortsDTO.Response.toEntity(shorts);
    }

    public UploadShortsDTO.Response updateShorts(UpdateShortsDTO.Request req,Long shortsId) throws Exception{
        Shorts shorts = verificationShorts(shortsId);


        UpdateShortsDTO.Update update = new UpdateShortsDTO.Update(shorts,req);
        if(req.getVideo() != null) update.setVideoUrl(gcsService.uploadPhoto(req.getVideo(), String.valueOf(shorts.getId()),"Shorts/"));
        if(req.getItineraryId() !=null) update.setItinerary(itineraryService.getItinerary(req.getItineraryId()));
        shorts = UpdateShortsDTO.updateShorts(shorts,update);
        shortsRepository.save(shorts);

        return UploadShortsDTO.Response.toEntity(shorts);
    }

    public ReadShortsDTO.AllResponse readShorts(Integer numOfRows, Integer page) throws Exception {
        Pageable pageable = PageRequest.of(page,numOfRows, Sort.by("id").ascending());
        List<Shorts> shortsList = shortsRepository.findAll(pageable).getContent();
        if(shortsList.isEmpty()) throw new NoSuchObjectException("쇼츠 정보가 존재하지 않습니다.");
        return new ReadShortsDTO.AllResponse(shortsList);
    }

    public String deleteShorts(Long shortsId,Long accountId) throws IOException {
        Shorts shorts = shortsRepository.findById(shortsId)
                .orElseThrow(()-> new NoSuchElementException("해당 ID를 가진 쇼츠가 존재하지 않습니다."));
        if(!shorts.getAccount().getId().equals(accountId)) throw new NoSuchElementException("해당 쇼츠를 삭제할 권한을 보유하고 있지 않습니다.");
        shortsRepository.deleteById(shortsId);
        gcsService.delete("Shorts/" + shortsId);
        return "쇼츠 삭제 성공";
    }

    public LikesDTO.Response likes(LikesDTO.Request req){
        Shorts shorts = verificationShorts(req.getShortsId());
        Account account = accountService.getAccount(req.getAccountId());
        Optional<ShortsLike> shortsLike = shortsLikeRepository.findByShortsIdAndAccountId(req.getShortsId(),req.getAccountId());

        if (req.getState().equals(0)) return LikesDTO.Response.toEntity(shorts,shortsLike.isPresent());

        shortsLike.ifPresentOrElse(shortsLikeRepository::delete, ()->{shortsLikeRepository.save(LikesDTO.fromEntity(shorts,account));});

        return LikesDTO.Response.toEntity(shorts,shortsLike.isEmpty());
    }
}
