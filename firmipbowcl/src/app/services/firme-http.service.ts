/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { Injectable } from '@angular/core';
import {ResourceHttpService} from "./resource-http.service";
import {Firma,} from "../model/firmipbo-data-model";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, map, Observable} from "rxjs";
import {CriterioRicercaFirme} from "../model/firmipbo-data-fe";


@Injectable({
  providedIn: 'root'
})
export class FirmeHttpService extends ResourceHttpService<Firma>{

  constructor(protected override httpClient: HttpClient
  ) {super(httpClient) }

   getResourceUrl(): string {
    return "firme";
  }

  getIniziativeResourceUrl() {
    return "iniziative";
  }

  public getListFirmeByIdIniziative(idIniziative : number): Observable<Firma[]> {

    return this.httpClient.get<Firma[]>(`${this.baseURL}${this.getIniziativeResourceUrl()}/${idIniziative}/firme`)
      .pipe(
        map((list) => list.map((item)=> this.fromServerModel(item))),
        catchError(super.handleError)
      );
  }

  getFirmeByIniziativa(criterio: CriterioRicercaFirme) : Observable<Firma[]> {
    let idIniziativa = criterio.idIniziativa;

    let criterioRicercaFirme = {};

    if (criterio.cognome ) {
      criterioRicercaFirme['cognome'] = criterio.cognome;
    }

    if (criterio.nome ) {
      criterioRicercaFirme['nome'] = criterio.nome;
    }
    if (criterio.indirizzo ) {
      criterioRicercaFirme['indirizzo'] = criterio.indirizzo;
    }
    if (criterio.codiceComune ) {
      criterioRicercaFirme['codiceComune'] = criterio.codiceComune;
    }
    if (criterio.tipoDocumento ) {
      criterioRicercaFirme['tipoDocumento'] = criterio.tipoDocumento;
    }
    if (criterio.numDocumento ) {
      criterioRicercaFirme['numDocumento'] = criterio.numDocumento;
    }
    if (criterio.numFoglio ) {
      criterioRicercaFirme['numFoglio'] = criterio.numFoglio;
    }
    if (criterio.ricercaStringheEsatte ) {
      criterioRicercaFirme['ricercaStringheEsatte'] = criterio.ricercaStringheEsatte;
    }

    return this.httpClient.get<Firma[]>(`${this.baseURL}${this.getIniziativeResourceUrl()}/${idIniziativa}/firme`, {params: criterioRicercaFirme})
      .pipe(
        map((list) => list.map((item)=> this.fromServerModel(item))),
        catchError(this.handleError)
      );
  }


  public addFirma(idIniziativa: number, firma: Firma) : Observable<Firma> {
    return this.httpClient.post<Firma[]>(`${this.baseURL}${this.getIniziativeResourceUrl()}/${idIniziativa}/firme`, this.toServerModel(firma))
      .pipe(
        map((json) => this.fromServerModel(json)),
        catchError(this.handleError)
      );
  }


  public updateFirma(idIniziativa: number, firma: Firma) : Observable<Firma> {
    return this.httpClient.put<Firma[]>(`${this.baseURL}${this.getIniziativeResourceUrl()}/${idIniziativa}/firme/${firma.id}`, this.toServerModel(firma))
      .pipe(
        map((json) => this.fromServerModel(json)),
        catchError(this.handleError)
      );
  }

  public getFirmeCount(idIniziativa)  {
    const headers = new HttpHeaders().set('Content-Type', 'text/plain; charset=utf-8');
    return this.httpClient.get(`${this.baseURL}${this.getIniziativeResourceUrl()}/${idIniziativa}/firme/count`,{headers, responseType: 'text'})
      .pipe(
        catchError(this.handleError)
      );
  }

  public getMaxNumFoglio(idIniziativa)  {
    const headers = new HttpHeaders().set('Content-Type', 'text/plain; charset=utf-8');
    return this.httpClient.get(`${this.baseURL}${this.getIniziativeResourceUrl()}/${idIniziativa}/firme/max-num-foglio`,{headers, responseType: 'text'})
      .pipe(
        catchError(this.handleError)
      );
  }

}
