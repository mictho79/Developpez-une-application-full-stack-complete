package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleRequestDto;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAllByOrderByCreatedAtDesc();
    }

    public Article createArticle(ArticleRequestDto requestDto, User user, Theme theme) {
        Article article = Article.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .author(user)
                .theme(theme)
                .build();
        return articleRepository.save(article);
    }
    public List<Article> getArticlesForUser(Long userId) {

        List<Article> list = articleRepository.findAllBySubscribedThemes(userId);
        return list;
    }
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }
}
