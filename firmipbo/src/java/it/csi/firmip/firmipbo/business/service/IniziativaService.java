/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service;

import java.util.List;

import it.csi.firmip.firmipbo.dto.Iniziativa;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundBusinessException;

/**
 * Metodi di business relativi all'iniziativa
 *
 * @see Iniziativa
 * 
 */
public interface IniziativaService {

	public List<Iniziativa> getElencoIniziative() throws BusinessException;
	public Iniziativa getIniziativaById(Integer idIniziativa) throws ItemNotFoundBusinessException, BusinessException;
	public Iniziativa createIniziativa(Iniziativa body) throws BusinessException;
	public Iniziativa updateIniziativa(Iniziativa body) throws ItemNotFoundBusinessException, BusinessException;
	public void deleteIniziativa(Integer idIniziativa) throws ItemNotFoundBusinessException, BusinessException;
	
}
