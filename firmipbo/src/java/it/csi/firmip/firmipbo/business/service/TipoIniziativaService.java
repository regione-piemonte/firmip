/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service;

import java.util.List;

import it.csi.firmip.firmipbo.dto.TipoIniziativa;

/**
 * Metodi di business relativi alla tipologia di iniziativa
 *
 * @see TipoIniziativa
 * 
 */
public interface TipoIniziativaService {

	public List<TipoIniziativa> getElencoTipoIniziativa();
	public TipoIniziativa getTipoIniziativaById(Integer idTipoIniziativa);
	
}
