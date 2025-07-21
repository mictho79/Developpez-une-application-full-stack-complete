import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Theme } from 'src/app/models/Theme.model';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private baseUrl = 'http://localhost:8080/api/themes';
  private subscriptionUrl = 'http://localhost:8080/api/subscriptions';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token') || '';
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  // Renvoie tous les thèmes disponibles
  getAllThemes(): Observable<Theme[]> {
    return this.http.get<Theme[]>(this.baseUrl, { headers: this.getAuthHeaders() });
  }

  // Renvoie les thèmes auxquels l’utilisateur est abonné
  getUserSubscriptions(): Observable<Theme[]> {
    return this.http.get<Theme[]>(this.subscriptionUrl, { headers: this.getAuthHeaders() });
  }

  // Abonnement à un thème
  subscribe(themeId: number): Observable<any> {
    return this.http.post(this.subscriptionUrl, { themeId }, { headers: this.getAuthHeaders() });
  }

  // Désabonnement d’un thème
  unsubscribe(themeId: number): Observable<any> {
    return this.http.delete(`${this.subscriptionUrl}/${themeId}`, { headers: this.getAuthHeaders() });
  }
}
