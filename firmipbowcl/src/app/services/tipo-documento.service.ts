/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { Injectable } from '@angular/core';
import {ResourceHttpService} from "./resource-http.service";
import {Iniziativa, TipoDocumento} from "../model/firmipbo-data-model";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class TipoDocumentoService extends ResourceHttpService<TipoDocumento> {

  constructor(protected override httpClient: HttpClient
  ) {super(httpClient);

  }

  getResourceUrl(): string {
    return 'tipi-documento';
  }

}
