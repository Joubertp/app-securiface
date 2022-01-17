import { Injectable } from '@angular/core';
import { User } from '../interfaces/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }
  public logIn(userInfo: User) {
    localStorage.setItem('ACCESS_TOKEN', 'access_token');
  }
  public isConnected(){
    return localStorage.getItem('ACCESS_TOKEN') !== null;
  }
  public logOut(){
    localStorage.removeItem('ACCESS_TOKEN');
  }
}
