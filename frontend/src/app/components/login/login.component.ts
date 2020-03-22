import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loadingLogin = false;
  errorLogin = '';
  isLoginErrorVisible = false;

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required]),
    password: new FormControl('', Validators.required),
    stayLoggedIn: new FormControl(false),
  });

  constructor(private authenticationService: AuthService,
              private router: Router) {
  }

  ngOnInit() {
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

}
