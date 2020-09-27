import { Component, OnInit } from '@angular/core';
import { User } from "./shopngo-module/services/user.service";
import { LoginService } from "./shopngo-module/services/login.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-entry',
  template: `
        <button mat-raised-button style="position:absolute;right: 30px;top: 20px;" *ngIf=user.isAuthenticated() (click)=logout()>Logout {{sub}}</button>
        <img style="position:absolute;left: 10px;top: 5px;width: 60px;height: 60px;" src="public/assets/img/ShopNGo.png">
        <router-outlet></router-outlet>`
})
export class EntryComponent implements OnInit {

  constructor(public user: User, private loginService: LoginService, private router: Router) {}

  ngOnInit(): void {
  }

  logout() {
    this.loginService.logout();
    this.router.navigate(["/login"]);
  }

  get sub() {
    return this.user.identity()?.sub;
  }
}
