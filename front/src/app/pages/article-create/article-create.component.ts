import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Theme } from 'src/app/models/Theme.model';
import { ThemeService } from 'src/app/services/theme.service';
import { ArticleService } from 'src/app/services/article.service';

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
    private themeService: ThemeService,
    private articleService: ArticleService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.articleForm = this.fb.group({
      themeId: ['', Validators.required],
      title: ['', Validators.required],
      content: ['', Validators.required]
    });

    this.themeService.getAllThemes()
      .subscribe({
        next: (themes) => this.themes = themes,
        error: (err) => console.error('Erreur de chargement des thèmes', err)
      });
  }

  onSubmit(): void {
    if (this.articleForm.invalid) return;

    const formValue = {
      ...this.articleForm.value,
      themeId: Number(this.articleForm.value.themeId)
    };

    this.articleService.createArticle(formValue)
      .subscribe({
        next: () => {
          console.log('Article créé avec succès');
          this.router.navigate(['/articles']);
        },
        error: (err) => console.error('Erreur de création d’article', err)
      });
  }
}
