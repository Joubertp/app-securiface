import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { CrudUserService } from 'src/app/services/crud-user.service';
import { User } from 'src/app/interfaces/user';
import { Router } from '@angular/router';
import axios from 'axios';

@Component({
  selector: 'app-profile-form',
  templateUrl: './profile-form.component.html',
  styleUrls: ['./profile-form.component.css']
})
export class ProfileFormComponent implements OnInit {

  public profileForm: FormGroup;
  public isSubmitted = false;
  private user:User;

  constructor(
    private authService: AuthService,
    private crudService: CrudUserService,
    private router: Router,
    private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser()
    this.profileForm = this.formBuilder.group({
      "id": [this.user.id],
      "firstname": [this.user.firstname, Validators.compose([Validators.required, Validators.minLength(3)])],
      "lastname": [this.user.lastname, Validators.compose([Validators.required, Validators.minLength(3)])],
      "username": [this.user.username, Validators.compose([Validators.required, Validators.minLength(3)])],
      "password": [this.user.password, Validators.compose([Validators.required, Validators.minLength(5)])],
      "email": [this.user.email, Validators.compose([Validators.required, Validators.email])],
    })
  }

  get formControls() { return this.profileForm.controls; }
  onUpdateUser():void {
    console.log(this.profileForm.value);
    this.isSubmitted = true;
    if(this.profileForm.invalid){
      return
    }
    axios.put(
      'http://localhost:8080/users/update',
      this.profileForm.value
      )
      .then(response => {
      console.log('recup', response.data);
      this.user = response.data;
      this.crudService.updateUser(this.user);
      console.log('id', this.user.id)
      this.router.navigateByUrl(`/dashboard/${this.user.id}/camera`);
    });
  }
  /*
  updateProfile(user:User):void {
    this.crudService.updateUser(user).subscribe(user => this.users = users)
  }
  */
}
