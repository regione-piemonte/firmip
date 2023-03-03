/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl.entity;

import java.util.Optional;

public class ComuneFilter {

	private Integer idComune;
	private String istatComune;
	private String nomeComune;
	private String siglaProvincia;
	private String istatRegione;
	private Long idIniziativa;
	
	public Optional<Integer> getIdComune() {
		return Optional.ofNullable(idComune);
	}
	public void setIdComune(Integer idComune) {
		this.idComune = idComune;
	}
	public Optional<String> getIstatComune() {
		return Optional.ofNullable(istatComune);
	}
	public void setIstatComune(String istatComune) {
		this.istatComune = istatComune;
	}
	public Optional<String> getNomeComune() {
		return Optional.ofNullable(nomeComune);
	}
	public void setNomeComune(String nomeComune) {
		this.nomeComune = nomeComune;
	}
	public Optional<String> getSiglaProvincia() {
		return Optional.ofNullable(siglaProvincia);
	}
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	public Optional<String> getIstatRegione() {
		return Optional.ofNullable(istatRegione);
	}
	public void setIstatRegione(String istatRegione) {
		this.istatRegione = istatRegione;
	}
	public Optional<Long> getIdIniziativa() {
		return Optional.ofNullable(idIniziativa);
	}
	public void setIdIniziativa(Long idIniziativa) {
		this.idIniziativa = idIniziativa;
	}
	
}
