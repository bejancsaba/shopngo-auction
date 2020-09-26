import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { LandingComponent } from './shopngo-module/components/landing.component';
import { EntryComponent } from './entry.component';
import { LoginComponent } from "./shopngo-module/components/login.component";
import { LoginService } from './shopngo-module/services/login.service';
import { MaterialModule } from './material/material.module';
import { User } from "./shopngo-module/services/user.service";
import { AclService } from "./shopngo-module/services/acl.service";
import { AuthJwtServerProvider } from "./shopngo-module/services/auth-jwt.service";
import {PageAccessGuard} from "./shopngo-module/services/page-access-guard.service";
import {UrlCacheService} from "./shopngo-module/services/url-cache.service";
import {JwtInterceptor} from "./shopngo-module/services/jwt-interceptor";

@NgModule({
  declarations: [
    EntryComponent,
    LoginComponent,
    LandingComponent
  ],
  exports: [
    LandingComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    AppRoutingModule
  ],
  providers: [
    AclService,
    AuthJwtServerProvider,
    LoginService,
    PageAccessGuard,
    UrlCacheService,
    User,
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
  ],
  bootstrap: [EntryComponent],
  entryComponents: [
    EntryComponent
  ]
})
export class AppModule { }
