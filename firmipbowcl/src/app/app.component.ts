/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {CsiSpinnerComponent} from "./components/commons/csi-spinner/csi-spinner.component";

@Component({
  selector: 'firmipbo-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy, AfterViewInit{
  title = 'FIRMIP - Raccolta Firme';
  public CsiSpinnerComponent =  CsiSpinnerComponent;
  constructor(

  ) { }


  ngOnInit() : void {

  }

  ngAfterViewInit(): void {

  }

  ngOnDestroy(): void {
  }



}
