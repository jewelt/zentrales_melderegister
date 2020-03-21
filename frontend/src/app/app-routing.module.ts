import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from './dashboard/dashboard.component';
import {UserGuard} from './guards/user.guard';
import {TestComponent} from './test/test.component';
import {ErfassungComponent} from './erfassung/erfassung.component';
import {TestListComponent} from './test-list/test-list.component';


const routes: Routes = [
  {path: 'test', component: TestComponent, canActivate: [UserGuard]},
  {path: 'erfassung', component: ErfassungComponent, canActivate: [UserGuard]},
  {path: 'dashboard', component: DashboardComponent, canActivate: [UserGuard]},
  {path: 'test-list', component: TestListComponent, canActivate: [UserGuard]},
  {path: '', redirectTo: 'dashboard', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
