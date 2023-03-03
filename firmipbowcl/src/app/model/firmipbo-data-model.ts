/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
export interface TipoIniziativa {
  id: number;
  descrizione: string;
}


export interface Iniziativa {
  id: number;
  tipo: TipoIniziativa;
  descrizioneTipo: string;
  titolo: string;
  data: Date;
  anno: number;
  numeroFirme: number;
}



export interface Comune {
  codice: string;
  nome: string;
  siglaProvincia: string;
}



export interface IBreadcrumb {
  label: string;
  url: string;
  active: boolean,
}

// firme
export interface Firma {
  id: number;
  idIniziativa: number;
  cognome: string;
  nome: string;
  dataNascita: Date ;
  luogoNascita: string;
  tipoDocumento: TipoDocumento;
  numDocumento: string;
  indirizzo: string;
  comune: Comune;
  numFoglio: string;
}

export interface TipoDocumento {
  tipo: string;
  nome: string;
}
