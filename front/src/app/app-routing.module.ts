import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';

import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ArticleListComponent } from './pages/article-list/article-list.component';
import { AuthGuard } from './guards/auth.guard';
import { ArticleCreateComponent } from './pages/article-create/article-create.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { ArticleDetailComponent } from './pages/article-detail/article-detail.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent },               // page d’accueil

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent } ,  // page des sessions     
  { path: 'articles', component: ArticleListComponent, canActivate: [AuthGuard] },
  { path: 'articles/new', component: ArticleCreateComponent, canActivate: [AuthGuard] },
  { path: 'themes', component: ThemesComponent, canActivate: [AuthGuard]  },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: 'articles/:id', component: ArticleDetailComponent, canActivate: [AuthGuard] }
     
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  
})
export class AppRoutingModule {}
