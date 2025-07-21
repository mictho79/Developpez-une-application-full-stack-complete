import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Article } from 'src/app/models/Article.model';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.scss']
})
export class ArticleListComponent implements OnInit {
  articles: Article[] = [];
  sortOrder: 'asc' | 'desc' = 'desc';

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (!token) return;

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    this.http.get<Article[]>('http://localhost:8080/api/articles/subscriptions', { headers })
      .subscribe({
        next: (res) => {
          this.articles = res;
          this.sortArticles(); // Appliquer un tri dès la réception
        },
        error: (err) => console.error('Erreur chargement articles', err)
      });
  }

  // Tri les articles selon l’ordre choisi
  sortArticles(): void {
    const getTime = (date: string) => new Date(date).getTime();
    this.articles.sort((a, b) =>
      this.sortOrder === 'asc'
        ? getTime(a.createdAt) - getTime(b.createdAt)
        : getTime(b.createdAt) - getTime(a.createdAt)
    );
  }

  // Redirige vers la page de détail d’un article
  goToArticle(id: number): void {
    this.router.navigate(['/articles', id]);
  }

  toggleSortOrder() {
  this.sortOrder = this.sortOrder === 'desc' ? 'asc' : 'desc';
  this.sortArticles();
}
}
