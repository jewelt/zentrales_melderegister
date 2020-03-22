import {BrowserModule} from '@angular/platform-browser';
import {LOCALE_ID, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavComponent} from './components/nav/nav.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatIconModule} from '@angular/material/icon';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatListModule} from '@angular/material/list';
import {MatButtonModule} from '@angular/material/button';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatCardModule} from '@angular/material/card';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatInputModule} from '@angular/material/input';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatMenuModule} from '@angular/material/menu';
import {LayoutModule} from '@angular/cdk/layout';
import {MatDatepickerModule, MatNativeDateModule} from '@angular/material';
import {MatRadioModule} from '@angular/material/radio';
import {ErfassungComponent} from './components/erfassung/erfassung.component';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE, MatOptionModule} from '@angular/material/core';
import {MatSelectModule} from '@angular/material/select';
import {TestListComponent} from './components/test-list/test-list.component';
import {SimplemattableModule} from 'simplemattable';
import {ApiModule, BASE_PATH} from './clients/melderegister';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatMomentDateModule, MomentDateAdapter} from '@angular/material-moment-adapter';
import {TimepickerComponent} from './components/timepicker/timepicker.component';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {TestListAllComponent} from './components/test-list-all/test-list-all.component';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatTooltipModule} from '@angular/material/tooltip';
import {LoginComponent} from './components/login/login.component';
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {LoadingInterceptor} from "./interceptor/loading-interceptor";

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    DashboardComponent,
    ErfassungComponent,
    TestListComponent,
    TimepickerComponent,
    TestListAllComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatMomentDateModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatCheckboxModule,
    MatInputModule,
    HttpClientModule,
    MatGridListModule,
    MatMenuModule,
    LayoutModule,
    MatOptionModule,
    MatSelectModule,
    NgxChartsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatRadioModule,
    FormsModule,
    MatSelectModule,
    SimplemattableModule,
    MatSnackBarModule,
    ApiModule,
    MatAutocompleteModule,
    MatTooltipModule,
    MatProgressBarModule
  ],
  providers: [
    {
      provide: BASE_PATH,
      useValue: 'http://localhost:4200/api'
    },
    {
      provide: LOCALE_ID,
      useValue: 'de'
    },
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {
      provide: MAT_DATE_FORMATS,
      useValue: {
        parse: {
          dateInput: ['DD.MM.YYYY', 'DDMMYYYY', 'd.m.yy', 'dmyy'],
        },
        display: {
          dateInput: 'DD.MM.YYYY',
          monthYearLabel: 'MMM YYYY',
          dateA11yLabel: 'LL',
          monthYearA11yLabel: 'MMMM YYYY',
        },
      },
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoadingInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
