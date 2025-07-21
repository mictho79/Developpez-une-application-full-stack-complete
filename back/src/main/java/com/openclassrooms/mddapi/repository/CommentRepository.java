package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Récupère les commentaires liés à un article, triés du plus récent au plus ancien
    List<Comment> findByArticleIdOrderByCreatedAtDesc(Long articleId);
}
