package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.service.ThemeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/themes")
@Tag(name = "Thèmes", description = "Gestion des thèmes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @Operation(summary = "Récupérer tous les thèmes")
    @GetMapping
    public ResponseEntity<List<ThemeDTO>> getAllThemes() {
        return ResponseEntity.ok(themeService.getAllThemes());
    }

    @Operation(summary = "Récupérer les thèmes suivis par l’utilisateur")
    @GetMapping("/subscriptions")
    public ResponseEntity<List<ThemeDTO>> getUserSubscriptions(Principal principal) {
        return ResponseEntity.ok(themeService.getUserSubscriptions(principal.getName()));
    }
}
