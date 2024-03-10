package com.YH.yeohaenghama.domain.diary.service;

import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.entity.DiaryPhotoUrl;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.GCDImage.service.GCSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final GCSService gcsService;

    public DiaryDTO.Response save(DiaryDTO.Request diaryDTO) throws IOException {


        Diary diary = new Diary();

        List<DiaryPhotoUrl> diaryPhotoUrls = new ArrayList<>();
        List<MultipartFile> photos = diaryDTO.getPhotos();

        int i = 0;
        for (MultipartFile photo : photos) {
            String PhotoURL = gcsService.uploadPhoto(photo, String.valueOf(i),"Diary/"+diaryDTO.getItinerary());
            DiaryPhotoUrl diaryPhotoUrl = new DiaryPhotoUrl(diary,PhotoURL);
            diaryPhotoUrls.add(diaryPhotoUrl);
            i++;
        }

        diary.set(diaryDTO.getDate(), diaryDTO.getTitle(), diaryDTO.getContent(), diaryDTO.getItinerary(), diaryPhotoUrls);

        diaryRepository.save(diary);

        return DiaryDTO.Response.fromEntity(diary);
    }


        public void delete(Long diaryId) throws IOException {
        if(diaryRepository.findById(diaryId).isEmpty()){
            throw new NoSuchElementException("해당 ID를 가진 일기가 존재하지 않습니다.");
        }
        Optional<Diary> optionalDiary = diaryRepository.findById(diaryId);

        gcsService.delete("Diary/"+optionalDiary.get().getItinerary());
        diaryRepository.deleteById(diaryId);

    }

}
