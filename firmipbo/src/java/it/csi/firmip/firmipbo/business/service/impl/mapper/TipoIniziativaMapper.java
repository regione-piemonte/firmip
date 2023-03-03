/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service.impl.mapper;

import it.csi.firmip.firmipbo.business.service.impl.entity.TipoIniziativaEntity;
import it.csi.firmip.firmipbo.dto.TipoIniziativa;

public class TipoIniziativaMapper {

	public static TipoIniziativa buildFromEntity(TipoIniziativaEntity entity) {
		TipoIniziativa result = new TipoIniziativa();
		result.setId(entity.getIdTipoIniziativa());
		result.setDescrizione(entity.getDesTipoIniziativa());
		return result;
	}

}
