import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {MatDialog} from "@angular/material/dialog";
import {LoginService} from "../services/login.service";
import {UrlCacheService} from "../services/url-cache.service";

@Component({
  selector: 'login-page',
  styleUrls: ['./login.component.scss'],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  form: FormGroup;
  authenticationError: boolean;

  constructor(public loginService: LoginService,
              public router: Router,
              public formBuilder: FormBuilder,
              public dialog: MatDialog,
              public urlCacheService: UrlCacheService) {
    this.form = this.formBuilder.group({
      username: [],
      password: []
    });
  }

  login(): void {
    if (this.form.valid) {
      this.loginService.login(this.form.getRawValue()).then(() => {
        this.authenticationError = false;
        this.successAction();
      }).catch(() => {
        this.authenticationError = true;
      });
    } else {
      this.authenticationError = true;
    }
  }

  successAction(): void {
    try {
      this.router.navigate([this.urlCacheService.cachedUrl]);
    } catch (error) {
      console.error('Navigation failed by cached url');
      this.router.navigate(['']);
    }
  }
}
