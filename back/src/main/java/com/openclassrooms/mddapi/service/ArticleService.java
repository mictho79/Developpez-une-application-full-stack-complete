package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleRequestDto;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service permettant la gestion des articles.
 * Fournit des opérations pour créer, récupérer et filtrer les articles.
 */
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    /**
     * Constructeur du service ArticleService.
     *
     * @param articleRepository le repository utilisé pour accéder aux articles
     */
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * Récupère tous les articles, triés du plus récent au plus ancien.
     *
     * @return une liste d'articles
     */
    public List<Article> getAllArticles() {
        return articleRepository.findAllByOrderByCreatedAtDesc();
    }

    /**
     * Crée un nouvel article en l'associant à un utilisateur et à un thème.
     *
     * @param requestDto les données du nouvel article
     * @param user l'auteur de l'article
     * @param theme le thème auquel est lié l'article
     * @return l'article créé et sauvegardé
     */
    public Article createArticle(ArticleRequestDto requestDto, User user, Theme theme) {
        Article article = Article.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .author(user)
                .theme(theme)
                .build();
        return articleRepository.save(article);
    }

    /**
     * Récupère les articles associés aux thèmes suivis par l'utilisateur.
     *
     * @param userId l'identifiant de l'utilisateur
     * @return une liste d'articles liés aux abonnements de l'utilisateur
     */
    public List<Article> getArticlesForUser(Long userId) {
        return articleRepository.findAllBySubscribedThemes(userId);
    }

    /**
     * Récupère un article à partir de son identifiant.
     *
     * @param id l'identifiant de l'article
     * @return un Optional contenant l'article, ou vide s'il n'existe pas
     */
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }
}
