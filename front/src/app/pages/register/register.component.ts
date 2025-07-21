import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  // Formulaire réactif pour l'inscription
  registerForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {
    // Initialisation du formulaire avec les validations
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  // Soumission du formulaire
  onSubmit(): void {
    if (this.registerForm.invalid) return;

    // Appel API pour enregistrer l'utilisateur
    this.http.post('http://localhost:8080/api/auth/register', this.registerForm.value)
      .subscribe({
        next: (res: any) => {
          // Sauvegarde du token dans le localStorage
          localStorage.setItem('token', res.token);
          // Redirection vers la page des articles
          this.router.navigate(['/articles']);
        },
        error: (err) => {
          console.error('Erreur lors de l’inscription', err);
        }
      });
  }
}
