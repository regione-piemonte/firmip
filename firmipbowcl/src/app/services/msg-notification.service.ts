/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
// Servizio per gestire le notifiche dei messaggi

import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {MsgNotifica, TYPE_MSG_NOTIFICA} from "../model/firmipbo-data-fe";

@Injectable({
  providedIn: 'root'
})
export class  MsgNotificationService {
private _sendMsgNotificatiop = new BehaviorSubject<MsgNotifica>(null);
readonly msgNotification$ = this._sendMsgNotificatiop.asObservable();

  constructor() { }

  sendMsgSuccess(msg: string, extra: string) {
    this._sendMsgNotificatiop.next({type: TYPE_MSG_NOTIFICA.SUCCCESS, message: msg, extraMessage: extra});
  }

  sendMsgWarning(msg: string, extra: string) {
    this._sendMsgNotificatiop.next({type: TYPE_MSG_NOTIFICA.WARNING, message: msg, extraMessage: extra});
  }

  sendMsgError(msg: string, extra: string) {
    this._sendMsgNotificatiop.next({type: TYPE_MSG_NOTIFICA.ERROR, message: msg, extraMessage: extra});
  }



}
