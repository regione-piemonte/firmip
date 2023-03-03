/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NGXLogger} from "ngx-logger";
import {Firma, Iniziativa} from "../../../../model/firmipbo-data-model";
import {FirmeHttpService} from "../../../../services/firme-http.service";
import {MsgNotificationService} from "../../../../services/msg-notification.service";
import {UtilityService} from "../../../../services/utility.service";
import {IniziativeHttpService} from "../../../../services/iniziative-http.service";
import {DlgType, EsportaFirme, IDialogConfig} from "../../../../model/firmipbo-data-fe";
import {ITableEvent} from "../../../../components/commons/w-table/table-models";
import {WDialogService} from "../../../../services/w-dialog.service";
import * as XLSX from 'xlsx';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

enum CMD_TABELLA_FIRME {
  MODIFICA_FIRMA = 'CMD_Modifica',
  ELIMINA_FIRMA = 'CMD_Elimina'
}


@Component({
  selector: 'firmipbo-firme-page',
  templateUrl: './firme-page.component.html',
  styleUrls: ['./firme-page.component.css']
})
export class FirmePageComponent implements OnInit, OnDestroy {

  private idIniziativa;
  elencoFirme: Firma[] = [];
  iniziativaSelezionata: Iniziativa;
  private MIN_CHAR_FILTER = 3;

  constructor(private firmeHttpService: FirmeHttpService,
              private iniziativeHttpService: IniziativeHttpService,
              private acRoute: ActivatedRoute,
              private router: Router,
              private logger: NGXLogger,
              private msgNotifocationService: MsgNotificationService,
              private utility: UtilityService,
              private dialogService: WDialogService,
              private modalService: NgbModal
  ) {
    this.elencoFirme = [];
  }

  ngOnInit(): void {

    this.idIniziativa = this.acRoute.snapshot.params['idIniziativa'];
    this.logger.debug('Id Iniziativa = ' + this.idIniziativa);
    if (isNaN(this.idIniziativa)) {
      this.router.navigate(['error'], {state: {msg: 'l\'URL non è corretto.'}});
    }else {
      // Recupero le informnazioni della iniziativa
      this.iniziativeHttpService.get(this.idIniziativa).subscribe(
        {
          next: iniziativa => {
            this.iniziativaSelezionata = iniziativa;
            this.utility.changeBreadcrumb(this.router, 'firme/:idIniziativa', 'firme', this.iniziativaSelezionata.titolo);
            if (this.elencoFirme.length === 0) {
              this.firmeHttpService.getListFirmeByIdIniziative(this.idIniziativa).subscribe((elenco) => {
                  this.elencoFirme = elenco;
                },
                error => {
                  this.msgNotifocationService.sendMsgError(error, null)
                }
              );
            }
          },
          error: err => this.logger.error(err)
        });
    }
  }

  ngOnDestroy(): void {
    if (this.iniziativaSelezionata) {
      this.utility.changeBreadcrumb(this.router, 'firme/:idIniziativa', this.iniziativaSelezionata.titolo, 'firme');
    }
  }

  ricercaFirme($event: any) {
    this.firmeHttpService.getFirmeByIniziativa($event).subscribe(
      (elenco) => this.elencoFirme = elenco
    );
    this.logger.debug('Submit ' + JSON.stringify($event));
  }

  comandoTabella($event: ITableEvent) {
    this.logger.debug(JSON.stringify($event));
    switch ($event.cmd) {
      case CMD_TABELLA_FIRME.MODIFICA_FIRMA:
        this.router.navigate(['/firme/UPD' + '/' + this.idIniziativa + '/' + $event.data['id']]);
        break;
      case CMD_TABELLA_FIRME.ELIMINA_FIRMA:
        this.logger.debug('Elimina Iniziativa');
        let firmaDaEliminare: Firma = $event.data;

        this.dialogService.open({
          tipo: DlgType.QUESTION,
          titolo: 'Elimina Firma',
          messaggio: '<p>Confermi di voler eliminare la firma di <b>' + firmaDaEliminare.cognome + '&nbsp' + firmaDaEliminare.nome + '</b>?</p>',
          okLabel: 'Sì, elimina',
          cancelLabel: 'No, annulla',
          cancelVisible: true,
          okHandler: () => {
            this.eliminaFirma(firmaDaEliminare)
          }
        } as IDialogConfig);
        break;
        break;
    }
  }

  private eliminaFirma(firmaDaEliminare: Firma) {
    this.firmeHttpService.delete(firmaDaEliminare.id).subscribe(
      () => {
        const index = this.elencoFirme.findIndex(obj => obj.id === firmaDaEliminare.id);
        if (index > -1) {
          this.elencoFirme.splice(index, 1);
        }
        const indexFiltro = this.elencoFirme.findIndex(obj => obj.id === firmaDaEliminare.id);
        if (indexFiltro > -1) {
          this.elencoFirme.splice(indexFiltro, 1);
        }
        this.msgNotifocationService.sendMsgSuccess("Firma di "  + firmaDaEliminare.cognome + " " + firmaDaEliminare.nome + " cancellata con successo", null);
      }
    )
  }

  stampaFirme() {
    let firme = this.elencoFirme;
    let firmePath = "firme/elenco/stampa";
    this.utility.setInfoRoute(this.router, firmePath, firme);
    this.router.navigate([firmePath]);
  }

  esportaFirme() {
    let firme = this.elencoFirme;
    let elencoEsportaFirme = []
    for (let i = 0; i < firme.length; i++) {
      elencoEsportaFirme[i] = new EsportaFirme(firme[i]);
    }
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(elencoEsportaFirme);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Firme');
    /* save to file */
    XLSX.writeFile(wb, this.iniziativaSelezionata.titolo + '_firme.xlsx');
  }

  modificaIniziative(): void {
    this.utility.changeBreadcrumb(this.router, 'modifica-iniziativa', 'Mod',this.iniziativaSelezionata.titolo);
    this.utility.setInfoRoute(this.router, 'modifica-iniziativa', this.iniziativaSelezionata);
      this.router.navigate(['/modifica-iniziativa'])
    }
}
