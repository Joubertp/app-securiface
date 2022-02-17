import { ErrorHandler, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { User } from '../interfaces/user';
import { Observable } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CrudUserService {

  private apiUrl: string = 'http://localhost:8080';
  private headers = new HttpHeaders().set('Content-Type', 'application/json');
  private messageService = []

  constructor(private http: HttpClient) { }

  // Create
  createUser(user: User):Observable<any> {
    let API_URL = `${this.apiUrl}/users/create`;
    return this.http.post(API_URL, user)
  }
  // Read
  showUsers():Observable<any> {
    console.log('je suis dans showUsers')
    return this.http.get<User[]>(`${this.apiUrl}/users/`)
  }

  // Update
  updateUser(user: User):Observable<any> {
    let API_URL = `${this.apiUrl}/users/${user.id}`;
    return this.http.put(API_URL, user, { headers: this.headers })
  }
  // Delete
  deleteUser(user: User):Observable<any> {
    var API_URL = `${this.apiUrl}/users/delete/${user.id}`;
    return this.http.delete(API_URL)
  }
}
