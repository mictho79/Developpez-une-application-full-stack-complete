import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Comment } from '../models/Comment.model';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  constructor(private http: HttpClient) {}

  // Récupère tous les commentaires pour un article donné
  getCommentsForArticle(articleId: number | string): Observable<Comment[]> {
    return this.http.get<Comment[]>(`http://localhost:8080/api/articles/${articleId}/comments`);
  }

  // Envoie un commentaire pour un article
  postComment(articleId: number | string, content: string): Observable<Comment> {
    return this.http.post<Comment>(
      `http://localhost:8080/api/articles/${articleId}/comments`,
      { content }
    );
  }
}
