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

  constructor(
    private authService: AuthService,
    private router: Router,
    private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      // "firstname": ["", Validators.compose([Validators.required, Validators.minLength(3)])],
      // "lastname": ["", Validators.compose([Validators.required, Validators.minLength(3)])],
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
      'http://127.0.0.1:9001/app-securiface/user/users/login',
      {
        headers: {
          'Content-Type': 'application/json;charset=UTF-8',
          'Access-Control-Allow-Origin': '*',
        }
      },
      this.loginForm.value
      )
      .then(response => {
      console.log(response);
    });
    this.authService.logIn(this.loginForm.value);
    this.router.navigateByUrl('/dashboard/1');
  }


}
