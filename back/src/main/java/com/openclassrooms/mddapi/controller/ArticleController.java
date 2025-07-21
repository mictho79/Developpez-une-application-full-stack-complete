package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.config.JwtUtil;
import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.ArticleRequestDto;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin
public class ArticleController {

    private final ArticleService articleService;
    private final ThemeRepository themeRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public ArticleController(
            ArticleService articleService,
            ThemeRepository themeRepository,
            UserRepository userRepository,
            JwtUtil jwtUtil
    ) {
        this.articleService = articleService;
        this.themeRepository = themeRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        List<ArticleDto> articles = articleService.getAllArticles()
                .stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(articles);
    }

    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(
            @RequestBody ArticleRequestDto requestDto,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utilisateur non trouvé"));

        Theme theme = themeRepository.findById(requestDto.getThemeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Thème introuvable"));

        Article article = articleService.createArticle(requestDto, user, theme);
        return ResponseEntity.status(HttpStatus.CREATED).body(ArticleDto.fromEntity(article));
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<List<ArticleDto>> getSubscribedArticles(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        List<Article> articles = articleService.getArticlesForUser(user.getId());

        List<ArticleDto> dtos = articles.stream()
                .map(ArticleDto::fromEntity)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        Article article = articleService.getArticleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article introuvable"));

        return ResponseEntity.ok(ArticleDto.fromEntity(article));
    }
}
