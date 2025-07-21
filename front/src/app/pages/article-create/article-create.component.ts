import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Theme } from 'src/app/models/Theme.model';

@Component({
  selector: 'app-article-create',
  templateUrl: './article-create.component.html',
  styleUrls: ['./article-create.component.scss'] 
})
export class ArticleCreateComponent implements OnInit {
  articleForm!: FormGroup;
  themes: Theme[] = [];

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Initialisation du formulaire avec validation
    this.articleForm = this.fb.group({
      themeId: ['', Validators.required],
      title: ['', Validators.required],
      content: ['', Validators.required]
    });

    // Récupération des thèmes disponibles depuis l’API
    this.http.get<Theme[]>('http://localhost:8080/api/themes')
      .subscribe({
        next: (themes) => this.themes = themes,
        error: (err) => console.error('Erreur de chargement des thèmes', err)
      });
  }

  // Soumission du formulaire
  onSubmit(): void {
    if (this.articleForm.invalid) return;

    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Aucun token trouvé');
      return;
    }

    // On s’assure que themeId est bien un nombre
    const formValue = {
      ...this.articleForm.value,
      themeId: Number(this.articleForm.value.themeId)
    };

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    this.http.post('http://localhost:8080/api/articles', formValue, { headers })
      .subscribe({
        next: () => {
          console.log('Article créé avec succès');
          this.router.navigate(['/articles']);
        },
        error: (err) => console.error('Erreur de création d’article', err)
      });
  }
}
