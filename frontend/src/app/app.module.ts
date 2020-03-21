import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavComponent} from './nav/nav.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatIconModule} from "@angular/material/icon";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatListModule} from "@angular/material/list";
import {MatButtonModule} from "@angular/material/button";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatCardModule} from "@angular/material/card";
import {ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatInputModule} from "@angular/material/input";
import {HttpClientModule} from "@angular/common/http";
import {DashboardComponent} from './dashboard/dashboard.component';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatMenuModule} from '@angular/material/menu';
import {LayoutModule} from '@angular/cdk/layout';
import {TestComponent} from './test/test.component';
import { ErfassungComponent } from './erfassung/erfassung.component';
import {MatOptionModule} from "@angular/material/core";
import { MatSelectModule } from "@angular/material/select";
import { MatDatepickerModule, MatNativeDateModule } from '@angular/material';
import { MatRadioModule } from '@angular/material/radio';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    DashboardComponent,
    TestComponent,
    ErfassungComponent
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
    ReactiveFormsModule,
    MatFormFieldModule,
    MatCheckboxModule,
    MatInputModule,
    HttpClientModule,
    MatGridListModule,
    MatMenuModule,
    LayoutModule,
    MatOptionModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatRadioModule,
    FormsModule,
    MatSelectModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
