package com.YH.yeohaenghama.domain.diary.repository;

import com.YH.yeohaenghama.domain.diary.entity.DiaryPhotoUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryPhotoUrlRepository extends JpaRepository<DiaryPhotoUrl, Long> {
    List<DiaryPhotoUrl> findByDiaryId(Long diaryId);
}
