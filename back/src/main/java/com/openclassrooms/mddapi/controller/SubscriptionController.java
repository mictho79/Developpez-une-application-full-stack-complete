package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.SubscriptionRequestDTO;
import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@Tag(name = "Abonnements", description = "Gérer les abonnements aux thèmes")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Operation(summary = "S'abonner à un thème")
    @PostMapping
    public ResponseEntity<?> subscribe(@RequestBody SubscriptionRequestDTO request, Principal principal) {
        subscriptionService.subscribe(principal.getName(), request.getThemeId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Se désabonner d’un thème")
    @DeleteMapping("/{themeId}")
    public ResponseEntity<?> unsubscribe(@PathVariable Long themeId, Principal principal) {
        subscriptionService.unsubscribe(principal.getName(), themeId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Voir les abonnements de l’utilisateur")
    @GetMapping
    public ResponseEntity<List<ThemeDTO>> getSubscriptions(Principal principal) {
        List<Theme> themes = subscriptionService.getUserSubscriptions(principal.getName());
        List<ThemeDTO> dtos = themes.stream()
                .map(t -> new ThemeDTO(t.getId(), t.getTitle(), t.getDescription()))
                .toList();
        return ResponseEntity.ok(dtos);
    }
}
