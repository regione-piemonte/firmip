/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl.entity;

public class TipoDocumentoEntity {

	private String tipoDocumento;
	private String nomeDocumento;

	public TipoDocumentoEntity() {
		super();
	}

	public TipoDocumentoEntity(String tipo, String nome) {
		super();
		this.tipoDocumento = tipo;
		this.nomeDocumento = nome;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}

	@Override
	public String toString() {
		return "TipoDocumentoEntity [tipoDocumento=" + tipoDocumento + ", nomeDocumento=" + nomeDocumento + "]";
	}

}