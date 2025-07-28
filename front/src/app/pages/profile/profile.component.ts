import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Theme } from 'src/app/models/Theme.model';
import { UserService } from 'src/app/services/user.service';
import { AuthService } from 'src/app/services/auth.service';
import { ThemeService } from 'src/app/services/theme.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  profileForm!: FormGroup;
  subscribedThemes: Theme[] = [];

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private authService: AuthService,
    private themeService: ThemeService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.profileForm = this.fb.group({
      username: [''],
      email: [''],
      password: ['']
    });

    this.themeService.getUserSubscriptions().subscribe({
      next: (themes) => this.subscribedThemes = themes,
      error: (err) => console.error('Erreur chargement abonnements', err)
    });
  }

  onSubmit(): void {
    if (this.profileForm.valid) {
      this.userService.updateUser(this.profileForm.value).subscribe({
        next: () => {
          alert('Profil mis à jour !');
          this.profileForm.patchValue({ password: '' });
        },
        error: (err) => console.error('Erreur mise à jour profil', err)
      });
    }
  }

  onUnsubscribe(themeId: number): void {
    this.themeService.unsubscribe(themeId).subscribe(() => {
      this.subscribedThemes = this.subscribedThemes.filter(t => t.id !== themeId);
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
