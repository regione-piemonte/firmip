/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Routes, RouterModule} from "@angular/router";
import {AuthenticationComponent} from "./components/auth/authentication/authentication.component";
import {errorRoutes} from "./components/commons/error/error-pages.routing";

const appRoutes: Routes = [
                            ...errorRoutes,
                            {
                              path: 'login',
                              component: AuthenticationComponent,
                            },
                            { path: '',
                              redirectTo: '/login',
                              pathMatch: 'full'
                            }
                         ];

@NgModule({
  imports: [
    CommonModule,RouterModule.forRoot(appRoutes, { useHash: true })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
