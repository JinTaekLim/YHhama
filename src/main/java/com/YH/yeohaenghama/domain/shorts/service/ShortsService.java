package com.YH.yeohaenghama.domain.shorts.service;

import com.YH.yeohaenghama.domain.GCDImage.service.GCSService;
import com.YH.yeohaenghama.domain.account.service.AccountService;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.service.ItineraryService;
import com.YH.yeohaenghama.domain.shorts.dto.ShortsInItineraryDTO;
import com.YH.yeohaenghama.domain.shorts.dto.UploadShortsDTO;
import com.YH.yeohaenghama.domain.shorts.entity.Shorts;
import com.YH.yeohaenghama.domain.shorts.entity.ShortsInItinerary;
import com.YH.yeohaenghama.domain.shorts.repository.ShortsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShortsService {

    private final GCSService gcsService;
    private final AccountService accountService;
    private final ShortsRepository shortsRepository;
    private final ItineraryService itineraryService;

    public void test(){}

    @Transactional
    public UploadShortsDTO.Response uploadShorts(UploadShortsDTO.Request req) throws Exception {
        Shorts shorts = shortsRepository.save(new Shorts());
        String videoUrl = gcsService.uploadPhoto(req.getVideo(), String.valueOf(shorts.getId()),"Shorts/");

        shortsRepository.save(UploadShortsDTO.toShorts(
                shorts,
                accountService.getAccount(req.getAccountId()),
                videoUrl,
                req,
                getShortsInItinerary(shorts,req.getItineraryId())));

        return UploadShortsDTO.Response.toEntity(shorts);
    }


    public List<ShortsInItinerary> getShortsInItinerary(Shorts shorts, Long itineraryId){
        List<ShortsInItinerary> response = new ArrayList<>();
        if(itineraryId == null) return null;
        response.add(ShortsInItineraryDTO.toEntity(shorts, itineraryService.getItinerary(itineraryId)));
        return response;
    }
}
