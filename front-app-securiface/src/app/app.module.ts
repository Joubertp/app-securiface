import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { AuthComponent } from './modules/auth/auth.component';
import { FourOhFourComponent } from './modules/four-oh-four/four-oh-four.component';
import { DashboardComponent } from './modules/dashboard/dashboard.component';
import { AuthGuard } from './guards/auth.guard';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { HeaderComponent } from './shared/header/header.component';
import { UsersComponent } from './modules/users/users.component';
import { HttpClientModule } from '@angular/common/http';
import { ProfileFormComponent } from './modules/profile-form/profile-form.component';
import { BodyComponent } from './modules/body/body.component';

const appRoutes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'auth' },
  { path: 'auth', component: AuthComponent },
  {
    path: 'api/:id',
    canActivate: [AuthGuard],
    component: BodyComponent,
    children: [
      {path: '', pathMatch: 'full', redirectTo: 'dashboard'},
      {path: 'dashboard', component: DashboardComponent},
      {path: 'users', component: UsersComponent},
      {path: 'profile', component: ProfileFormComponent},
    ]
  },
  { path: 'not-found', component: FourOhFourComponent },
  { path: '**', redirectTo: 'not-found' }
]

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    FourOhFourComponent,
    DashboardComponent,
    NavbarComponent,
    HeaderComponent,
    UsersComponent,
    ProfileFormComponent,
    BodyComponent
  ],
  imports: [
    BrowserModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: environment.production,
      // Register the ServiceWorker as soon as the app is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    }),
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
