/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { Injectable } from '@angular/core';
import {BehaviorSubject, Subject} from "rxjs";
import {IDialogConfig} from "../model/firmipbo-data-fe";

@Injectable()
export class WDialogService {
  constructor() { }

  private config$: Subject<IDialogConfig> = new Subject<IDialogConfig>();

  get configObs() {
    return this.config$.asObservable();
  }

  // funzione per open della dialog
  open(config: IDialogConfig) {
    this.config$.next(config);
  }

}
