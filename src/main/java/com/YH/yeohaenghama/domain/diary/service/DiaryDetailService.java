package com.YH.yeohaenghama.domain.diary.service;

import com.YH.yeohaenghama.domain.diary.dto.DiaryDetailDTO;
import com.YH.yeohaenghama.domain.diary.entity.DiaryDetail;
import com.YH.yeohaenghama.domain.diary.entity.DiaryDetailPhotoURL;
import com.YH.yeohaenghama.domain.diary.repository.DiaryDetailPhotoUrlRepository;
import com.YH.yeohaenghama.domain.diary.repository.DiaryDetailRespository;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryDetailService {
    private final DiaryDetailRespository diaryDetailRespository;
    private final DiaryDetailPhotoUrlRepository diaryDetailPhotoUrlRepository;
    private final DiaryRepository diaryRepository;

    public DiaryDetailDTO.Response detailSave(DiaryDetailDTO.Request req){
        log.info("DiaryDetailDTO 확인 : " + req);
        if(diaryRepository.findById(req.getDiaryId()).isEmpty()){
            log.info("해당 ID를 가진 일정이 존재하지 않습니다. : " + req.getDiaryId());
            throw new NoSuchElementException("해당 id 값을 가진 일정이 존재하지 않습니다. : " + req.getDiaryId());
        };

        DiaryDetailDTO diaryDetailDTO = new DiaryDetailDTO(req);
        DiaryDetail diaryDetail = diaryDetailRespository.save(diaryDetailDTO.toEntity());

        for (String photoUrl : req.getPhotoURL()) {
            DiaryDetailPhotoURL diaryDetailPhotoURL = DiaryDetailPhotoURL.builder()
                    .diaryDetail(diaryDetail)
                    .photoUrl(photoUrl)
                    .build();
            diaryDetailPhotoUrlRepository.save(diaryDetailPhotoURL);
        }

        return DiaryDetailDTO.Response.fromEntity(diaryDetail);
    }


    public boolean checkDiary(Long diaryId) {
        return diaryRepository.findById(diaryId).isPresent();
    }

}
