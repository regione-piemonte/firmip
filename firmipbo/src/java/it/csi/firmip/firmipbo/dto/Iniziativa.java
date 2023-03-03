/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Iniziativa {

	private Integer id;
	private TipoIniziativa tipo;
	private String titolo;
	private Date data;
	private Integer anno;
	private Integer numeroFirme;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public TipoIniziativa getTipo() {
		return tipo;
	}

	public void setTipo(TipoIniziativa tipo) {
		this.tipo = tipo;
	}

	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "CET")
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
	
	public Integer getNumeroFirme() {
		return numeroFirme;
	}
	public void setNumeroFirme(Integer numeroFirme) {
		this.numeroFirme = numeroFirme;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Iniziativa {\n");
		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
		sb.append("    titolo: ").append(toIndentedString(titolo)).append("\n");
		sb.append("    data: ").append(toIndentedString(data)).append("\n");
		sb.append("    anno: ").append(toIndentedString(anno)).append("\n");
		sb.append("    numeroFirme: ").append(toIndentedString(numeroFirme)).append("\n");
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