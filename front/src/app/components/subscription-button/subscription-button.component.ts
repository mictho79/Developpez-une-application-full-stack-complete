import { Component, Input, Output, EventEmitter } from '@angular/core';
import { ThemeService } from '../../services/theme.service';

@Component({
  selector: 'app-subscription-button',
  templateUrl: './subscription-button.component.html',
  styleUrls: ['./subscription-button.component.scss'] 
})
export class SubscriptionButtonComponent {
  // ID du thème ciblé par le bouton
  @Input() themeId!: number;

  // Si l'utilisateur est déjà abonné ou non (géré depuis le parent)
  @Input() isSubscribed = false;

  // Événement émis après abonnement réussi
  @Output() subscribed = new EventEmitter<void>();

  // Empêche plusieurs clics pendant le traitement
  loading = false;

  constructor(private themeService: ThemeService) {}

  // Méthode appelée au clic sur le bouton
  onSubscribe(): void {
    if (this.isSubscribed || this.loading) return; // évite les doublons

    this.loading = true;

    this.themeService.subscribe(this.themeId).subscribe({
      next: () => {
        this.subscribed.emit(); // on notifie le parent que c'est bon
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur abonnement :', err);
        this.loading = false;
      }
    });
  }
}
