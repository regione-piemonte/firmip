/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service;

import java.util.List;

import it.csi.firmip.firmipbo.business.service.impl.entity.ComuneFilter;
import it.csi.firmip.firmipbo.dto.Comune;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundBusinessException;

/**
 * Metodi di business relativi ai comuni
 *
 * @see Comune
 *
 */
public interface ComuneService {

	public List<Comune> getElencoComuni() throws BusinessException;
	public List<Comune> getElencoComuni(ComuneFilter filter) throws BusinessException;
	public List<Comune> getElencoComuniByIstatRegione(String codiceRegione) throws BusinessException;
	public Comune getComuneById(Integer idComune) throws ItemNotFoundBusinessException, BusinessException;
	public Comune getComuneByCodiceIstat(String codiceIstat) throws ItemNotFoundBusinessException, BusinessException;
	public Integer getIdComuneByCodiceIstat(String codiceIstat) throws ItemNotFoundBusinessException, BusinessException;
	
}
