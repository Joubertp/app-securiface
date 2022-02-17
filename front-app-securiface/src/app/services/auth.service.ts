import { Injectable } from '@angular/core';
import { User } from '../interfaces/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  user:User ;

  constructor() { }

  public getCurrentUser(){
    console.log('test', this.user)
    return this.user;
  }

  public logIn(userInfo: User) {
    this.user = userInfo;
    localStorage.setItem('ACCESS_TOKEN', 'access_token');
  }
  public isConnected(){
    return localStorage.getItem('ACCESS_TOKEN') !== null;
  }
  public logOut(){
    localStorage.removeItem('ACCESS_TOKEN');
  }
}
