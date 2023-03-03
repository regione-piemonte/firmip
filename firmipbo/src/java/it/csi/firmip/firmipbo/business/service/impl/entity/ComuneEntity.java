/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl.entity;

public class ComuneEntity {

	private Integer idComune;
	private String codiceIstat;
	private String nomeComune;
	private String siglaProvincia;
	
	public Integer getIdComune() {
		return idComune;
	}
	public void setIdComune(Integer idComune) {
		this.idComune = idComune;
	}
	public String getCodiceIstat() {
		return codiceIstat;
	}
	public void setCodiceIstat(String codiceIstat) {
		this.codiceIstat = codiceIstat;
	}
	public String getNomeComune() {
		return nomeComune;
	}
	public void setNomeComune(String nomeComune) {
		this.nomeComune = nomeComune;
	}
	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	
	@Override
	public String toString() {
		return "ComuneEntity [idComune=" + idComune + ", codiceIstat=" + codiceIstat + ", nomeComune=" + nomeComune
				+ ", siglaProvincia=" + siglaProvincia + "]";
	}
	
}
