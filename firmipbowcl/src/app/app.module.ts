/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/commons/header/header.component';
import { BreadcrumbsComponent } from './components/commons/breadcrumbs/breadcrumbs.component';
import { FooterComponent } from './components/commons/footer/footer.component';
import { NotificationComponent } from './components/commons/notification/notification.component';
import { AppRoutingModule } from './app-routing.module';
import { AuthenticationComponent } from './components/auth/authentication/authentication.component';
import {IniziativeModule} from "./modules/iniziative/iniziative.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {IniziativeHttpService} from "./services/iniziative-http.service";
import {FakeIrideInterceptor} from "./interceptors/fake-iride.interceptor";
import {MsgNotificationService} from "./services/msg-notification.service";
import {NgHttpLoaderModule} from "ng-http-loader";
import {LoggerModule, NgxLoggerLevel} from "ngx-logger";
import {FirmeModule} from "./modules/firme/firme.module";
import {WDialogComponent} from "./components/commons/w-dialog/w-dialog.component";
import { ErrorComponent } from './components/commons/error/error.component';
import {AuthService} from "./services/auth.service";
import {NavigationEnd, NavigationStart, Router} from "@angular/router";
import {IniziativeRoutingModule} from "./modules/iniziative/iniziative-routing.module";
import {XsrfInterceptor} from "./interceptors/httpXSRFInterceptor";

@NgModule({
    declarations: [
        AppComponent,
        HeaderComponent,
        FooterComponent,
        NotificationComponent,
        BreadcrumbsComponent,
        AuthenticationComponent,
        WDialogComponent,
        ErrorComponent
    ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    IniziativeModule,
    FirmeModule,
    HttpClientModule,
    LoggerModule.forRoot({
      level: NgxLoggerLevel.DEBUG
    }),
    NgHttpLoaderModule.forRoot(),

  ],
    providers: [IniziativeHttpService,
        {provide: HTTP_INTERCEPTORS, useClass: FakeIrideInterceptor, multi: true},
        { provide: HTTP_INTERCEPTORS, useClass: XsrfInterceptor, multi: true },
      MsgNotificationService, AuthService
    ],
  exports: [
  ],
    bootstrap: [AppComponent]
})
export class AppModule {

  constructor(private readonly router: Router) {
    router.events.subscribe(
      // console.log

    )

    this.router.events.subscribe( event => {
      if (event instanceof NavigationStart) {
        console.log(event.url)
      }
    });


  }


}
