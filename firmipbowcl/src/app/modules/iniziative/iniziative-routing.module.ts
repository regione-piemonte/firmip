/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {IniziativePageComponent} from "./components/iniziative-page/iniziative-page.component";
import {IniziativeUpdateComponent} from "./components/iniziative-update/iniziative-update.component";


const routes: Routes = [ {path: 'home',
  component: IniziativePageComponent,
  data: {
    breadcrumb: [
      {
        label: 'Home',
        url: '',
        active: true,
        class: 'breadcrumb-item active'
      }
    ]
  }
},
{path: 'nuova-iniziativa',
  component: IniziativeUpdateComponent,
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
        label: 'Nuova iniziativa',
        url: '',
        active: true,
        class: 'breadcrumb-item active'
      }
    ]
  }
},
{path: 'modifica-iniziativa',
  component: IniziativeUpdateComponent,
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
        label: 'Mod',
        url: '',
        active: true,
        class: 'breadcrumb-item active'
      }
    ]
  }
}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { useHash: true })
  ],
  exports: [RouterModule]
})

export class IniziativeRoutingModule { }
