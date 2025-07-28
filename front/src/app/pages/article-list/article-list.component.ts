import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Article } from 'src/app/models/Article.model';
import { ArticleService } from 'src/app/services/article.service';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.scss']
})
export class ArticleListComponent implements OnInit {
  articles: Article[] = [];
  sortOrder: 'asc' | 'desc' = 'desc';

  constructor(
    private articleService: ArticleService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.articleService.getSubscribedArticles()
      .subscribe({
        next: (res) => {
          this.articles = res;
          this.sortArticles();
        },
        error: (err) => console.error('Erreur chargement articles', err)
      });
  }

  sortArticles(): void {
    const getTime = (date: string) => new Date(date).getTime();
    this.articles.sort((a, b) =>
      this.sortOrder === 'asc'
        ? getTime(a.createdAt) - getTime(b.createdAt)
        : getTime(b.createdAt) - getTime(a.createdAt)
    );
  }

  goToArticle(id: number): void {
    this.router.navigate(['/articles', id]);
  }

  toggleSortOrder(): void {
    this.sortOrder = this.sortOrder === 'desc' ? 'asc' : 'desc';
    this.sortArticles();
  }
}
