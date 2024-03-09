package com.YH.yeohaenghama.domain.diary.repository;

import com.YH.yeohaenghama.domain.diary.entity.DiaryDetail;
import com.YH.yeohaenghama.domain.diary.entity.DiaryPhotoUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryDetailRespository extends JpaRepository<DiaryDetail ,Long> {
    List<DiaryDetail> findByDiaryId(Long diaryId);

}
