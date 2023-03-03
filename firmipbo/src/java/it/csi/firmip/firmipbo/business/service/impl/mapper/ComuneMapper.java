/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service.impl.mapper;

import it.csi.firmip.firmipbo.business.service.impl.entity.ComuneEntity;
import it.csi.firmip.firmipbo.dto.Comune;

public class ComuneMapper {

	public static Comune buildFromEntity(ComuneEntity entity) {
		Comune result = new Comune();
		result.setCodice(entity.getCodiceIstat());
		result.setNome(entity.getNomeComune());
		result.setSiglaProvincia(entity.getSiglaProvincia());
		return result;
	}
	
}
