package com.YH.yeohaenghama.domain.shorts.service;

import com.YH.yeohaenghama.domain.GCDImage.service.GCSService;
import com.YH.yeohaenghama.domain.account.service.AccountService;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.service.ItineraryService;
import com.YH.yeohaenghama.domain.shorts.dto.UpdateShortsDTO;
import com.YH.yeohaenghama.domain.shorts.dto.UploadShortsDTO;
import com.YH.yeohaenghama.domain.shorts.entity.Shorts;
import com.YH.yeohaenghama.domain.shorts.repository.ShortsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
                itineraryService.getItinerary(req.getItineraryId())));

        return UploadShortsDTO.Response.toEntity(shorts);
    }

    public UploadShortsDTO.Response updateShorts(UpdateShortsDTO.Request req,Long shortsId) throws Exception{
        Shorts shorts = shortsRepository.findById(shortsId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID를 가진 쇼츠가 존재하지 않습니다."));


        UpdateShortsDTO.Update update = new UpdateShortsDTO.Update(shorts,req);
        if(req.getVideo() != null) update.setVideoUrl(gcsService.uploadPhoto(req.getVideo(), String.valueOf(shorts.getId()),"Shorts/"));
        if(req.getItineraryId() !=null) update.setItinerary(itineraryService.getItinerary(req.getItineraryId()));
        shorts = UpdateShortsDTO.updateShorts(shorts,update);
        shortsRepository.save(shorts);

        return UploadShortsDTO.Response.toEntity(shorts);
    }


//    public List<ShortsInItinerary> getShortsInItinerary(Shorts shorts, Long itineraryId){
//        List<ShortsInItinerary> response = new ArrayList<>();
//        if(itineraryId == null) return null;
//        response.add(ShortsInItineraryDTO.toEntity(shorts, itineraryService.getItinerary(itineraryId)));
//        return response;
//    }
}
