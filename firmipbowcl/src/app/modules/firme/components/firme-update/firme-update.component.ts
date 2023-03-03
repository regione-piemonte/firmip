/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UtilityService} from "../../../../services/utility.service";
import {FirmeHttpService} from "../../../../services/firme-http.service";
import {IniziativeHttpService} from "../../../../services/iniziative-http.service";
import {Comune, Firma, Iniziativa, TipoDocumento} from "../../../../model/firmipbo-data-model";
import { FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {NGXLogger} from "ngx-logger";
import {ComuniHttpService} from "../../../../services/comuni-http.service";
import {MsgNotificationService} from "../../../../services/msg-notification.service";
import {CriterioRicercaFirme, NOTIFY_ERROR_MESSAGE_AND_INFO} from "../../../../model/firmipbo-data-fe";
import {TipoDocumentoService} from "../../../../services/tipo-documento.service";
import * as moment from "moment";

enum OPERAZIONE {
  INSERT = 'INS',
  UPDATE = 'UPD'
}


@Component({
  selector: 'firmipbo-firme-update',
  templateUrl: './firme-update.component.html',
  styleUrls: ['./firme-update.component.css']
})


export class FirmeUpdateComponent implements OnInit, OnDestroy {


  private operazione: string;
  idIniziativa: number;
  private idFirma: number;
  private labelBack: string;
  private iniziativa: Iniziativa;
  private static MIN_INPUT_SEARCH = 1;
  private static MAX_INPUT_SEARCH = 5;
  firma: Firma;
  frmUpdateFirme: FormGroup;
  preSubmit: boolean = true;
  elencoTipiDocumenti: TipoDocumento[];
  elencoComuni: Comune[];
  comuneSelezionato: Comune;
  searchKey = 'nome';
  isLoadingComuni = false;
  tipoOperazione: any;
  titoloIniziativa: string;
  btnSubmitLabel: string;
  countFirme: string;
  maxNumFoglio: string;
  focusCognome: boolean;
  dataError: boolean = false;


  get ff() {
    return this.frmUpdateFirme.controls as any;
  }

  constructor(private acRoute: ActivatedRoute,
              private utility: UtilityService,
              private ruoter: Router,
              private firmeHttpService: FirmeHttpService,
              private logger: NGXLogger,
              private _fb: FormBuilder,
              private iniziativeHttpService: IniziativeHttpService,
              private comuniHttpService: ComuniHttpService,
              private msgNotifocationService: MsgNotificationService,
              private tipoDocumentiService: TipoDocumentoService) {

    var comune_vuoto: Comune = null;
    this.elencoComuni = [];
    this.frmUpdateFirme = _fb.group({
      cognome: [null, Validators.required],
      nome: [null, Validators.required],
      dataNascita: [null, Validators.required],
      luogoNascita: [null, Validators.required],
      indirizzo: [null],
      comune: [null, Validators.required],
      tipoDocumento: [null],
      numDocumento: [null],
      numFoglio: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    //window.scrollTo(0,0);
    // leggo operazione
    this.operazione = this.acRoute.snapshot.params['operazione'];
    this.idIniziativa = this.acRoute.snapshot.params['idIniziativa'];
    this.idFirma = this.acRoute.snapshot.params['idFirma'];

    // recupero dati iniziativa
    this.iniziativeHttpService.get(this.idIniziativa).subscribe(
      (iniziativa) => {
        this.iniziativa = iniziativa;
        this.impostaRotte();
        this.aggiornaConteggiFirme();
        this.titoloIniziativa = iniziativa.titolo;
      }
    );
    this.tipoDocumentiService.getList().subscribe(
      (elenco) => this.elencoTipiDocumenti = elenco
    );

    // Imposta variabile lavori
    switch (this.operazione) {
      case OPERAZIONE.INSERT:
        //  this.titoloIniziativa = this.iniziativa.titolo;
        this.tipoOperazione = "Nuova Firma";
        this.btnSubmitLabel = 'Inserisci';

        break;
      case OPERAZIONE.UPDATE:
        this.firmeHttpService.get(this.idFirma).subscribe(
          (f) => {
            this.firma = f;
            this.setCampiForm();
          });
        this.tipoOperazione = "Modifica Firma";
        this.btnSubmitLabel = 'Salva';
        break;
    }
  }


  private impostaRotte() {
    this.utility.changeBreadcrumb(this.ruoter, 'firme/:operazione/:idIniziativa/:idFirma', 'back', this.iniziativa.titolo, 'firme/' + this.iniziativa.id);
    switch (this.operazione) {
      case OPERAZIONE.INSERT:
        // label
        this.utility.changeBreadcrumb(this.ruoter, 'firme/:operazione/:idIniziativa/:idFirma', 'comando', 'Inserisci Firma');
        break;
      case OPERAZIONE.UPDATE:
        this.utility.changeBreadcrumb(this.ruoter, 'firme/:operazione/:idIniziativa/:idFirma', 'comando', 'Modifica Firma');
    }
  }

  ngOnDestroy(): void {
    this.utility.changeBreadcrumb(this.ruoter, 'firme/:operazione/:idIniziativa/:idFirma', this.iniziativa.titolo, 'back', '');
    switch (this.operazione) {
      case OPERAZIONE.INSERT:
        // label
        this.utility.changeBreadcrumb(this.ruoter, 'firme/:operazione/:idIniziativa/:idFirma', 'Inserisci Firma', 'comando',);
        break;
      case OPERAZIONE.UPDATE:
        this.utility.changeBreadcrumb(this.ruoter, 'firme/:operazione/:idIniziativa/:idFirma', 'Modifica Firma', 'comando',);
    }
  }

  submitForm() {
    this.preSubmit = false;
    console.log('submit');
    if(this.frmUpdateFirme.controls["comune"].value != null &&
      this.frmUpdateFirme.controls["comune"].value.length > 0 &&
      this.frmUpdateFirme.controls["comune"].value.codice == null){
      this.msgNotifocationService.sendMsgError(NOTIFY_ERROR_MESSAGE_AND_INFO.ERROR_TYPE_GENERICO, NOTIFY_ERROR_MESSAGE_AND_INFO.ERROR_TYPE_SELEZIONE_COMUNE);
      return;
    }
    if(this.frmUpdateFirme.controls["comune"].value != null && this.frmUpdateFirme.controls["comune"].value.length == 0 ){
      this.frmUpdateFirme.controls["comune"].setValue(null);
    }
    let v = this.frmUpdateFirme.value;
    if (this.frmUpdateFirme.valid) {
      switch (this.operazione) {
        case OPERAZIONE.INSERT:
          let numvaFirma: Firma = {
            ...v,
            idIniziativa: this.idIniziativa
          };
          this.firmeHttpService.addFirma(this.idIniziativa, numvaFirma).subscribe(
            (res) => {
              delete res.id;
              this.frmUpdateFirme.patchValue({...res});
              this.msgNotifocationService.sendMsgSuccess(NOTIFY_ERROR_MESSAGE_AND_INFO.SUCCESS_MESSAGE_INSERT_FIRMA, null);
              this.aggiornaConteggiFirme();
              this.pulisciCampi();
            },
            (error) => {
              console.log(error.name)
              if(error.message.includes('FIRMIP-02014')){
                this.msgNotifocationService.sendMsgError(NOTIFY_ERROR_MESSAGE_AND_INFO.ERROR_TYPE_MINORENNE, null);
              }
              else if(error.message.includes('FIRMIP-02100')){
                this.msgNotifocationService.sendMsgError(NOTIFY_ERROR_MESSAGE_AND_INFO.ERROR_TYPE_FIRMA_DUPLICATA, NOTIFY_ERROR_MESSAGE_AND_INFO.ERROR_MESSAGE_FIRMA_DUPLICATA);
              }
              else {
                this.msgNotifocationService.sendMsgError(NOTIFY_ERROR_MESSAGE_AND_INFO.ERROR_TYPE_GENERICO, NOTIFY_ERROR_MESSAGE_AND_INFO.ERROR_TYPE_GENERICO_DETAIL);
              }
            });
          break;
        case OPERAZIONE.UPDATE:
          let f: Firma = {
            ...v,
            id: this.firma.id,
            idIniziativa: this.idIniziativa
          };
          this.firmeHttpService.updateFirma(this.idIniziativa, f).subscribe(
            (res) => {
              this.frmUpdateFirme.patchValue(res);
              this.msgNotifocationService.sendMsgSuccess(NOTIFY_ERROR_MESSAGE_AND_INFO.SUCCESS_MESSAGE_MODIFY_FIRMA, null);
              this.aggiornaConteggiFirme();
            },
            (error) => {
              console.log(error.name)
              if(error.message.includes('FIRMIP-02014')){
                this.msgNotifocationService.sendMsgError(NOTIFY_ERROR_MESSAGE_AND_INFO.ERROR_TYPE_MINORENNE, null);
              }
              else if(error.message.includes('FIRMIP-02100')){
                this.msgNotifocationService.sendMsgError(NOTIFY_ERROR_MESSAGE_AND_INFO.ERROR_TYPE_FIRMA_DUPLICATA, NOTIFY_ERROR_MESSAGE_AND_INFO.ERROR_MESSAGE_FIRMA_DUPLICATA);
              }
              else {
                this.msgNotifocationService.sendMsgError(NOTIFY_ERROR_MESSAGE_AND_INFO.ERROR_TYPE_GENERICO, NOTIFY_ERROR_MESSAGE_AND_INFO.ERROR_TYPE_GENERICO_DETAIL);
              }
            });
          console.log(JSON.stringify(f));
          break;
      }
    }
  }


  private setCampiForm() {
    this.logger.debug('Setting dei valori ' + JSON.stringify(this.firma));
    this.frmUpdateFirme.setValue({
      nome: this.firma.nome !== undefined ? this.firma.nome : null,
      cognome: this.firma.cognome !== undefined ? this.firma.cognome : null,
      comune: this.firma.comune !== undefined ? this.firma.comune : null,
      dataNascita: this.firma.dataNascita !== undefined ? this.firma.dataNascita : null,
      luogoNascita: this.firma.luogoNascita !== undefined ? this.firma.luogoNascita : null,
      indirizzo: this.firma.indirizzo !== undefined ? this.firma.indirizzo : null,
      tipoDocumento: this.firma.tipoDocumento !== undefined ? this.firma.tipoDocumento : null,
      numDocumento: this.firma.numDocumento !== undefined ? this.firma.numDocumento : null,
      numFoglio: this.firma.numFoglio !== undefined ? this.firma.numFoglio : null
    })
  }

  selectEvent($event: any) {
    this.logger.debug('Select comune :' + $event);
    console.log($event);
  }

  clearFilter() {
    this.comuneSelezionato = null;
    this.elencoComuni = [];
  }

  onChangeSearch($event: any) {
    let strInput = $event;
    if (strInput.length >= FirmeUpdateComponent.MIN_INPUT_SEARCH &&
      strInput.length <= FirmeUpdateComponent.MAX_INPUT_SEARCH &&
      !this.isLoadingComuni &&
      this.elencoComuni.length === 0
    ) {
      this.loadComuniByNome(strInput.substring(0,1));
    }
  }

  private loadComuniByNome(nome: string) {
    this.isLoadingComuni = true;
    this.comuniHttpService.getElencoComuniByNome(nome).subscribe(
      (elenco) => {
        this.elencoComuni = elenco;
        this.isLoadingComuni = false;
      }
    );
  }

  private aggiornaConteggiFirme() {
    this.firmeHttpService.getFirmeCount(this.idIniziativa
    ).subscribe((v) => this.countFirme = v);

    this.firmeHttpService.getMaxNumFoglio(this.idIniziativa
    ).subscribe((v) => {
      this.maxNumFoglio = v;
      if (this.operazione === OPERAZIONE.INSERT) {
        // setto il numero foglio
        this.frmUpdateFirme.controls['numFoglio'].setValue(this.maxNumFoglio);
      }
    });
  }

  onFocusNomeOut($event: FocusEvent) {
    this.checkOmonimia();
  }


  private checkOmonimia() {
    let nome = this.frmUpdateFirme.get('nome').value;
    let cognome = this.frmUpdateFirme.get('cognome').value;
    if (nome && cognome) {
      let criterio = {
        idIniziativa: this.idIniziativa,
        cognome: cognome,
        nome: nome,
        ricercaStringheEsatte: true
      } as CriterioRicercaFirme;

      this.firmeHttpService.getFirmeByIniziativa(criterio).subscribe(
        (response) => {
          let c = response.length;
          if (c > 0) {
            this.msgNotifocationService.sendMsgWarning(NOTIFY_ERROR_MESSAGE_AND_INFO.WARNING_TYPE_FIRMA_OMONIMA,
              NOTIFY_ERROR_MESSAGE_AND_INFO.WARNING_MESSAGE_FIRMA_OMONIMA + '\"' + this.iniziativa.titolo + '\".');
          }
        }
      );
    }
  }

  public pulisciCampi(): void {
    this.frmUpdateFirme.setValue({
      nome: null,
      cognome: null,
      comune: null,
      dataNascita: null,
      luogoNascita: null,
      indirizzo: null,
      tipoDocumento: null,
      numDocumento: null,
      numFoglio: this.firma !== undefined ? (this.firma.numFoglio !== undefined ? this.firma.numFoglio : this.maxNumFoglio) : this.maxNumFoglio
    })
  }

  public dateControl(): void {
    //console.log(this.firma.dataNascita);
    const now = moment(new Date(), 'YYYY-MM-DD');
    const tooOld = moment(new Date('1900-01-01'), 'YYYY-MM-DD');

    let data = moment(new Date(this.ff.dataNascita.value), 'YYYY-MM-DD');
    this.dataError = data > now || data < tooOld;
  }

}
