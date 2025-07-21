import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Theme } from 'src/app/models/Theme.model';

@Component({
  selector: 'app-theme-card',
  templateUrl: './theme-card.component.html',
  styleUrls: ['./theme-card.component.scss'] 
})
export class ThemeCardComponent {
  // Le thème à afficher dans la carte
  @Input() theme!: Theme;

  // Indique si l'utilisateur est déjà abonné à ce thème
  @Input() isSubscribed: boolean = false;

  // Événement déclenché quand l'utilisateur clique pour s'abonner
  @Output() subscribe = new EventEmitter<number>();

  // Événement déclenché quand l'utilisateur clique pour se désabonner
  @Output() unsubscribe = new EventEmitter<number>();

  // Gestion du clic : émet l'événement approprié selon l'état d'abonnement
  onClick(): void {
    this.isSubscribed
      ? this.unsubscribe.emit(this.theme.id)
      : this.subscribe.emit(this.theme.id);
  }
}
