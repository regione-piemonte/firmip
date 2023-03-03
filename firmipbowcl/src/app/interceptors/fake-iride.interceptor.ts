/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import {Injectable, isDevMode} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";

@Injectable()
export class FakeIrideInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (!environment.production) {
      console.log('Interceptor fake iride');
      let newHttpParams = request.params;
      newHttpParams = newHttpParams.append( 'Shib-Iride-IdentitaDigitale', environment.identitaIrideParameter);
      const authReq = request.clone({params: newHttpParams});
      return next.handle(authReq);
    } else {
      return next.handle(request);
    }
  }
}
