import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { MatButtonModule } from '@angular/material/button';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ArticleListComponent } from './pages/article-list/article-list.component';

import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { ArticleCreateComponent } from './pages/article-create/article-create.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { SubscriptionButtonComponent } from './components/subscription-button/subscription-button.component';
import { ArticleComponent } from './components/article/article.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { ThemeCardComponent } from './components/theme-card/theme-card.component';
import { ArticleDetailComponent } from './pages/article-detail/article-detail.component'; // adapte le chemin si nécessaire
import { FormsModule } from '@angular/forms';
import { BackButtonComponent } from './components/back-button/back-button.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ErrorInterceptor } from './interceptors/error.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    ArticleListComponent,
    ArticleCreateComponent,
    ThemesComponent,
    SubscriptionButtonComponent,
    ArticleComponent,
    ProfileComponent,
    ThemeCardComponent,
    ArticleDetailComponent,
    BackButtonComponent,
    NavbarComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    },
  {
    provide: HTTP_INTERCEPTORS,
    useClass: ErrorInterceptor,
    multi: true
  }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
