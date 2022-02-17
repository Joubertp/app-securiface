import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../services/auth.service";
import { User } from '../../interfaces/user';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  user:User;
  constructor(private authService: AuthService) {
   }

  ngOnInit(): void {
    this.getUser()
  }

  getUser() {
    this.user = this.authService.getCurrentUser();
    console.log('j ai le user ', this.user.id);
    return this.user;
  }

}
