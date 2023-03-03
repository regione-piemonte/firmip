/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { Component, OnInit } from '@angular/core';
import {IniziativeHttpService} from "../../../../services/iniziative-http.service";
import {Iniziativa} from "../../../../model/firmipbo-data-model";
import {ITableCmd, ITableEvent} from "../../../../components/commons/w-table/table-models";
import {Router} from "@angular/router";
import { UtilityService } from 'src/app/services/utility.service';
import {NGXLogger} from "ngx-logger";
import { MsgNotificationService } from 'src/app/services/msg-notification.service';
import {WDialogService} from "../../../../services/w-dialog.service";
import {DlgType, IDialogConfig} from "../../../../model/firmipbo-data-fe";

enum CMD_TABELLA_INIZIATIVE {
  VISUALIZZA_INIZIATIVA = 'CMD_vediIniziativa',
  MODIFICA_INIZIATIVA = 'CMD_Modifica',
  AGGIUNGI_FIRMA = 'CMD_AggiungiFirma',
  ELIMINA_INZIATIVA = 'CMD_Elimina'
}

@Component({
  selector: 'firmipbo-iniziative-page',
  templateUrl: './iniziative-page.component.html',
  styleUrls: ['./iniziative-page.component.css']
})
export class IniziativePageComponent implements OnInit {

  elencoIniziative : Iniziativa[] = [];
  elencoIniziativeFiltrato : Iniziativa[] = [];
  private MIN_CHAR_FILTER = 3;

  constructor(private iniziativeHttpService: IniziativeHttpService,
              private ruoter: Router,
              private utility: UtilityService,
              private logger: NGXLogger,
              private msgNotifocationService: MsgNotificationService,
              private dialogService: WDialogService) {
    this.elencoIniziative = [];
  }

  ngOnInit(): void {
    if (this.elencoIniziative.length === 0 ) {
         this.iniziativeHttpService.getList().subscribe( (elenco) => {
           this.elencoIniziative = elenco;
           this.assignCopy();
         },
           error => {
             this.msgNotifocationService.sendMsgError(error, null)
           }
      );
    }
  }

  
  gestisciComando($event: ITableEvent) {
    switch ($event.cmd) {
      case CMD_TABELLA_INIZIATIVE.VISUALIZZA_INIZIATIVA :
         this.logger.debug('Visualizza Iniziativa');
         let riga: Iniziativa = $event.data;
           console.log(riga.id);
        this.ruoter.navigate(['/firme/' +riga.id ]);
        break;
      case CMD_TABELLA_INIZIATIVE.MODIFICA_INIZIATIVA :
        this.logger.debug('Modifica Iniziativa');
          let riga2: Iniziativa = $event.data;
            this.logger.debug(riga2.id);
            this.utility.changeBreadcrumb(this.ruoter, 'modifica-iniziativa', 'Mod', riga2.titolo);
            this.utility.setInfoRoute(this.ruoter, 'modifica-iniziativa', riga2);
            this.ruoter.navigate(['modifica-iniziativa']);
         break;
      case CMD_TABELLA_INIZIATIVE.AGGIUNGI_FIRMA :
        this.logger.debug('Aggiungi Firma');
        let riga1: Iniziativa = $event.data;
        console.log(riga1.id);
        this.ruoter.navigate(['/firme/INS'+'/' +riga1.id + '/-1']);
        break;
      case CMD_TABELLA_INIZIATIVE.ELIMINA_INZIATIVA :
          this.logger.debug('Elimina Iniziativa');
          let riga3: Iniziativa = $event.data;
            console.log(riga3.id);
        this.dialogService.open({
          tipo: DlgType.QUESTION,
          titolo: 'Elimina iniziativa',
          messaggio: '<p>Confermi di voler eliminare "<b>' + riga3.titolo + '</b>?' + (riga3.numeroFirme === 0 ? '' : '<br> Verranno eliminate anche le <b>' + riga3.numeroFirme + ' firme</b> relative.</p>'),
          okLabel: 'Sì, elimina',
          cancelLabel: 'No, annulla',
          cancelVisible: true,
          okHandler: () => {this.eliminaIniziativa(riga3)}
        } as IDialogConfig );
         break;
      default:

    }
  }


  eliminaIniziativa(riga3: Iniziativa) : void {
    this.iniziativeHttpService.delete(riga3.id).subscribe(
      (resp) => {
        const index = this.elencoIniziative.findIndex(obj => obj.id === riga3.id);
        if(index > -1){
          this.elencoIniziative.splice(index, 1);
        }
        const indexFiltro = this.elencoIniziativeFiltrato.findIndex(obj => obj.id === riga3.id);
        if(indexFiltro > -1){
          this.elencoIniziativeFiltrato.splice(indexFiltro, 1);
        }
        this.msgNotifocationService.sendMsgSuccess("Iniziativa \"" + riga3.titolo + "\" cancellata con successo", null);
      },
      (error => {
        this.logger.error('Errore invocazione servizio cancellazione ' + error);
      })
    );
  }


  assignCopy(){
    this.elencoIniziativeFiltrato = Object.assign([], this.elencoIniziative);
  }



  aggiungiIniziativa(){
    this.ruoter.navigate(['nuova-iniziativa']);
  }

  filtraIniziative(value: string) {
    if(!value){
      this.assignCopy();
    }  else if (value.length >= this.MIN_CHAR_FILTER) {
      this.elencoIniziativeFiltrato = Object.assign([], this.elencoIniziative.filter(
        (item) => item.titolo.toLowerCase().indexOf(value.toLowerCase()) > -1
      ))
    }
  }
}
