package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping
    public ResponseEntity<List<ThemeDTO>> getAllThemes() {
        return ResponseEntity.ok(themeService.getAllThemes());
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<List<ThemeDTO>> getUserSubscriptions(Principal principal) {
        return ResponseEntity.ok(themeService.getUserSubscriptions(principal.getName()));
    }
}
