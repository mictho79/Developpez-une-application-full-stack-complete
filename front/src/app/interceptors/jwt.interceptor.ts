import { HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {

  // Intercepte toutes les requêtes HTTP sortantes
  intercept(request: HttpRequest<any>, next: HttpHandler) {
    const token = localStorage.getItem('token');

    // Si un token JWT est présent, on l'ajoute dans l'en-tête Authorization
    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }

    // On continue le traitement normal de la requête
    return next.handle(request);
  }
}
