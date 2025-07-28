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

/**
 * Service permettant la gestion des commentaires sur les articles.
 * Fournit des opérations pour récupérer ou ajouter des commentaires.
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    /**
     * Récupère tous les commentaires d’un article donné, triés par date décroissante.
     *
     * @param articleId l'identifiant de l'article
     * @return la liste des commentaires associés à l'article
     */
    public List<Comment> getCommentsByArticleId(Long articleId) {
        return commentRepository.findByArticleIdOrderByCreatedAtDesc(articleId);
    }

    /**
     * Ajoute un nouveau commentaire à un article.
     *
     * @param articleId l'identifiant de l'article à commenter
     * @param userEmail l'email de l'utilisateur auteur du commentaire
     * @param content le contenu du commentaire
     * @return le commentaire créé et sauvegardé
     * @throws RuntimeException si l'article ou l'utilisateur n'est pas trouvé
     */
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
