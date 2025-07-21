import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-back-button',
  templateUrl: './back-button.component.html',
  styleUrls: ['./back-button.component.scss'] 
})
export class BackButtonComponent {
  @Input() route = '/';

  constructor(private router: Router) {}

  goBack(): void {
    this.router.navigate([this.route]);
  }

  isAuthPage(): boolean {
    return this.router.url === '/login' || this.router.url === '/register';
  }
}
