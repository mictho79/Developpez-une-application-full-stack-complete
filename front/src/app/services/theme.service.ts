import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Theme } from 'src/app/models/Theme.model';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private baseUrl = 'http://localhost:8080/api/themes';
  private subscriptionUrl = 'http://localhost:8080/api/subscriptions';

  constructor(private http: HttpClient) {}

  // Récupère tous les thèmes disponibles
  getAllThemes(): Observable<Theme[]> {
    return this.http.get<Theme[]>(this.baseUrl);
  }

  // Récupère les thèmes auxquels l’utilisateur est abonné
  getUserSubscriptions(): Observable<Theme[]> {
    return this.http.get<Theme[]>(this.subscriptionUrl);
  }

  // Abonnement à un thème
  subscribe(themeId: number): Observable<any> {
    return this.http.post(this.subscriptionUrl, { themeId });
  }

  // Désabonnement d’un thème
  unsubscribe(themeId: number): Observable<any> {
    return this.http.delete(`${this.subscriptionUrl}/${themeId}`);
  }
}
