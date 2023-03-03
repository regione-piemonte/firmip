/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service.impl.mapper;

import it.csi.firmip.firmipbo.business.service.impl.entity.TipoDocumentoEntity;
import it.csi.firmip.firmipbo.dto.TipoDocumento;

public class TipoDocumentoMapper {

	public static TipoDocumento buildFromEntity(TipoDocumentoEntity entity) {
		TipoDocumento result = new TipoDocumento();
		result.setTipo(entity.getTipoDocumento());
		result.setNome(entity.getNomeDocumento());
		return result;
	}
	
}
