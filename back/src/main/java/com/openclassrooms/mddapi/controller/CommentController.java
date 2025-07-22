package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.config.JwtUtil;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles/{articleId}/comments")
@CrossOrigin
@Tag(name = "Commentaires", description = "Opérations liées aux commentaires")
public class CommentController {

    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    public CommentController(CommentService commentService, JwtUtil jwtUtil) {
        this.commentService = commentService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Récupérer les commentaires d’un article")
    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long articleId) {
        List<Comment> comments = commentService.getCommentsByArticleId(articleId);
        List<CommentDto> dtos = comments.stream().map(CommentDto::fromEntity).toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Ajouter un commentaire à un article")
    @PostMapping
    public ResponseEntity<CommentDto> addComment(
            @PathVariable Long articleId,
            @RequestBody CommentDto commentDto,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);
        Comment comment = commentService.addComment(articleId, email, commentDto.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(CommentDto.fromEntity(comment));
    }
}
