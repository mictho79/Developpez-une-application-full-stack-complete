import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpErrorResponse
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private router: Router) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Erreur interceptée :', error);

        switch (error.status) {
          case 401:
            this.router.navigate(['/login']);
            break;
          case 403:
            alert('Accès interdit.');
            this.router.navigate(['/articles']);
            break;
          case 404:
            alert('Ressource introuvable.');
            break;
          case 500:
            alert('Erreur serveur, réessayez plus tard.');
            break;
          default:
            alert('Une erreur est survenue.');
        }

        return throwError(() => error);
      })
    );
  }
}
