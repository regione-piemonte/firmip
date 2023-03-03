/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import {ErrorComponent} from "./error.component";


export const errorRoutes = [


  {
    path: 'error',
    component: ErrorComponent,
    data: {
      msg: 'Errore imprevisto',
      breadcrumb: [
        {
          label: 'Home',
          url: '',
          active: false,
          class: 'breadcrumb-item'
        }
      ]
    }
  },
  {
    path: '404',
    component: ErrorComponent,
    data: {
      msg: 'Pagina non trovata',
      breadcrumb: [
        {
          label: 'Home',
          url: '',
          active: false,
          class: 'breadcrumb-item'
        }
      ]
    }
  },
  {
    path: '403',
    component: ErrorComponent,
    data: {
      msg: 'Utente non autorizzato',
      breadcrumb: [
        {
          label: 'Home',
          url: '',
          active: false,
          class: 'breadcrumb-item'
        }
      ]
    }
  }
]
