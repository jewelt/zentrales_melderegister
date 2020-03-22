import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable, of} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {AuthService} from '../services/auth.service';
import {UserAccountApiControllerService} from '../clients/melderegister';

@Injectable({
  providedIn: 'root'
})
export class AnyGuard implements CanActivate {

  constructor(private authService: AuthService,
              private userAccountApiControllerService: UserAccountApiControllerService) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    console.log('Checking @AnyGuard');
    return this.userAccountApiControllerService.getCurrentUserUsingGET().pipe(map((user) => {
      return user.authorities.includes('USER') || user.authorities.includes('ADMIN');
    }), catchError(error => {
      console.log('User is not allowed to access admin url ' + next.url);
      return of(false);
    }));
  }

}
