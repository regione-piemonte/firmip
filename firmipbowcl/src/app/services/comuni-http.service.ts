/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { Injectable } from '@angular/core';
import {ResourceHttpService} from "./resource-http.service";
import {Comune, Iniziativa} from "../model/firmipbo-data-model";
import {HttpClient, HttpParams} from "@angular/common/http";
import {catchError, map, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ComuniHttpService extends ResourceHttpService<Comune>{

  constructor(protected override httpClient: HttpClient
  ) {super(httpClient) }



  getResourceUrl(): string {
    return "comuni";
  }

  get
      getResourceUrlComuniByIstat() : string {
        return this.getResourceUrl() + '/' + 'by-istat-regione';
   }


  getElencoComuniByIstatRegione(istatRegione: string):Observable<Comune[]> {
      return this.httpClient.get<Comune[]>(`${this.baseURL}${this.getResourceUrlComuniByIstat}/${istatRegione}`)
        .pipe(
          map((list) => list.map((item)=> this.fromServerModel(item))),
          catchError(this.handleError)
        );
  }


  getElencoComuniByNome(nome: string) : Observable<Comune[]> {
    const params = new HttpParams()
      .set('nome',nome);
    return this.httpClient.get<Comune[]>(`${this.baseURL}${this.getResourceUrl()}`,{params})
      .pipe(
        map((list) => list.map((item)=> this.fromServerModel(item))),
        catchError(this.handleError)
      );
  }





}
