import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/interfaces/user';
import { AuthService } from '../../services/auth.service';
import axios from 'axios'

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

  loginForm: FormGroup;
  isSubmitted = false;
  user: User;

  constructor(
    private authService: AuthService,
    private router: Router,
    private formBuilder: FormBuilder
  ) { }


  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      "password": ["", Validators.compose([Validators.required, Validators.minLength(5)])],
      "email": ["", Validators.compose([Validators.required, Validators.email])],

    });
  }
  get formControls() { return this.loginForm.controls; }
  onLogIn(){
    console.log(this.loginForm.value);
    this.isSubmitted = true;
    if(this.loginForm.invalid){
      return
    }
    axios.post(
      'http://localhost:8080/users/login',
      this.loginForm.value
      )
      .then(response => {
      console.log('recup', response.data);
      this.user = response.data;
      this.authService.logIn(this.user);
      console.log('id', this.user.id)
      this.router.navigateByUrl(`/api/${this.user.id}/dashboard`);
    });
  }


}
