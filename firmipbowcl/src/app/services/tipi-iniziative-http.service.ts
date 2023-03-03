/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { Injectable } from '@angular/core';
import {ResourceHttpService} from "./resource-http.service";
import { TipoIniziativa} from "../model/firmipbo-data-model";
import {HttpClient} from "@angular/common/http";
import {MsgNotificationService} from "./msg-notification.service";

@Injectable({
  providedIn: 'root'
})
export class TipoIniziativaHttpService extends ResourceHttpService<TipoIniziativa> {

  constructor(protected override httpClient: HttpClient) {super(httpClient) }

  getResourceUrl(): string {
    return "TipoIniziativa";
  }




}
