import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {CityControllerService, LoginRequest, UserJwtApiControllerService} from '../clients/melderegister';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient,
              private userJwtApiControllerService: UserJwtApiControllerService,
              private cityControllerService: CityControllerService) {
    this.setApiKeys({});
  }

  authenticated$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  login(loginRequest: LoginRequest): Observable<void> {
    this.userJwtApiControllerService.configuration.apiKeys = {};
    return this.userJwtApiControllerService.authorizeUsingPOST(loginRequest).pipe(map(response => {
      this.authenticated$.next(true);
      this.setApiKeys({
        'X-Melderegister-Authorization': 'Bearer ' + response.idToken
      });
    }));
  }

  private setApiKeys(keys: any) {
    this.cityControllerService.configuration.apiKeys = keys;
  }

}
