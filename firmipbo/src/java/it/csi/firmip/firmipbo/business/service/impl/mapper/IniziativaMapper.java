/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service.impl.mapper;

import it.csi.firmip.firmipbo.business.service.impl.entity.IniziativaCountEntity;
import it.csi.firmip.firmipbo.business.service.impl.entity.IniziativaEntity;
import it.csi.firmip.firmipbo.dto.Iniziativa;
import it.csi.firmip.firmipbo.dto.TipoIniziativa;

public class IniziativaMapper {

	public static Iniziativa buildFromEntity(IniziativaCountEntity entity) {
		Iniziativa result = new Iniziativa();
		result.setId(entity.getIdIniziativa());
		result.setTipo(new TipoIniziativa(entity.getIdTipoIniziativa(), null));
		result.setTitolo(entity.getTitolo());
		result.setData(entity.getData());
		result.setAnno(entity.getAnno());
		result.setNumeroFirme(entity.getNumeroFirme());
		return result;
	}
	
	public static Iniziativa buildFromEntities(IniziativaCountEntity entity, TipoIniziativa tipoIniziativa) {
		Iniziativa result = new Iniziativa();
		result.setId(entity.getIdIniziativa());
		result.setTipo(tipoIniziativa);
		result.setTitolo(entity.getTitolo());
		result.setData(entity.getData());
		result.setAnno(entity.getAnno());
		result.setNumeroFirme(entity.getNumeroFirme());
		return result;
	}
	
	public static IniziativaEntity buildFromDTO(Iniziativa dto) {
		IniziativaEntity result = new IniziativaEntity();
		result.setIdIniziativa(dto.getId());
		result.setIdTipoIniziativa(dto.getTipo().getId());
		result.setTitolo(dto.getTitolo());
		result.setData(dto.getData());
		result.setAnno(dto.getAnno());
		return result;
	}
}
