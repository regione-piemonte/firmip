/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { Injectable } from '@angular/core';
import {ResourceHttpService} from "./resource-http.service";
import {Firma, Iniziativa} from "../model/firmipbo-data-model";
import {HttpClient, HttpParams} from "@angular/common/http";
import {MsgNotificationService} from "./msg-notification.service";
import {catchError, map, Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {CriterioRicercaFirme} from "../model/firmipbo-data-fe";

@Injectable({
  providedIn: 'root'
})
export class IniziativeHttpService extends ResourceHttpService<Iniziativa> {

  constructor(protected override httpClient: HttpClient
              ) {super(httpClient);

  }


  getResourceUrl(): string {
    return 'iniziative';
  }

  override toServerModel(entity: Iniziativa): any {
    delete entity.descrizioneTipo;
    return {
      ...entity,
    }
  }

  // json ritornato dal server => model
  override fromServerModel(json: any): Iniziativa {
    // return json;
    return {
      ...json,
      descrizioneTipo: json.tipo.descrizione
    }
  }


  override  update(resource: Iniziativa) {
    let endpoint = this.baseURL + this.getResourceUrl() +'/'+resource.id;
    return this.httpClient.put(endpoint, this.toServerModel(resource))
      .pipe(
        catchError(this.handleError)
      );
  }






}
