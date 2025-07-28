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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour la gestion des articles.
 * Permet de créer, consulter et filtrer les articles en fonction des abonnements.
 */
@RestController
@RequestMapping("/api/articles")
@CrossOrigin
@Tag(name = "Articles", description = "Opérations liées aux articles")
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

    /**
     * Récupère tous les articles disponibles dans la base de données.
     *
     * @return Liste d'articles au format DTO
     */
    @Operation(summary = "Récupérer tous les articles")
    @GetMapping
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        List<ArticleDto> articles = articleService.getAllArticles()
                .stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(articles);
    }

    /**
     * Crée un nouvel article associé à l'utilisateur connecté et à un thème.
     *
     * @param requestDto  Données nécessaires à la création de l'article
     * @param authHeader  En-tête HTTP contenant le token JWT (Bearer)
     * @return Article créé, encapsulé dans un DTO
     * @throws ResponseStatusException si l'utilisateur ou le thème est introuvable
     */
    @Operation(summary = "Créer un nouvel article")
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

    /**
     * Récupère tous les articles liés aux thèmes suivis par l'utilisateur connecté.
     *
     * @param authHeader En-tête HTTP contenant le token JWT (Bearer)
     * @return Liste d'articles au format DTO
     * @throws RuntimeException si l'utilisateur est introuvable
     */
    @Operation(summary = "Récupérer les articles liés aux abonnements de l'utilisateur")
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

    /**
     * Récupère un article par son identifiant.
     *
     * @param id Identifiant de l'article à récupérer
     * @return Article trouvé au format DTO
     * @throws ResponseStatusException si l'article est introuvable
     */
    @Operation(summary = "Récupérer un article par son ID")
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        Article article = articleService.getArticleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article introuvable"));

        return ResponseEntity.ok(ArticleDto.fromEntity(article));
    }
}
