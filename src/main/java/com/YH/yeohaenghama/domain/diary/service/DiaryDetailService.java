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


}
