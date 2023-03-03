/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {Iniziativa} from "../../../../model/firmipbo-data-model";
import {WTableComponent} from "../../../../components/commons/w-table/w-table.component";
import {ResourceHttpService} from "../../../../services/resource-http.service";
import {IniziativeHttpService} from "../../../../services/iniziative-http.service";
import {ITableEvent} from "../../../../components/commons/w-table/table-models";
import {environment} from "../../../../../environments/environment";

@Component({
  selector: 'firmipbo-iniziative-tabella',
  templateUrl: './iniziative-tabella.component.html',
  styleUrls: ['./iniziative-tabella.component.css']
})
export class IniziativeTabellaComponent implements OnInit, OnChanges {
 @ViewChild('dyntab') tabella: WTableComponent ;
 @Input() iniziative : Iniziativa[];
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
        format: '',
        baselink: '',
        keylink: ''
      },
      {key: 'titolo',
        index: 2,
        isSelected: true,
        isChecked: true,
        label: 'Titolo',
        formatted: false,
        type: 'link',
        format: '',
        baselink: '/firme',
        keylink: 'id'
      },
      {key: 'descrizioneTipo',
        index: 3,
        isSelected: true,
        isChecked: true,

        label: 'Tipologia',
        formatted: false,
        type: '',
        format: '',
        baselink: '',
        keylink: ''
      },
      {key: 'anno',
        index: 4,
        isSelected: true,
        isChecked: true,

        label: 'Anno',
        formatted: false,
        type: '',
        format: '',
        baselink: '',
        keylink: ''
      },
      {key: 'data',
      index: 5,
      isSelected: true,
        isChecked: true,

        label: 'Data',
      formatted: true,
      type: 'date',
      format: 'dd/MM/yyyy',
        baselink: '',
        keylink: ''
      },
      {key: 'numeroFirme',
      index: 6,
      isSelected: true,
        isChecked: true,

        label: 'Firme',
      formatted: false,
      type: '',
      format: '',
        baselink: '',
        keylink: ''
      }];



  private  commands =
    [
      {key: 'vediIniziativa',
        index: 1,
        isSelected: false,
        cmd: 'CMD_vediIniziativa',
        icon: 'it-password-visible',
        tooltip: 'Visualizza',
        theme: 'primary'
      },
      {key: 'modifica',
        index: 2,
        isSelected: false,
        cmd: 'CMD_Modifica',
        icon: 'it-settings',
        tooltip: 'Modifica',
        theme: 'primary'
      },
      {key: 'aggiungiFirma',
        index: 3,
        isSelected: false,
        cmd: 'CMD_AggiungiFirma',
        icon: 'it-pencil',
        tooltip: 'Aggiungi firma',
        theme: 'primary'
      },
      {key: 'apriModale',
        index: 4,
        isSelected: false,
        cmd: 'CMD_Elimina',
        icon: 'it-delete',
        tooltip: 'Elimina',
        theme: 'danger'
      }
    ] ;



  constructor() { }

  ngAfterViewInit(): void {

   }

  ngOnInit(): void {
    console.log('init ' + this.iniziative.length);
    console.log('afterViewInit');
    console.log('init ' + this.iniziative.length);


  }
  comando($event: ITableEvent) {
    this.cmdEvent.emit($event);
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log('Change ' + changes);
    if (this.tabella) {
      this.tabella.render(this.headers,this.iniziative, this.commands);
    }
  }

}
