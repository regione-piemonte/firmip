/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service.impl.mapper;

import it.csi.firmip.firmipbo.business.service.impl.entity.ComuneEntity;
import it.csi.firmip.firmipbo.business.service.impl.entity.FirmaIniziativaEntity;
import it.csi.firmip.firmipbo.dto.Comune;
import it.csi.firmip.firmipbo.dto.Firma;

public class FirmaMapper {

	//
	// Firma
	public static Firma buildFromEntity(FirmaIniziativaEntity entity) {
		Firma result = new Firma();
		result.setId(entity.getIdFirmaIniziativa());
		result.setIdIniziativa(entity.getIdIniziativa());
		result.setCognome(entity.getCognome());
		result.setNome(entity.getNome());
		result.setDataNascita(entity.getDataNascita());
		result.setLuogoNascita(entity.getLuogoNascita());
		result.setTipoDocumento(entity.getTipoDocumento());
		result.setNumDocumento(entity.getNumDocumento());
		result.setIndirizzo(entity.getIndirizzo());
		result.setComune(null);
		result.setNumFoglio(entity.getNumFoglio());
		return result;
	}
	
	public static Firma buildFromEntities(FirmaIniziativaEntity entity, Comune comune) {
		Firma result = buildFromEntity(entity);
		result.setComune(comune);
		return result;
	}
	
	//
	// FirmaIniziativaEntity
	public static FirmaIniziativaEntity buildFromDTO(Firma dto) {
		FirmaIniziativaEntity result = new FirmaIniziativaEntity();
		result.setIdFirmaIniziativa(dto.getId());
		result.setIdIniziativa(dto.getIdIniziativa());
		result.setCognome(dto.getCognome());
		result.setNome(dto.getNome());
		result.setDataNascita(dto.getDataNascita());
		result.setLuogoNascita(dto.getLuogoNascita());
		result.setTipoDocumento(dto.getTipoDocumento());
		result.setNumDocumento(dto.getNumDocumento());
		result.setIndirizzo(dto.getIndirizzo());
		result.setIdComune(null);
		result.setNumFoglio(dto.getNumFoglio());
		return result;
	}
	
	public static FirmaIniziativaEntity buildFromDTO(Firma dto, ComuneEntity comuneE) {
		FirmaIniziativaEntity result = buildFromDTO(dto);
		result.setIdComune(comuneE.getIdComune());
		return result;
	}
	
	public static FirmaIniziativaEntity buildFromDTO(Firma dto, Integer idComune) {
		FirmaIniziativaEntity result = buildFromDTO(dto);
		result.setIdComune(idComune);
		return result;
	}
}
