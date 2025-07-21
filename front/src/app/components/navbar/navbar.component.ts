import { Component, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  public showMenu = false;
  isMobileScreen = window.innerWidth <= 768;

  constructor(public auth: AuthService, private router: Router) {}

  @HostListener('window:resize', ['$event'])
  onResize(): void {
    this.isMobileScreen = window.innerWidth <= 768;
  }

  isMobile(): boolean {
    return window.innerWidth <= 768;
  }

  isHomePage(): boolean {
    return this.router.url === '/';
  }

  isAuthPage(): boolean {
    return this.router.url === '/login' || this.router.url === '/register';
  }

 logout(): void {
    this.auth.logout();
    this.router.navigate(['/']);
  }
  isLoggedIn(): boolean {
  return this.auth.isLoggedIn();
}

  toggleMenu(): void {
    this.showMenu = !this.showMenu;
  }
}
