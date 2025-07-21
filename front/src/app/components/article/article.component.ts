import { Component, Input } from '@angular/core';
import { Article } from 'src/app/models/Article.model';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent {
  @Input() article!: Article;
}
