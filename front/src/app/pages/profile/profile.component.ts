import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService, UserDTO } from 'src/app/services/user.service';
import { AuthService } from 'src/app/services/auth.service';
import { ThemeService } from 'src/app/services/theme.service';
import { Theme } from 'src/app/models/Theme.model';
import { Router } from '@angular/router';

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
    // Initialisation du formulaire avec validation
   this.profileForm = this.fb.group({
  username: [''],
  email: [''],
  password: ['']
});


    // Récupération des thèmes auxquels l'utilisateur est abonné
    this.themeService.getUserSubscriptions().subscribe((themes) => {
      this.subscribedThemes = themes;
    });
  }

  // Enregistrement des modifications du profil
 onSubmit(): void {
  if (this.profileForm.valid) {
    const formValues = this.profileForm.value;

    // Envoie tous les champs en une seule requête
    this.userService.updateUser(formValues).subscribe(() => {
      alert('Profil mis à jour !');
      this.profileForm.patchValue({ password: '' }); // reset le champ mot de passe
    });
  }
}

  // Désabonnement d’un thème
  onUnsubscribe(themeId: number): void {
    this.themeService.unsubscribe(themeId).subscribe(() => {
      this.subscribedThemes = this.subscribedThemes.filter(t => t.id !== themeId);
    });
  }

  // Déconnexion
  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
