import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';
import {BehaviorSubject, Observable, of, Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {LoginRequest} from '../model/login-request';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  authenticated$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  login(loginRequest: LoginRequest): Observable<void> {
    console.log("Login request");
    this.authenticated$.next(true);
    return of();
    // return this.http.post('/api/login', loginRequest).pipe(
    //   map((response: any) => {
    //     this.authenticated$.next(true);
    //   })
    // );
  }

}
