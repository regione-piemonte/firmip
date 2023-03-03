/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl.entity;

import java.util.Date;

public class IniziativaEntity {

	private Integer idIniziativa;
	private Integer idTipoIniziativa;
	private String titolo;
	private Date data;
	private Integer anno;
	
	public Integer getIdIniziativa() {
		return idIniziativa;
	}
	public void setIdIniziativa(Integer idIniziativa) {
		this.idIniziativa = idIniziativa;
	}
	public Integer getIdTipoIniziativa() {
		return idTipoIniziativa;
	}
	public void setIdTipoIniziativa(Integer idTipoIniziativa) {
		this.idTipoIniziativa = idTipoIniziativa;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Integer getAnno() {
		return anno;
	}
	public void setAnno(Integer anno) {
		this.anno = anno;
	}
	
	@Override
	public String toString() {
		return "IniziativaEntity [idIniziativa=" + idIniziativa + ", idTipoIniziativa=" + idTipoIniziativa + ", titolo="
				+ titolo + ", data=" + data + ", anno=" + anno + "]";
	}
	
}