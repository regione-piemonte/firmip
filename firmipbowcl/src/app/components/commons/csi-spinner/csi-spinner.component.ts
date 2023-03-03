/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-csi-spinner',
  templateUrl: './csi-spinner.component.html',
  styleUrls: ['./csi-spinner.component.css']
})
export class CsiSpinnerComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    window.scrollTo(0,0);
  }

}
