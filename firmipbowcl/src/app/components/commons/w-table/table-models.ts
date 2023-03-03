/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
export interface ITableHeader {
  key: string; // rif. campo record
  label: string;
  index: number;
  isSelected: boolean;
  isChecked: boolean;
  formatted: boolean;
  type: string;
  format: string;
  baselink?: string;
  keylink?: string;
}

export interface ITableCmd {
  index: number
  key: string;
  cmd: string;
  icon: any;
  theme: string;
  tooltip: string;
}


export interface IDynamicTable {
  headers: ITableHeader[];
  data: any[];
  cmd: ITableCmd[];
}

// evento comando tabella
export interface ITableEvent {
  cmd: string;
  data: any;
}
