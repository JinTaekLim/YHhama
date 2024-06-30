package com.YH.yeohaenghama.domain.shorts.service;

import com.YH.yeohaenghama.domain.GCDImage.service.GCSService;
import com.YH.yeohaenghama.domain.account.service.AccountService;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.service.ItineraryService;
import com.YH.yeohaenghama.domain.shorts.dto.ReadShortsDTO;
import com.YH.yeohaenghama.domain.shorts.dto.UpdateShortsDTO;
import com.YH.yeohaenghama.domain.shorts.dto.UploadShortsDTO;
import com.YH.yeohaenghama.domain.shorts.entity.Shorts;
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

    public ReadShortsDTO.AllResponse readShorts(ReadShortsDTO.AllRequest req) throws Exception {
        log.info("1");
        Pageable pageable = PageRequest.of(req.getPage(),req.getNumOfRows(), Sort.by("id").ascending());
        log.info("2");
        List<Shorts> shortsList = shortsRepository.findAll(pageable).getContent();
        if(shortsList.isEmpty()) throw new NoSuchObjectException("쇼츠 정보가 존재하지 않습니다.");
        log.info("3");
        return new ReadShortsDTO.AllResponse(shortsList);
    }


//    public List<ShortsInItinerary> getShortsInItinerary(Shorts shorts, Long itineraryId){
//        List<ShortsInItinerary> response = new ArrayList<>();
//        if(itineraryId == null) return null;
//        response.add(ShortsInItineraryDTO.toEntity(shorts, itineraryService.getItinerary(itineraryId)));
//        return response;
//    }
}
