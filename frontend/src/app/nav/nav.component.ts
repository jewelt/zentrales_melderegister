import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {map, shareReplay} from 'rxjs/operators';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../services/auth.service';
import {Router} from '@angular/router';
import {UserAccountApiControllerService} from '../clients/melderegister';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnInit {

  loadingLogin = false;
  errorLogin = '';
  isLoginErrorVisible = false;

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );
  authenticated$: Observable<boolean> = this.authenticationService.authenticated$;

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required]),
    password: new FormControl('', Validators.required),
    stayLoggedIn: new FormControl(false),
  });

  isUser = false;
  isAdmin = false;

  constructor(
    private breakpointObserver: BreakpointObserver,
    private userAccountApiControllerService: UserAccountApiControllerService,
    private authenticationService: AuthService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.authenticated$.subscribe(isAuthenticated => {
      if (isAuthenticated) {
        this.userAccountApiControllerService.getCurrentUserUsingGET().subscribe((user) => {
          this.isUser = user.authorities.includes('USER');
          this.isAdmin = user.authorities.includes('ADMIN');
        });
      }
    });
  }

  login() {
    this.loadingLogin = true;
    this.isLoginErrorVisible = false;
    const email = this.loginForm.get('email').value;
    const password = this.loginForm.get('password').value;
    const stayLoggedIn = this.loginForm.get('stayLoggedIn').value;
    console.log('Login!');
    this.authenticationService.login({
      email,
      password,
      rememberMe: stayLoggedIn
    }).subscribe(() => {
      console.log('Navigation to dashboard');
      this.loadingLogin = false;
      this.isLoginErrorVisible = false;
      this.router.navigate(['/dashboard']);
    }, error => {
      console.error(error);
      this.loadingLogin = false;
      this.errorLogin = error.error.message;
      if (!this.errorLogin) {
        this.errorLogin = 'Verbindungsfehler';
      }
      this.isLoginErrorVisible = true;
    });

  }

  logout() {
    this.authenticationService.logout();
  }

}
