/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {BehaviorSubject, catchError, Observable, of, Subject, throwError} from "rxjs";
import {User} from "../model/firmipbo-data-fe";
import {Router} from "@angular/router";
import {environment} from "../../environments/environment";
/*
 Servizi per gestire autenticazione
 */



@Injectable()
export class AuthService {

  private readonly baseURL = environment.APIUrl

  private authentication$:  BehaviorSubject<User> = new BehaviorSubject<User>({cognome: '',nome: '', codFisc: ''}) ;

  constructor(private httpClient: HttpClient,
              private router: Router) { }

  get authObs() {
    return this.authentication$.asObservable();
  }

  get
    authSub$() {
     return this.authentication$;
  }

  public loginUser(): Observable<User> {
    return this.httpClient.get<User>(`${this.baseURL}currentUser`)
  }

  public logoutUser(): Observable<any> {
    return this.httpClient.get<User>(`${this.baseURL}localLogout`)
  }





}
