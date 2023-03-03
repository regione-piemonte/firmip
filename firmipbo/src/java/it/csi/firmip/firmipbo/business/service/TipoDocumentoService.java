/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service;

import java.util.List;

import it.csi.firmip.firmipbo.dto.TipoDocumento;

/**
 * Metodi di business relativi ai tipi di documento
 *
 * @see TipoDocumento
 *
 */
public interface TipoDocumentoService {

	public List<TipoDocumento> getElencoTipoDocumento();
	public TipoDocumento getTipoDocumentoByTipo(String tipo);
	
}
