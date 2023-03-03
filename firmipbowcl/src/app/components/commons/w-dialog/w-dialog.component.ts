/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { Component, OnInit } from '@angular/core';
import {DlgType, IDialogConfig} from "../../../model/firmipbo-data-fe";
import {takeWhile} from "rxjs";
import {WDialogService} from "../../../services/w-dialog.service";

@Component({
  selector: 'firmipbo-w-dialog',
  templateUrl: './w-dialog.component.html',
  styleUrls: ['./w-dialog.component.css']
})
export class WDialogComponent implements OnInit {

  private subscribed: boolean = true;
  config: IDialogConfig;
  dialogVisible: boolean;
  constructor(private dialogService: WDialogService) { }

  ngOnInit(): void {
    this.dialogService.configObs
      .pipe(takeWhile(() => this.subscribed))
      .subscribe((config) => {
        console.log('subscribe');
        if (config)
          this.open(config);
      });
  }

  private open(config: IDialogConfig) {
    this.config = {
      tipo: config.tipo ?? DlgType.INFO,
      titolo: config.titolo ?? 'Avviso',
      messaggio: config.messaggio ?? 'Vuoi continuare ?',
      cancelVisible: config.cancelVisible ?? false,
      okLabel: config.okLabel ?? 'OK',
      cancelLabel: config.cancelLabel ?? 'Cancel',
      okHandler: config.okHandler ?? (() => {})
    } as IDialogConfig;
    this.dialogVisible = true;
  }

  close(okClicked: boolean){
    if (okClicked) {
      this.config.okHandler();
    }
    this.dialogVisible = false;
  }
}
