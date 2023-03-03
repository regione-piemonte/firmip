/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl.entity;

public class AuditEntity {
	
	public enum EnumOperazione {
		LOGIN,
		LOGOUT,
		READ,
		INSERT,
		UPDATE,
		DELETE
	}
	
	private String idApp;
	private String ipAddress;
	private String utente;
	private EnumOperazione operazione;
	private String oggettoOperazione;
	private String keyOperazione;
	
	public AuditEntity(String ipAddress, String utente, EnumOperazione operazione, String oggettoOperazione, String keyOperazione) {
		super();
		this.ipAddress = ipAddress;
		this.utente = utente;
		this.operazione = operazione;
		this.oggettoOperazione = oggettoOperazione;
		this.keyOperazione = keyOperazione;
	}
	
	public String getIdApp() {
		return idApp;
	}
	public void setIdApp(String idApp) {
		this.idApp = idApp;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getUtente() {
		return utente;
	}
	public void setUtente(String utente) {
		this.utente = utente;
	}
	public EnumOperazione getOperazione() {
		return operazione;
	}
	public void setOperazione(EnumOperazione operazione) {
		this.operazione = operazione;
	}
	public String getOggettoOperazione() {
		return oggettoOperazione;
	}
	public void setOggettoOperazione(String oggettoOperazione) {
		this.oggettoOperazione = oggettoOperazione;
	}
	public String getKeyOperazione() {
		return keyOperazione;
	}
	public void setKeyOperazione(String keyOperazione) {
		this.keyOperazione = keyOperazione;
	}

	@Override
	public String toString() {
		return "AuditEntity [idApp=" + idApp + ", ipAddress=" + ipAddress + ", utente=" + utente + ", operazione="
				+ operazione + ", oggettoOperazione=" + oggettoOperazione + ", keyOperazione=" + keyOperazione + "]";
	}
	
}
