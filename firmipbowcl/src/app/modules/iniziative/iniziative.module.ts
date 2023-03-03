/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IniziativePageComponent } from './components/iniziative-page/iniziative-page.component';
import { IniziativeFiltroComponent } from './components/iniziative-filtro/iniziative-filtro.component';
import { IniziativeElencoComponent } from './components/iniziative-elenco/iniziative-elenco.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { IniziativeRoutingModule } from './iniziative-routing.module';
import { IniziativeTabellaComponent } from './components/iniziative-tabella/iniziative-tabella.component';
import {WTableComponent} from "../../components/commons/w-table/w-table.component";
import {SortDirective} from "../../components/commons/w-table/sort.directive";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {BrowserModule} from "@angular/platform-browser";
import { UtilityService } from 'src/app/services/utility.service';
import { IniziativeUpdateComponent } from './components/iniziative-update/iniziative-update.component';
import {WDialogService} from "../../services/w-dialog.service";



@NgModule({
    declarations: [
        IniziativePageComponent,
        IniziativeFiltroComponent,
        IniziativeElencoComponent,
        IniziativeTabellaComponent,
        IniziativeUpdateComponent,
        WTableComponent,
        SortDirective,
    ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        BrowserModule,
        IniziativeRoutingModule,
        NgbModule,
        FormsModule,

    ],
  exports: [
    WTableComponent
  ],
    providers: [
        UtilityService, WDialogService
    ]
})
export class IniziativeModule { }
