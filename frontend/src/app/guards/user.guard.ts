import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable, of} from 'rxjs';
import {AuthService} from '../services/auth.service';
import {UserAccountApiControllerService} from '../clients/melderegister';
import {catchError, map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserGuard implements CanActivate {

  constructor(private authService: AuthService,
              private userAccountApiControllerService: UserAccountApiControllerService) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.userAccountApiControllerService.getCurrentUserUsingGET().pipe(map((user) => {
      return user.authorities.includes('USER');
    }), catchError(error => {
      console.log('User is not allowed to access user url ' + next.url);
      return of(false);
    }));
  }

}
