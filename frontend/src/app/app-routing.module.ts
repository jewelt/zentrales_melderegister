import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from './dashboard/dashboard.component';
import {UserGuard} from './guards/user.guard';
import {TestComponent} from './test/test.component';


const routes: Routes = [
  {path: 'test', component: TestComponent, canActivate: [UserGuard]},
  {path: 'dashboard', component: DashboardComponent, canActivate: [UserGuard]},
  {path: '', redirectTo: 'dashboard', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
