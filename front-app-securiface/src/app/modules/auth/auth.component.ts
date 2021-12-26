import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/interfaces/user';
import { AuthService } from '../../services/auth.service';

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
      "password": ["", Validators.compose([Validators.required, Validators.minLength(6)])],
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
    this.authService.logIn(this.loginForm.value);
    this.router.navigateByUrl('/dashboard');
  }


}
