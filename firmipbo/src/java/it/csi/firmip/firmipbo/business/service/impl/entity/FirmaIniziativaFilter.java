/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl.entity;

import java.util.Date;
import java.util.Optional;

public class FirmaIniziativaFilter {

	private Integer idFirmaIniziativa;
	private Integer idIniziativa;
	private String cognome;
	private String nome;
	private Date dataNascita;
	private String luogoNascita;
	private String tipoDocumento;
	private String numDocumento;
	private String indirizzo;
	private Integer idComune;
	private String codiceIstatComune;
	private Integer numFoglio;
	private boolean ricercaStringheEsatte;
	
	public FirmaIniziativaFilter() {
		super();
	}
	public FirmaIniziativaFilter(Integer idIniziativa) {
		super();
		this.idIniziativa = idIniziativa;
	}

	public Optional<Integer> getIdIniziativa() {
		return Optional.ofNullable(idIniziativa);
	}
	public void setIdIniziativa(Integer idIniziativa) {
		this.idIniziativa = idIniziativa;
	}
	public Optional<Integer> getIdFirmaIniziativa() {
		return Optional.ofNullable(idFirmaIniziativa);
	}
	public void setIdFirmaIniziativa(Integer idFirmaIniziativa) {
		this.idFirmaIniziativa = idFirmaIniziativa;
	}
	public Optional<String> getCognome() {
		return Optional.ofNullable(cognome);
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public Optional<String> getNome() {
		return Optional.ofNullable(nome);
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Optional<Date> getDataNascita() {
		return Optional.ofNullable(dataNascita);
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public Optional<String> getLuogoNascita() {
		return Optional.ofNullable(luogoNascita);
	}
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	public Optional<String> getTipoDocumento() {
		return Optional.ofNullable(tipoDocumento);
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public Optional<String> getNumDocumento() {
		return Optional.ofNullable(numDocumento);
	}
	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}
	public Optional<String> getIndirizzo() {
		return Optional.ofNullable(indirizzo);
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public Optional<Integer> getIdComune() {
		return Optional.ofNullable(idComune);
	}
	public void setIdComune(Integer idComune) {
		this.idComune = idComune;
	}
	public Optional<String> getCodiceIstatComune() {
		return Optional.ofNullable(codiceIstatComune);
	}
	public void setCodiceIstatComune(String codiceIstatComune) {
		this.codiceIstatComune = codiceIstatComune;
	}
	public Optional<Integer> getNumFoglio() {
		return Optional.ofNullable(numFoglio);
	}
	public void setNumFoglio(Integer numFoglio) {
		this.numFoglio = numFoglio;
	}
	public boolean isRicercaStringheEsatte() {
		return ricercaStringheEsatte;
	}
	public void setRicercaStringheEsatte(boolean ricercaStringheEsatte) {
		this.ricercaStringheEsatte = ricercaStringheEsatte;
	}
	
	@Override
	public String toString() {
		return "FirmaIniziativaFilter [idFirmaIniziativa=" + idFirmaIniziativa + ", idIniziativa=" + idIniziativa
				+ ", cognome=" + cognome + ", nome=" + nome + ", dataNascita=" + dataNascita + ", luogoNascita="
				+ luogoNascita + ", tipoDocumento=" + tipoDocumento + ", numDocumento=" + numDocumento + ", indirizzo="
				+ indirizzo + ", idComune=" + idComune + ", codiceIstatComune=" + codiceIstatComune
				+ ", numFoglio=" + numFoglio + ", ricercaStringheEsatte=" + ricercaStringheEsatte + "]";
	}

}