/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
/*
 Contiene le interfacce dei model
 utilizzati all'interno della applicazione
 */

import {Comune, Firma, TipoDocumento} from "./firmipbo-data-model";
import * as moment from "moment";

export enum TYPE_MSG_NOTIFICA {
  SUCCCESS = 'success',
  ERROR = 'error',
  WARNING = 'warning'
}

export enum NOTIFY_ERROR_MESSAGE_AND_INFO {
  ERROR_TYPE_FIRMA_DUPLICATA = "Firma Duplicata",
  ERROR_MESSAGE_FIRMA_DUPLICATA = "Questa persona ha già firmato per questa iniziativa. Verifica che cognome, nome, luogo e data di nascita siano corretti",
  WARNING_TYPE_FIRMA_OMONIMA = "Firma Omonima",
  WARNING_MESSAGE_FIRMA_OMONIMA = "Esiste già una firma con questo cognome e nome per l'iniziativa ",
  SUCCESS_MESSAGE_INSERT_FIRMA = "Firma inserita con successo",
  SUCCESS_MESSAGE_MODIFY_FIRMA = "Firma modificata con successo",
  ERROR_TYPE_MINORENNE = "I minorenni non possono firmare",
  ERROR_TYPE_GENERICO= "SALVATAGGIO NON RIUSCITO",
  ERROR_TYPE_GENERICO_DETAIL = "Errore imprevisto durante il salvataggio. Riprova nuovamente",
  ERROR_TYPE_SELEZIONE_COMUNE = "Il campo comune non è stato selezionato correttamente",
}

export interface MsgNotifica {
  type: TYPE_MSG_NOTIFICA;
  message: string;
  extraMessage: string;
}

export interface IDialogConfig {
  tipo: DlgType
  titolo: string;
  messaggio: string;
  cancelVisible: boolean;
  okLabel: string;
  cancelLabel: string;
  // azione eseguita quando utente preme ok
  okHandler: () => void;

}

export enum DlgType {
  INFO,
  WARNING,
  ERROR,
  QUESTION
}

export interface CriterioRicercaFirme {
  idIniziativa: number;
  cognome: string;
  nome: string;
  indirizzo: string;
  codiceComune: string;
  tipoDocumento: string;
  numDocumento: string;
  numFoglio: number;
  ricercaStringheEsatte: boolean;
}


export class RicercaFirme {
  public cognome: string;
  public nome: string;
  public indirizzo: string;
  public codiceComune: TipoComune;
  public tipoDocumento: TipoDocRicercaFirm;
  public numeroDocumento: string;
  public numeroFoglio: number;

  constructor() {
  }
}

export class TipoDocRicercaFirm implements TipoDocumento {
  public nome: string;
  public tipo: string;

  constructor() {
  }

}

export class TipoComune implements Comune {
  public codice: string;
  public nome: string;
  public siglaProvincia: string;

  constructor() {
  }
}

export class EsportaFirme {
  cognome: string;
  nome: string;
  dataNascita: string;
  luogoNascita: string;
  tipoDocumento: TipoDocumento;
  numDocumento: string;
  indirizzo: string;
  comune: string;
  numFoglio: string;

  constructor(firma: Firma) {
    this.cognome = firma.cognome !== undefined ? firma.cognome : null;
    this.nome = firma.nome !== undefined ? firma.nome : null;
    this.dataNascita = firma.dataNascita !== undefined ? moment(firma.dataNascita).format("DD/MM/YYYY") : null;
    this.luogoNascita = firma.luogoNascita !== undefined ? firma.luogoNascita : null;
    this.tipoDocumento = firma.tipoDocumento !== undefined ? firma.tipoDocumento : null;
    this.numDocumento = firma.numDocumento !== undefined ? firma.numDocumento : null;
    this.indirizzo = firma.indirizzo !== undefined ? firma.indirizzo : null;
    this.comune = firma.comune !== undefined ? firma.comune.nome : null;
    this.numFoglio = firma.numFoglio !== undefined ? firma.numFoglio : null;
  }
}


export interface User {
  cognome: string,
  nome: string,
  codFisc: string
}



