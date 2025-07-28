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

/**
 * Contrôleur REST pour gérer les abonnements de l'utilisateur aux thèmes.
 */
@RestController
@RequestMapping("/api/subscriptions")
@Tag(name = "Abonnements", description = "Gérer les abonnements aux thèmes")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * Permet à l'utilisateur connecté de s'abonner à un thème.
     *
     * @param request   Objet contenant l'ID du thème ciblé
     * @param principal Utilisateur connecté (récupéré automatiquement via le token)
     * @return Réponse HTTP 200 si l’abonnement est un succès
     */
    @Operation(summary = "S'abonner à un thème")
    @PostMapping
    public ResponseEntity<?> subscribe(@RequestBody SubscriptionRequestDTO request, Principal principal) {
        subscriptionService.subscribe(principal.getName(), request.getThemeId());
        return ResponseEntity.ok().build();
    }

    /**
     * Permet à l'utilisateur connecté de se désabonner d'un thème.
     *
     * @param themeId   ID du thème ciblé
     * @param principal Utilisateur connecté
     * @return Réponse HTTP 200 si le désabonnement a réussi
     */
    @Operation(summary = "Se désabonner d’un thème")
    @DeleteMapping("/{themeId}")
    public ResponseEntity<?> unsubscribe(@PathVariable Long themeId, Principal principal) {
        subscriptionService.unsubscribe(principal.getName(), themeId);
        return ResponseEntity.ok().build();
    }

    /**
     * Récupère tous les thèmes auxquels l'utilisateur est actuellement abonné.
     *
     * @param principal Utilisateur connecté
     * @return Liste des thèmes abonnés, au format DTO
     */
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
