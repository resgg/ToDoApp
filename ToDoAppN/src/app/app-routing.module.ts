import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SkyBackgroundComponent } from './components/sky-background/sky-background.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import { CreateUserComponent } from './components/create-user/create-user.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: CreateUserComponent},
  { path: 'dashboard', component: SkyBackgroundComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
