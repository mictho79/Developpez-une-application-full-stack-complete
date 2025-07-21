// user.service.ts

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface UserDTO {
  username: string;
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  getCurrentUser(): Observable<UserDTO> {
    return this.http.get<UserDTO>(`${this.apiUrl}/me`);
  }

  // on garde le même nom mais on accepte aussi le password en option
  updateUser(data: { username: string; email: string; password?: string }): Observable<UserDTO> {
    return this.http.patch<UserDTO>(`${this.apiUrl}/me`, data);
  }
}
