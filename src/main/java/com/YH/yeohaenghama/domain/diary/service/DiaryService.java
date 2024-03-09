package com.YH.yeohaenghama.domain.diary.service;

import com.YH.yeohaenghama.domain.diary.dto.DiaryDTO;
import com.YH.yeohaenghama.domain.diary.dto.DiaryDetailDTO;
import com.YH.yeohaenghama.domain.diary.dto.DiaryShowDTO;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.entity.DiaryDetail;
import com.YH.yeohaenghama.domain.diary.entity.DiaryDetailPhotoURL;
import com.YH.yeohaenghama.domain.diary.entity.DiaryPhotoUrl;
import com.YH.yeohaenghama.domain.diary.repository.DiaryDetailPhotoUrlRepository;
import com.YH.yeohaenghama.domain.diary.repository.DiaryDetailRespository;
import com.YH.yeohaenghama.domain.diary.repository.DiaryPhotoUrlRepository;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.uploadImage.service.GCSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final DiaryDetailRespository diaryDetailRespository;
    private final ItineraryRepository itineraryRepository;
    private final DiaryPhotoUrlRepository diaryPhotoUrlRepository;
    private final DiaryDetailPhotoUrlRepository diaryDetailPhotoUrlRepository;
    private final GCSService gcsService;

    public DiaryDTO.Response save(DiaryDTO.Request req) {
        log.info("DiaryDTO 확인 : " + req);

        if (itineraryRepository.findById(req.getItinerary()).isEmpty()) {
            throw new NoSuchElementException("해당 ID를 가진 일정이 존재하지 않습니다. ");
        }

        DiaryDTO diaryDTO = new DiaryDTO(req);
        Diary diary = diaryRepository.save(diaryDTO.toEntity());        // 일기 생성

        Long DiaryID = diary.getId();


        if (req.getPhotos() != null) {
            for (MultipartFile photo : req.getPhotos()) {                   // 일기 사진 저장
                try {
                    String photoURL = gcsService.uploadPhoto(photo, photo.getOriginalFilename(), "Diary/" + DiaryID);
                    DiaryPhotoUrl diaryPhotoUrl = new DiaryPhotoUrl(diary, photoURL);
                    diaryPhotoUrlRepository.save(diaryPhotoUrl);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (req.getDetail() != null) {

            for (DiaryDetailDTO.Request detail : req.getDetail()) {             // 일정 별 일기 저장
                detail.setDiary(diary);
                DiaryDetailDTO diaryDetailDTO = new DiaryDetailDTO(detail);
                DiaryDetail diaryDetail = diaryDetailRespository.save(diaryDetailDTO.toEntity());


                if (detail.getPhotos() != null) {
                    for (MultipartFile photo : detail.getPhotos()) {                   // 일기 일정 별 사진 저장
                        try {
                            String photoURL = gcsService.uploadPhoto(photo, photo.getOriginalFilename(), "Diary/" + DiaryID + "/" + detail.getDay());
                            DiaryDetailPhotoURL diaryDetailPhotoURL = new DiaryDetailPhotoURL(diaryDetail, photoURL);
                            diaryDetailPhotoUrlRepository.save(diaryDetailPhotoURL);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

            }

        }

        return DiaryDTO.Response.fromEntity(diary);

    }


    public void delete(Long diaryId){
        if(diaryRepository.findById(diaryId).isEmpty()){
            throw new NoSuchElementException("해당 ID를 가진 일기가 존재하지 않습니다.");
        }
        diaryRepository.deleteById(diaryId);

    }


    public DiaryShowDTO show(Long diaryId){
        Optional<Diary> diaryOpt = diaryRepository.findById(diaryId);
        DiaryShowDTO showDTO = new DiaryShowDTO();

        diaryOpt.ifPresent(diary -> {
            log.info("Diary found - Title: " + diary.getTitle() + ", Content: " + diary.getContent());


            showDTO.setItinerary(diary.getItinerary());
            showDTO.setTitle(diary.getTitle());
            showDTO.setDate(diary.getDate());
            showDTO.setContent(diary.getContent());


            List<DiaryPhotoUrl> photoUrls = diaryPhotoUrlRepository.findByDiaryId(diaryId);     // DiaryPhoto 정보 중 Id값을 제외
            List<String> photoURLs = photoUrls.stream()
                    .map(DiaryPhotoUrl::getPhotoURL)
                    .collect(Collectors.toList());


            showDTO.setPhotos(photoURLs);


            List<DiaryDetail> diaryDetails = diaryDetailRespository.findByDiaryId(diaryId);

            List<DiaryDetailDTO.Response> diaryDetailDTOs = new ArrayList<>();
            for (DiaryDetail detail : diaryDetails) {
                DiaryDetailDTO.Response response = DiaryDetailDTO.Response.fromEntity(detail);
                diaryDetailDTOs.add(response);
            }

            showDTO.setDiaryDetailDTO(diaryDetailDTOs);


        });
        return showDTO;
    }








}
