import { Component, OnInit } from '@angular/core';
import { ThemeService } from 'src/app/services/theme.service';
import { Theme } from 'src/app/models/Theme.model';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss']
})
export class ThemesComponent implements OnInit {
  themes: Theme[] = [];
  subscribedThemes: number[] = [];

  constructor(private themeService: ThemeService) {}

  ngOnInit(): void {
    this.themeService.getAllThemes().subscribe({
      next: (res) => this.themes = res,
      error: (err) => console.error('Erreur chargement des thèmes', err)
    });

    this.themeService.getUserSubscriptions().subscribe({
      next: (subscribed) => {
        this.subscribedThemes = subscribed.map(theme => theme.id);
      },
      error: (err) => console.error('Erreur chargement des abonnements', err)
    });
  }

  handleSubscribed(themeId: number): void {
    if (!this.subscribedThemes.includes(themeId)) {
      this.subscribedThemes.push(themeId);
    }
  }
}
