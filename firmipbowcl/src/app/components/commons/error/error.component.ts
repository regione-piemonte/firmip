/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, ActivatedRouteSnapshot, Route, Router} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
  selector: 'firmipbo-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {

  msg: string;
  constructor(
      private ar : ActivatedRoute,
      private r: Router
  ) {
    if (this.r.getCurrentNavigation().extras.state) {
      let routerState = this.r.getCurrentNavigation().extras.state;
      if (routerState){
        this.msg = routerState['msg'];
      }
    } else {
      this.ar.data.subscribe( (dati) =>
        this.msg = dati['msg'] );
    }
  }

  ngOnInit(): void {
  }

}
