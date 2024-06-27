package com.YH.yeohaenghama.domain.shorts.service;

import com.YH.yeohaenghama.domain.GCDImage.service.GCSService;
import com.YH.yeohaenghama.domain.account.service.AccountService;
import com.YH.yeohaenghama.domain.shorts.dto.UploadShortsDTO;
import com.YH.yeohaenghama.domain.shorts.entity.Shorts;
import com.YH.yeohaenghama.domain.shorts.repository.ShortsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShortsService {

    private final GCSService gcsService;
    private final AccountService accountService;
    private final ShortsRepository shortsRepository;

    @Transactional
    public UploadShortsDTO.Response uploadShorts(UploadShortsDTO.Request req) throws Exception {


        Shorts shorts = shortsRepository.save(new Shorts());
        String videoUrl = gcsService.uploadPhoto(req.getVideo(), String.valueOf(shorts.getId()),"Shorts/");
        shorts = shortsRepository.save(UploadShortsDTO.toShorts(shorts,accountService.getAccount(req.getAccountId()),videoUrl,req));

        return UploadShortsDTO.Response.toEntity(shorts);
    }
}
