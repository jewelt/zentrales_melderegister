import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import {UserGuard} from './guards/user.guard';
import {ErfassungComponent} from './components/erfassung/erfassung.component';
import {TestListComponent} from './components/test-list/test-list.component';
import {TestListAllComponent} from './components/test-list-all/test-list-all.component';
import {AdminGuard} from './guards/admin.guard';
import {AnyGuard} from './guards/any.guard';


const routes: Routes = [
  {path: 'erfassung', component: ErfassungComponent, canActivate: [UserGuard]},
  {path: 'dashboard', component: DashboardComponent, canActivate: [AnyGuard]},
  {path: 'test-list', component: TestListComponent, canActivate: [UserGuard]},
  {path: 'test-list-all', component: TestListAllComponent, canActivate: [AdminGuard]},
  {path: '', redirectTo: 'dashboard', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
