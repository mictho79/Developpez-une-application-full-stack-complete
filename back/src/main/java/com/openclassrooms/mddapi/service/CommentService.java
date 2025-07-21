package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public List<Comment> getCommentsByArticleId(Long articleId) {
        return commentRepository.findByArticleIdOrderByCreatedAtDesc(articleId);
    }

    public Comment addComment(Long articleId, String userEmail, String content) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article introuvable"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Comment comment = Comment.builder()
                .content(content)
                .author(user)
                .article(article)
                .build();

        return commentRepository.save(comment);
    }
}
