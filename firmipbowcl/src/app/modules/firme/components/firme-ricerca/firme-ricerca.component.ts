/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Comune, Firma, Iniziativa, TipoDocumento} from "../../../../model/firmipbo-data-model";
import {ComuniHttpService} from "../../../../services/comuni-http.service";
import {NGXLogger} from "ngx-logger";
import {TipoDocumentoService} from "../../../../services/tipo-documento.service";
import {CriterioRicercaFirme} from "../../../../model/firmipbo-data-fe";

@Component({
  selector: 'firmipbo-firme-ricerca',
  templateUrl: './firme-ricerca.component.html',
  styleUrls: ['./firme-ricerca.component.css']
})
export class FirmeRicercaComponent implements OnInit {

 @Output()  submitRicercaFirme: EventEmitter<CriterioRicercaFirme> = new EventEmitter<CriterioRicercaFirme>();
 @Input() iniziativa: Iniziativa;

  private static  MIN_INPUT_SEARCH = 2;
  private static  MAX_INPUT_SEARCH = 5;

  frmRicercaFirme: FormGroup;
  elencoTipiDocumenti: TipoDocumento[];
  elencoComuni: Comune[];
  comuneSelezionato: Comune;
  searchKey = 'nome';
  isLoadingComuni = false;
  disableReset = true;

  constructor(private comuniHttpService: ComuniHttpService,
              private tipoDocumentiService: TipoDocumentoService,
              private logger: NGXLogger,
              private _fb: FormBuilder
             ) {
    var comune_vuoto: Comune = {nome: "", codice:"", siglaProvincia:""};
    this.elencoComuni = [];
    this.frmRicercaFirme = _fb.group({
      cognome: [''],
      nome: [''],
      indirizzo: [''],
      comune: [{value: comune_vuoto, disabled: false},],
      tipoDocumento: [''],
      numeroDocumento: [''],
      numeroFoglio: ['']
    });
  }

  submitForm() {
    let criterioRicerca = {
      'idIniziativa'    :   this.iniziativa.id,
      'cognome'         :   this.frmRicercaFirme.value['cognome'].toUpperCase(),
      'nome'            :   this.frmRicercaFirme.value['nome'].toUpperCase(),
      'codiceComune'    :   this.frmRicercaFirme.value['comune']['codice'],
      'tipoDocumento'   :   this.frmRicercaFirme.value['tipoDocumento'],
      'indirizzo'       :   this.frmRicercaFirme.value['indirizzo'].toUpperCase(),
      'numDocumento' :   this.frmRicercaFirme.value['numeroDocumento'].toUpperCase(),
      'numFoglio'    :   this.frmRicercaFirme.value['numeroFoglio'],
      'ricercaStringheEsatte': false
    } as CriterioRicercaFirme ;
    this.logger.debug('Criterio : ' + JSON.stringify(criterioRicerca));
    this.disableReset = false;
    this.submitRicercaFirme.emit(criterioRicerca);
  }

  ngOnInit(): void {
    this.tipoDocumentiService.getList().subscribe(
      (elenco) => this.elencoTipiDocumenti = elenco
    );

  }

  selectEvent($event: any) {
    this.logger.debug('Select comune :' +  $event);
    console.log($event);
  }

  clearFilter() {
    this.comuneSelezionato = null;
    this.elencoComuni = [];
  }

  onChangeSearch($event: any) {
    let strInput = $event;
    if (strInput.length >= FirmeRicercaComponent.MIN_INPUT_SEARCH &&
        strInput.length <= FirmeRicercaComponent.MAX_INPUT_SEARCH &&
        !this.isLoadingComuni &&
        this.elencoComuni.length === 0
       )  {
            this.loadComuniByNome(strInput);
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

  resetForm(){
    this.frmRicercaFirme.setValue({
      nome: "",
      cognome: "",
      comune: "",
      indirizzo: "",
      tipoDocumento: "",
      numeroDocumento: "",
      numeroFoglio: ""
    });
  }
}
