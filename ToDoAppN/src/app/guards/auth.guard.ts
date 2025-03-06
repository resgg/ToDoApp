import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    const token = sessionStorage.getItem('token'); 
    if (token) {
      console.log('AuthGuard: User authenticated.');
      return true;
    } else {
      console.log('AuthGuard: User not authenticated. Redirecting to login.');
      this.router.navigate(['/login']); 
      return false; 
    }
  }
}