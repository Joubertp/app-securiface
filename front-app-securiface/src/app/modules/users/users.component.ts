import { Component, OnInit } from '@angular/core';
import { CrudUserService } from 'src/app/services/crud-user.service';
import { User } from 'src/app/interfaces/user';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})

export class UsersComponent implements OnInit {

  users: User[] = [];

  constructor(private crudService: CrudUserService) { }

  ngOnInit(): void {
    this.getAllUsers()
  }

  getAllUsers () {
    this.crudService.showUsers().subscribe(users => this.users = users)
  }
}
