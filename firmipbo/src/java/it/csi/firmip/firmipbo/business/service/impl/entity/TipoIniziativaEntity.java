/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl.entity;

public class TipoIniziativaEntity {

	private Integer idTipoIniziativa;
	private String desTipoIniziativa;

	public TipoIniziativaEntity() {
		super();
	}

	public TipoIniziativaEntity(Integer id, String descrizione) {
		super();
		this.idTipoIniziativa = id;
		this.desTipoIniziativa = descrizione;
	}

	public Integer getIdTipoIniziativa() {
		return this.idTipoIniziativa;
	}

	public void setIdTipoIniziativa(Integer id) {
		this.idTipoIniziativa = id;
	}

	public String getDesTipoIniziativa() {
		return this.desTipoIniziativa;
	}

	public void setDesTipoIniziativa(String descrizione) {
		this.desTipoIniziativa = descrizione;
	}

	@Override
	public String toString() {
		return "TipoIniziativaEntity [idTipoIniziativa=" + idTipoIniziativa + ", desTipoIniziativa=" + desTipoIniziativa + "]";
	}

}