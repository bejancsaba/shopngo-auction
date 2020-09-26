import { NgModule } from '@angular/core';
import { RouterModule, Routes} from '@angular/router';
import { LandingComponent } from './shopngo-module/components/landing.component';
import { LoginComponent } from "./shopngo-module/components/login.component";
import { PageAccessGuard } from "./shopngo-module/services/page-access-guard.service";

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '',
    component: LandingComponent,
    canActivate: [PageAccessGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true, enableTracing: false})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
