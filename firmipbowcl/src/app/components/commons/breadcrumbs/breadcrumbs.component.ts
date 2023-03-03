/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { Component, OnInit } from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'firmipbo-breadcrumbs',
  templateUrl: './breadcrumbs.component.html',
  styleUrls: ['./breadcrumbs.component.css']
})
export class BreadcrumbsComponent implements OnInit {

  b: any;
  constructor(private route: Router) { }

  ngOnInit(): void {
    this.route.events.subscribe( event => {
      if (event instanceof NavigationEnd) {
        var snapshot = this.route.routerState.snapshot;
        this.b = snapshot.root.children[0].data['breadcrumb'];
      }
    });
  }

}
