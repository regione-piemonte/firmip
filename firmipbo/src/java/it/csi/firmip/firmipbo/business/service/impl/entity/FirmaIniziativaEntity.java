/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl.entity;

import java.time.LocalDate;

public class FirmaIniziativaEntity {

	private Integer idFirmaIniziativa;
	private Integer idIniziativa;
	private String cognome;
	private String nome;
	private LocalDate dataNascita;
	private String luogoNascita;
	private String tipoDocumento;
	private String numDocumento;
	private String indirizzo;
	private Integer idComune;
	private Integer numFoglio;
	
	public Integer getIdFirmaIniziativa() {
		return idFirmaIniziativa;
	}
	public void setIdFirmaIniziativa(Integer idFirmaIniziativa) {
		this.idFirmaIniziativa = idFirmaIniziativa;
	}
	public Integer getIdIniziativa() {
		return idIniziativa;
	}
	public void setIdIniziativa(Integer idIniziativa) {
		this.idIniziativa = idIniziativa;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public LocalDate getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getLuogoNascita() {
		return luogoNascita;
	}
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNumDocumento() {
		return numDocumento;
	}
	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public Integer getIdComune() {
		return idComune;
	}
	public void setIdComune(Integer idComune) {
		this.idComune = idComune;
	}
	public Integer getNumFoglio() {
		return numFoglio;
	}
	public void setNumFoglio(Integer numFoglio) {
		this.numFoglio = numFoglio;
	}
	
	@Override
	public String toString() {
		return "FirmaIniziativaEntity [idFirmaIniziativa=" + idFirmaIniziativa + ", idIniziativa=" + idIniziativa
				+ ", cognome=" + cognome + ", nome=" + nome + ", dataNascita=" + dataNascita + ", luogoNascita="
				+ luogoNascita + ", tipoDocumento=" + tipoDocumento + ", numDocumento=" + numDocumento + ", indirizzo="
				+ indirizzo + ", idComune=" + idComune + ", numFoglio=" + numFoglio + "]";
	}

}