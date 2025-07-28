import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Article } from 'src/app/models/Article.model';
import { Comment } from 'src/app/models/Comment.model';
import { ArticleService } from 'src/app/services/article.service';
import { CommentService } from 'src/app/services/comment.service';

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
    private articleService: ArticleService,
    private commentService: CommentService
  ) {}

  ngOnInit(): void {
    this.articleId = this.route.snapshot.paramMap.get('id')!;

    this.articleService.getArticleById(this.articleId)
      .subscribe({
        next: (res) => this.article = res,
        error: (err) => console.error('Erreur chargement article', err)
      });

    this.commentService.getCommentsForArticle(this.articleId)
      .subscribe({
        next: (res) => this.comments = res,
        error: (err) => console.error('Erreur chargement commentaires', err)
      });
  }

  postComment(): void {
    if (!this.newComment.trim()) return;

    this.commentService.postComment(this.articleId, this.newComment)
      .subscribe({
        next: (newCom) => {
          this.comments.unshift(newCom);
          this.newComment = '';
        },
        error: (err) => console.error('Erreur ajout commentaire', err)
      });
  }
}
