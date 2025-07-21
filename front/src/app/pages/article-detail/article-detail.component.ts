import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Article } from 'src/app/models/Article.model';
import { Comment } from 'src/app/models/Comment.model';

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.scss'] 
})
export class ArticleDetailComponent implements OnInit {
  article!: Article;
  comments: Comment[] = [];
  articleId!: string;
  newComment: string = '';

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.articleId = this.route.snapshot.paramMap.get('id')!;
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    // Chargement des infos de l’article
    this.http.get<Article>(`http://localhost:8080/api/articles/${this.articleId}`, { headers })
      .subscribe({
        next: (res) => this.article = res,
        error: (err) => console.error('Erreur chargement article', err)
      });

    // Chargement des commentaires liés à l’article
    this.http.get<Comment[]>(`http://localhost:8080/api/articles/${this.articleId}/comments`, { headers })
      .subscribe({
        next: (res) => this.comments = res,
        error: (err) => console.error('Erreur chargement commentaires', err)
      });
  }

  // Permet à l’utilisateur d’ajouter un commentaire
  postComment(): void {
    if (!this.newComment.trim()) return;

    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const body = { content: this.newComment };

    this.http.post<Comment>(`http://localhost:8080/api/articles/${this.articleId}/comments`, body, { headers })
      .subscribe({
        next: (newCom) => {
          this.comments.unshift(newCom); // Ajoute le commentaire en haut de la liste
          this.newComment = ''; // Réinitialise le champ
        },
        error: (err) => console.error('Erreur ajout commentaire', err)
      });
  }
}
