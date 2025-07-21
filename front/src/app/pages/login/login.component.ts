import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {
    // Initialisation du formulaire avec validation obligatoire sur les champs
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  // Soumission du formulaire de connexion
  onSubmit(): void {
    if (this.loginForm.invalid) return;

    this.http.post('http://localhost:8080/api/auth/login', this.loginForm.value)
      .subscribe({
        next: (res: any) => {
          // On stocke le token dans le localStorage pour les prochaines requêtes
          localStorage.setItem('token', res.token);
          // Redirection vers la liste des articles après connexion
          this.router.navigate(['/articles']); 
        },
        error: (err) => {
          // Gestion des erreurs de connexion
          console.error('Erreur de connexion', err);
        }
      });
  }
}
