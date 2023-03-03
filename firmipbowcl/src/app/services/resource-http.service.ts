/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {catchError, map, Observable, throwError} from "rxjs";
import {environment} from "../../environments/environment";
import {TipoIniziativa} from "../model/firmipbo-data-model";
import {MsgNotificationService} from "./msg-notification.service";

@Injectable({
  providedIn: 'root'
})

// Classe abstract, non puó essere istanziata

export abstract class ResourceHttpService<T> {

  protected readonly baseURL = environment.APIUrl;
  private readonly APIUrl = this.baseURL + this.getResourceUrl();

  constructor(protected httpClient: HttpClient
              ) {
  }

  // Front-End Model => Server Model
  toServerModel(entity: T): any {
    return entity;
  }

  // Server Model => Front-End Model
  fromServerModel(json: any): T {
    return json;
  }


  // metodo abstract, classe che deriva deve istanziare questo metodo per avere URL alla risorsa
  abstract getResourceUrl(): string;

  getPaginatedList(page: number, count: number): Observable<T[]> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('count', count.toString());

    return this.httpClient.get<T[]>(`/${this.APIUrl}}?${params.toString()}`)
      .pipe(
        map((list) => list.map((item)=> this.fromServerModel(item))),
        catchError(this.handleError)
      );
  }

  getList(): Observable<T[]> {

    return this.httpClient.get<T[]>(`${this.APIUrl}`)
      .pipe(
        map((list) => list.map((item)=> this.fromServerModel(item))),
        catchError(this.handleError)
      );
  }

  getListTipi(): Observable<T[]> {

    return this.httpClient.get<TipoIniziativa[]>(environment.APIUrl + 'tipi-iniziativa/')
      .pipe(
        map((list) => list.map((item)=> this.fromServerModel(item))),
        catchError(this.handleError)
      );
  }



  get(id: string | number): Observable<T> {
    return this.httpClient.get<T>(`${this.APIUrl}/${id}`)
      .pipe(
        map((json) => this.fromServerModel(json)),
        catchError(this.handleError)
      );
  }

  add(resource: T): Observable<any> {
    return this.httpClient.post(`${this.APIUrl}`, this.toServerModel(resource))
      .pipe(
        catchError(this.handleError)
      );
  }

  delete(id: string | number):Observable<any> {
    return this.httpClient.delete(`${this.APIUrl}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  update(resource: T) {
    return this.httpClient.put(`/${this.APIUrl}`, this.toServerModel(resource))
      .pipe(
        catchError(this.handleError)
      );
  }

  protected handleError(error: HttpErrorResponse) {
    // Handle the HTTP error here
    let msg = 'Errore';
     if (error.status === 0) { // client side o netowrk error
       console.error ('Errore invocazione servizio http: ', error.error);
       msg = 'Client/Netowk Error';
     } else {
       console.error(`Errore Backend http code ${error.status}, body: `, error.error);    
       msg = (error.error.code !== undefined ? error.error.code + ' - ' : '') + 'Invocazione servizi business-codice: ' +  (error.error.msg !== undefined ? error.error.msg : error.status);
     }
    // return throwError('Something wrong happened');
    return throwError(() => new Error(msg));
  }
}
