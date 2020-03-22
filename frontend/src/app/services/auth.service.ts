import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {
  CityControllerService, CountryControllerService,
  LoginRequest,
  PatientControllerService, StateControllerService,
  StatisticsControllerService,
  TestControllerService,
  TestResultControllerService,
  UserAccountApiControllerService,
  UserJwtApiControllerService
} from '../clients/melderegister';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient,
              private userJwtApiControllerService: UserJwtApiControllerService,
              private cityControllerService: CityControllerService,
              private stateControllerService: StateControllerService,
              private countryControllerService: CountryControllerService,
              private testControllerService: TestControllerService,
              private patientControllerService: PatientControllerService,
              private userAccountApiControllerService: UserAccountApiControllerService,
              private statisticsControllerService: StatisticsControllerService,
              private testResultControllerService: TestResultControllerService) {

    const token = localStorage.getItem('token');
    if (token) {
      const tokenData = this.getAuthDetailsFromToken(token);
      if (new Date().getTime() < tokenData.exp * 1000) {
        console.log('Key still valid');
        this.setApiKeys({
          'X-Melderegister-Authorization': 'Bearer ' + token
        });
        this.authenticated$.next(true);
      }
    } else {
      console.log('Token expired. Deleting');
      localStorage.removeItem('token');
      this.setApiKeys({});
    }
  }

  authenticated$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  login(loginRequest: LoginRequest): Observable<void> {
    this.userJwtApiControllerService.configuration.apiKeys = {};
    return this.userJwtApiControllerService.authorizeUsingPOST(loginRequest).pipe(map(response => {
      const token = (response as any).id_token;
      localStorage.setItem('token', token);
      this.setApiKeys({
        'X-Melderegister-Authorization': 'Bearer ' + token
      });
      this.authenticated$.next(true);
    }));
  }

  private getAuthDetailsFromToken(jwt: string): AuthDetails {
    const jwtData = jwt.split('.')[1];
    return JSON.parse(window.atob(jwtData));
  }

  logout() {
    localStorage.removeItem('token');
    this.setApiKeys({});
    this.authenticated$.next(false);
  }

  private setApiKeys(keys: any) {
    this.cityControllerService.configuration.apiKeys = keys;
    this.testControllerService.configuration.apiKeys = keys;
    this.testResultControllerService.configuration.apiKeys = keys;
    this.patientControllerService.configuration.apiKeys = keys;
    this.statisticsControllerService.configuration.apiKeys = keys;
    this.userAccountApiControllerService.configuration.apiKeys = keys;
    this.stateControllerService.configuration.apiKeys = keys;
    this.countryControllerService.configuration.apiKeys = keys;
  }

}

export interface AuthDetails {
  sub: string;
  auth: string;
  exp: number;
}
