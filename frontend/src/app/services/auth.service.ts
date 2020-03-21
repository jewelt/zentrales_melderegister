import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {
  CityControllerService,
  LoginRequest, PatientControllerService, StatisticsControllerService,
  TestControllerService,
  TestResultControllerService, UserAccountApiControllerService,
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
              private userAccountApiControllerService: UserAccountApiControllerService,
              private statisticsControllerService: StatisticsControllerService,
              private testResultControllerService: TestResultControllerService) {
    this.setApiKeys({});
  }

  authenticated$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  login(loginRequest: LoginRequest): Observable<void> {
    this.userJwtApiControllerService.configuration.apiKeys = {};
    return this.userJwtApiControllerService.authorizeUsingPOST(loginRequest).pipe(map(response => {
      this.setApiKeys({
        'X-Melderegister-Authorization': 'Bearer ' + (response as any).id_token
      });
      this.authenticated$.next(true);
    }));
  }

  private setApiKeys(keys: any) {
    this.cityControllerService.configuration.apiKeys = keys;
    this.testControllerService.configuration.apiKeys = keys;
    this.testResultControllerService.configuration.apiKeys = keys;
    this.patientControllerService.configuration.apiKeys = keys;
    this.statisticsControllerService.configuration.apiKeys = keys;
    this.userAccountApiControllerService.configuration.apiKeys = keys;
  }

}
