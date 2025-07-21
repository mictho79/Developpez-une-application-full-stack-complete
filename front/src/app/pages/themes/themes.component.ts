import { Component, OnInit } from '@angular/core';
import { ThemeService } from '../../services/theme.service';
import { Theme } from 'src/app/models/Theme.model';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss']
})
export class ThemesComponent implements OnInit {
  // Liste de tous les thèmes
  themes: Theme[] = [];

  // Liste des IDs des thèmes auxquels l'utilisateur est déjà abonné
  subscribedThemes: number[] = [];

  constructor(private themeService: ThemeService) {}

  ngOnInit(): void {
    // Récupère tous les thèmes
    this.themeService.getAllThemes().subscribe({
      next: (res) => this.themes = res,
      error: (err) => console.error('Erreur chargement des thèmes', err)
    });

    // Récupère les abonnements de l'utilisateur
    this.themeService.getUserSubscriptions().subscribe({
      next: (subscribed) => {
        this.subscribedThemes = subscribed.map(theme => theme.id);
      },
      error: (err) => console.error('Erreur chargement des abonnements', err)
    });
  }

  // Lorsqu’un abonnement a lieu via le bouton
  handleSubscribed(themeId: number): void {
    if (!this.subscribedThemes.includes(themeId)) {
      this.subscribedThemes.push(themeId);
    }
  }
}
