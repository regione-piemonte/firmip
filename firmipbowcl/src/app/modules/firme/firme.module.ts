/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FirmeRoutingModule } from './firme-routing.module';
import { FirmeTabellaComponent } from './components/firme-tabella/firme-tabella.component';
import { FirmePageComponent } from './components/firme-page/firme-page.component';
import {FirmeRicercaComponent} from "./components/firme-ricerca/firme-ricerca.component";
import { FirmeUpdateComponent } from './components/firme-update/firme-update.component';
import {IniziativeModule} from "../iniziative/iniziative.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AutocompleteLibModule} from "angular-ng-autocomplete";
import { FirmeStampaComponent } from './components/firme-stampa/firme-stampa.component';
import {MsgNotificationService} from "../../services/msg-notification.service";
import {NgbDatepickerModule} from "@ng-bootstrap/ng-bootstrap";



@NgModule({
  declarations: [
    FirmeTabellaComponent,
    FirmePageComponent,
    FirmeRicercaComponent,
    FirmeUpdateComponent,
    FirmeStampaComponent,

  ],
  imports: [
    CommonModule,
    FirmeRoutingModule,
    IniziativeModule,
    FormsModule,
    AutocompleteLibModule,
    ReactiveFormsModule,
    NgbDatepickerModule,

  ],
  providers: [MsgNotificationService],
  exports :[FirmeRoutingModule]
})
export class FirmeModule { }
