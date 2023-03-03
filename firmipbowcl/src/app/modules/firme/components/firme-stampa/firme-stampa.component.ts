/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Firma, Iniziativa} from "../../../../model/firmipbo-data-model";
import * as _ from "lodash"
import {IniziativeHttpService} from "../../../../services/iniziative-http.service";
import {Location} from "@angular/common";

@Component({
  selector: 'firmipbo-firme-stampa',
  templateUrl: './firme-stampa.component.html',
  styleUrls: ['./firme-stampa.component.css']
})
export class FirmeStampaComponent implements OnInit {

  elencoFirme : Firma[] = []
  iniziativaSelezionata : Iniziativa;
  idIniziativa : number;

  constructor(
    private iniziativeHttpService : IniziativeHttpService,
    private route: ActivatedRoute,
    private router: Router,
    private location: Location
  ) { }

  ngOnInit(): void {
    if (this.route.data !== null) {
      this.route.data.subscribe(data => {
        if (!_.isEmpty(data['info'])) {
          this.elencoFirme = data['info'];
          this.idIniziativa = this.elencoFirme[0].idIniziativa;
          this.iniziativeHttpService.get(this.idIniziativa).subscribe({
              next: iniziativa => {
                this.iniziativaSelezionata = iniziativa;
                setTimeout(
                  function () {
                    print();
                    window.history.back();
                  }, 30);
              }
          });
        }
      });
    }
  }
}
