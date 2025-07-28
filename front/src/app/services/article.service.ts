import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Article } from '../models/Article.model';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  private readonly apiUrl = 'http://localhost:8080/api/articles';

  constructor(private http: HttpClient) {}

  // Récupère les articles des abonnements
  getSubscribedArticles(): Observable<Article[]> {
    return this.http.get<Article[]>(`${this.apiUrl}/subscriptions`);
  }

  // Récupère un article par son ID
  getArticleById(id: number | string): Observable<Article> {
    return this.http.get<Article>(`${this.apiUrl}/${id}`);
  }

  // Crée un nouvel article
  createArticle(data: { title: string; content: string; themeId: number }): Observable<any> {
    return this.http.post(this.apiUrl, data);
  }
}
