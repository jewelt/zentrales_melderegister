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

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );
  authenticated$: Observable<boolean> = this.authenticationService.authenticated$;


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

  logout() {
    this.authenticationService.logout();
  }

}
