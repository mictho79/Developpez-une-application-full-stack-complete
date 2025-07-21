package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByOrderByCreatedAtDesc();
    @Query("SELECT a FROM Article a JOIN a.theme t JOIN t.subscribers s WHERE s.id = :userId")
    List<Article> findAllBySubscribedThemes(@Param("userId") Long userId);

}
