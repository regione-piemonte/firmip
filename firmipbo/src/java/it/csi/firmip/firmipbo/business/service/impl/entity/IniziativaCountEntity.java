/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl.entity;

public class IniziativaCountEntity extends IniziativaEntity {

	private Integer numeroFirme;
	
	public Integer getNumeroFirme() {
		return numeroFirme;
	}
	public void setNumeroFirme(Integer numeroFirme) {
		this.numeroFirme = numeroFirme;
	}
	
	@Override
	public String toString() {
		return "IniziativaCountEntity [idIniziativa=" + getIdIniziativa() + ", idTipoIniziativa=" + getIdTipoIniziativa() + 
			", titolo=" + getTitolo() + ", data=" + getData() + ", anno=" + getAnno() + ", numeroFirme=" + numeroFirme + "]";
	}
	
}