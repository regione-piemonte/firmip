/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {WTableComponent} from "../../../../components/commons/w-table/w-table.component";
import {Firma} from "../../../../model/firmipbo-data-model";
import {environment} from "../../../../../environments/environment";
import {ITableEvent} from "../../../../components/commons/w-table/table-models";

@Component({
  selector: 'firmipbo-firme-tabella',
  templateUrl: './firme-tabella.component.html',
  styleUrls: ['./firme-tabella.component.css']
})
export class FirmeTabellaComponent implements OnInit, OnChanges {
@ViewChild('dyntab') tabella: WTableComponent ;
@Input() firme : Firma[];
@Output('cmdEvent') cmdEvent = new EventEmitter();
  page: number = 1;
  pageSize: number = environment.pageSize;


  private headers =
    [
      {key: 'id',
        index: 1,
        isSelected: false,
        isChecked: false,
        label: 'Id',
        formatted: false,
        type: '',
        format: ''
      },
      {key: 'idIniziativa',
        index: 2,
        isSelected: false,
        isChecked: false,

        label: 'idIniziativa',
        formatted: false,
        type: '',
        format: ''
      },
      {key: 'cognome',
        index: 3,
        isSelected: true,
        isChecked: true,

        label: 'Cognome',
        formatted: false,
        type: '',
        format: ''
      },
      {key: 'nome',
        index: 4,
        isSelected: true,
        isChecked: true,
        label: 'Nome',
        formatted: false,
        type: '',
        format: ''
      },
      {key: 'dataNascita',
        index: 5,
        isSelected: true,
        isChecked: false,
        label: 'Data di nascita',
        formatted: false,
        type: 'date',
        format: 'dd/MM/yyyy'
      },
      {key: 'luogoNascita',
        index: 6,
        isSelected: true,
        isChecked: false,
        label: 'Luogo di Nascita',
        formatted: false,
        type: '',
        format: ''
      },
      {
        key: 'tipoDocumento',
        index: 7,
        isSelected: true,
        isChecked: true,
        label: 'Tipo Documento',
        formatted: false,
        type: '',
        format: ''
      },
      {key: 'numDocumento',
        index: 8,
        isSelected: true,
        isChecked: false,
        label: 'Numero Documento',
        formatted: false,
        type: '',
        format: ''
      },
      {key: 'numFoglio',
        index: 9,
        isSelected: true,
        isChecked: true,
        label: 'Foglio N.',
        formatted: false,
        type: '',
        format: ''
      },
      {key: 'indirizzo',
        index: 10,
        isSelected: true,
        isChecked: true,
        label: 'Indirizzo',
        formatted: false,
        type: '',
        format: ''
      },
      {key: 'comune',
        index: 11,
        isSelected: true,
        isChecked: true,
        label: 'Comune',
        formatted: false,
        type: 'comune',
        format: ''
      },
    ];



  private  commands =
    [
      {key: 'modifica',
        index: 1,
        isSelected: false,
        cmd: 'CMD_Modifica',
        icon: 'it-settings',
        tooltip: 'Modifica',
        theme: 'primary'
      },
      {key: 'apriModale',
        index: 2,
        isSelected: false,
        cmd: 'CMD_Elimina',
        icon: 'it-delete',
        tooltip: 'Elimina',
        theme: 'danger'
      }
    ] ;


  constructor() { }

  ngOnInit(): void {
    console.log('init ' + this.firme.length);
    console.log('afterViewInit');
    console.log('init ' + this.firme.length);

  }
  comando($event: ITableEvent) {
    this.cmdEvent.emit($event);
    //  console.log(`${$event.cmd}`);
    //  console.log(JSON.stringify($event.data));
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.tabella) {
      this.tabella.render(this.headers,this.firme, this.commands);
    }
  }

}
