/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import {Iniziativa, TipoIniziativa} from 'src/app/model/firmipbo-data-model';
import { UtilityService } from 'src/app/services/utility.service';
import {IniziativeHttpService} from "../../../../services/iniziative-http.service";
import {TipoIniziativaHttpService} from "../../../../services/tipi-iniziative-http.service";
import {MsgNotificationService} from "../../../../services/msg-notification.service";
import * as _ from "lodash"
import {NOTIFY_ERROR_MESSAGE_AND_INFO} from "../../../../model/firmipbo-data-fe";

enum CMD_TYPE {
  UPDATE,
  INSERT
}



@Component({
  selector: 'iniziative-update',
  templateUrl: './iniziative-update.component.html',
  styleUrls: ['./iniziative-update.component.css']
})



export class IniziativeUpdateComponent implements OnInit {
  customerForm: FormGroup;
  preSubmit: boolean = true;
  iniziativaDaMod: Iniziativa;
  myType: string;
  elencoTipo: TipoIniziativa[];
  cmd: CMD_TYPE;
  titoloForm: string;
  labelBtnAzione = "Salva"
  get f() {
    return this.customerForm.controls as any;
  }
  constructor(  private formBuilder: FormBuilder,
                private router: Router,
                private utility: UtilityService,
                private route: ActivatedRoute,
                private iniziativeHttpService: IniziativeHttpService,
                private tipoIniziativaHttpService: TipoIniziativaHttpService,
                private msgNotifocationService: MsgNotificationService
  ) {
    this.elencoTipo = [];
    this.customerForm = this.formBuilder.group({
      type: ['', Validators.required],
      titolo: ['', Validators.required],
      data: [''],
      anno: ['', Validators.compose([  Validators.min(1999), Validators.max(2999)])],

    });
  }
  submitForm() {
    this.preSubmit = false;
    if(this.customerForm.valid){
      console.log('submit');
      console.log(this.customerForm.value.type);
      var myIniz: Iniziativa;
      myIniz = this.buildIniziativa(this.customerForm.value, this.iniziativaDaMod);
      console.log(myIniz);
      switch (this.cmd) {
        case CMD_TYPE.INSERT:
          this.iniziativeHttpService.add(myIniz).subscribe( (response) => {
              this.msgNotifocationService.sendMsgSuccess(this.MsgNotifica, null);
              this.indietroBtn();
            },
            (error) => {
              console.log(error);
              this.msgNotifocationService.sendMsgError(NOTIFY_ERROR_MESSAGE_AND_INFO.ERROR_TYPE_GENERICO, null);
              this.indietroBtn();
            }
          );
          break;

        case CMD_TYPE.UPDATE:
          this.iniziativeHttpService.update(myIniz).subscribe( (response) => {
              this.msgNotifocationService.sendMsgSuccess(this.MsgNotifica, null);
              this.indietroBtn();
            },
            (error) => {
              this.msgNotifocationService.sendMsgError(error, null);
              this.indietroBtn();
            }
          );
          break;
      }

      this.customerForm.reset();
    }
  }


  get MsgNotifica (): string {
    return this.cmd === CMD_TYPE.INSERT ? 'Iniziativa inserita con successo' : 'Iniziativa modificata con successo';
  }


  buildIniziativa(customerForm: FormGroup, iniziativaDaModificare: Iniziativa): Iniziativa {
    const locData = new Date(customerForm['data']);
    const result: Iniziativa = {
      id: (iniziativaDaModificare && iniziativaDaModificare.id) ? iniziativaDaModificare.id : null,
      tipo: { id: Number(customerForm['type']), descrizione: customerForm['type'] },
      descrizioneTipo: '',
      titolo: customerForm['titolo'],
      data: locData,
      anno: Number(customerForm['anno']) !== 0 ? Number(customerForm['anno']) : null,
      numeroFirme: 0,
    }
    return result;
  }

  selectType($event: Event) {
    console.log(JSON.stringify($event));
  }


  ngOnInit(): void {

    this.tipoIniziativaHttpService.getListTipi().subscribe((elenco) => {
      this.elencoTipo = elenco;
      if (this.route.data !== null) {
        this.cmd = CMD_TYPE.UPDATE;
        this.route.data.subscribe(data => {
          if (!_.isEmpty(data['info'])) {
            this.titoloForm = "Modifica iniziativa";
            this.labelBtnAzione = "Salva"
            this.iniziativaDaMod = data['info'];
              if (this.iniziativaDaMod !== null)
                this.customerForm.setValue({
                  anno: this.iniziativaDaMod.anno !== undefined ? this.iniziativaDaMod.anno : '',
                  titolo: this.iniziativaDaMod.titolo !== undefined ? this.iniziativaDaMod.titolo : '',
                  data: this.iniziativaDaMod.data !== undefined ? this.iniziativaDaMod.data : '',
                  type: this.iniziativaDaMod.tipo.id !== undefined ? this.iniziativaDaMod.tipo.id : '',
                });
                this.myType = this.iniziativaDaMod.tipo.id.toString();
                document.getElementById("lbl-tit").classList.add("active");
                document.getElementById("lbl-anno").classList.add("active");
                document.getElementById("buttonUpdate").innerText = "Salva";
          } else {
            // INSERT
            this.titoloForm = "Inserisci nuova iniziativa"
            this.cmd = CMD_TYPE.INSERT;
            this.labelBtnAzione = "Inserisci";
          }
        });
      }
    });
  }

  indietroBtn(){
    if(this.iniziativaDaMod != null && this.iniziativaDaMod.titolo != null && this.iniziativaDaMod.titolo.length > 0)
      this.utility.changeBreadcrumb(this.router, 'modifica-iniziativa', this.iniziativaDaMod.titolo ,'Mod');
    this.router.navigate(['home']);
  }

  ngOnDestroy(): void{
    if(this.iniziativaDaMod != null && this.iniziativaDaMod.titolo != null && this.iniziativaDaMod.titolo.length > 0)
      this.utility.changeBreadcrumb(this.router, 'modifica-iniziativa', this.iniziativaDaMod.titolo ,'Mod');
  }
}
