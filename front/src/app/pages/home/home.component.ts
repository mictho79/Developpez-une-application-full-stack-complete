import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {

  constructor() {}

  ngOnInit(): void {
    // Aucun traitement au démarrage pour l’instant
  }

  // Affiche une alerte pour inciter à lire le README
  start(): void {
    alert('Commencez par lire le README et à vous de jouer !');
  }
}
