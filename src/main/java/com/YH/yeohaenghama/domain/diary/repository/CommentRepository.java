package com.YH.yeohaenghama.domain.diary.repository;

import com.YH.yeohaenghama.domain.diary.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByDiaryId(Long diaryId);
}
