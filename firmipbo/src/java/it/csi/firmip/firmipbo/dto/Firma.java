/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Firma {

	private Integer id;
	private Integer idIniziativa;
	private String cognome;
	private String nome;
	private LocalDate dataNascita;
	private String luogoNascita;
	private String tipoDocumento;
	private String numDocumento;
	private String indirizzo;
	private Comune comune;
	private Integer numFoglio;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "CET")
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
	public Comune getComune() {
		return comune;
	}
	public void setComune(Comune comune) {
		this.comune = comune;
	}
	public Integer getNumFoglio() {
		return numFoglio;
	}
	public void setNumFoglio(Integer numFoglio) {
		this.numFoglio = numFoglio;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Firma {\n");
		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    idIniziativa: ").append(toIndentedString(idIniziativa)).append("\n");
		sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
		sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
		sb.append("    dataNascita: ").append(toIndentedString(dataNascita)).append("\n");
		sb.append("    luogoNascita: ").append(toIndentedString(luogoNascita)).append("\n");
		sb.append("    tipoDocumento: ").append(toIndentedString(tipoDocumento)).append("\n");
		sb.append("    numDocumento: ").append(toIndentedString(numDocumento)).append("\n");
		sb.append("    indirizzo: ").append(toIndentedString(indirizzo)).append("\n");
		sb.append("    comune: ").append(toIndentedString(comune)).append("\n");
		sb.append("    numFoglio: ").append(toIndentedString(numFoglio)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	private String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

}