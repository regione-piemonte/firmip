/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {FirmePageComponent} from "./components/firme-page/firme-page.component";
import {FirmeUpdateComponent} from "./components/firme-update/firme-update.component";
import {FirmeStampaComponent} from "./components/firme-stampa/firme-stampa.component";
import {IniziativeUpdateComponent} from "../iniziative/components/iniziative-update/iniziative-update.component";

const routes: Routes = [
  {
    path: 'firme/:idIniziativa', component: FirmePageComponent,
    data: {
      info: {},
      breadcrumb: [
        {
          label: 'Home',
          url: '',
          active: false,
          class: 'breadcrumb-item'
        },
        {
          label: 'firme',
          url: '',
          active: true,
          class: 'breadcrumb-item active'
        }
      ]
    }
  },
  {
    path: 'firme/:operazione/:idIniziativa/:idFirma', component: FirmeUpdateComponent,
    data: {
      info: {},
      breadcrumb: [
        {
          label: 'Home',
          url: '',
          active: false,
          class: 'breadcrumb-item'
        },
        {
          label: 'back',
          url: '',
          active: false,
          class: 'breadcrumb-item'
        },
        {
          label: 'comando',
          url: '',
          active: true,
          class: 'breadcrumb-item active'
        }
      ]
    }
  },
  {
    path: 'firme/elenco/stampa', component: FirmeStampaComponent,
    data: {
      info: {},
      breadcrumb: []
    }
  }  ,
  {path: '**', redirectTo: '404'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class FirmeRoutingModule { }
