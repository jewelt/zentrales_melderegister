import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {
  CityControllerService,
  LoginRequest, PatientControllerService,
  TestControllerService,
  TestResultControllerService,
  UserJwtApiControllerService
} from '../clients/melderegister';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient,
              private userJwtApiControllerService: UserJwtApiControllerService,
              private cityControllerService: CityControllerService,
              private testControllerService: TestControllerService,
              private patientControllerService: PatientControllerService,
              private testResultControllerService: TestResultControllerService) {
    this.setApiKeys({});
  }

  authenticated$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  login(loginRequest: LoginRequest): Observable<void> {
    this.userJwtApiControllerService.configuration.apiKeys = {};
    return this.userJwtApiControllerService.authorizeUsingPOST(loginRequest).pipe(map(response => {
      this.authenticated$.next(true);
      this.setApiKeys({
        'X-Melderegister-Authorization': 'Bearer ' + (response as any).id_token
      });
    }));
  }

  private setApiKeys(keys: any) {
    this.cityControllerService.configuration.apiKeys = keys;
    this.testControllerService.configuration.apiKeys = keys;
    this.testResultControllerService.configuration.apiKeys = keys;
    this.patientControllerService.configuration.apiKeys = keys;
  }

}
