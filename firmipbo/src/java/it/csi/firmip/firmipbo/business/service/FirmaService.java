/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service;

import java.util.List;

import it.csi.firmip.firmipbo.business.service.impl.entity.FirmaIniziativaFilter;
import it.csi.firmip.firmipbo.dto.Firma;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundBusinessException;

/**
 * Metodi di business relativi alle firme
 *
 * @see Firma
 * 
 */
public interface FirmaService {

	public List<Firma> getFirme(FirmaIniziativaFilter filter, String sort) throws BusinessException;
	public List<Firma> getFirmeByIdIniziativa(Integer idIniziativa, String sort) throws BusinessException;
	public List<Firma> getFirmeByIdIniziativa(Integer idIniziativa) throws BusinessException;
	public int getCountFirmeByIdIniziativa(Integer idIniziativa) throws ItemNotFoundBusinessException, BusinessException;
	public Firma getFirmaById(Integer idFirma) throws ItemNotFoundBusinessException, BusinessException;
	public Firma createFirma(Firma body) throws BusinessException;
	public Firma updateFirma(Firma body) throws ItemNotFoundBusinessException, BusinessException;
	public void deleteFirma(Integer idFirma) throws ItemNotFoundBusinessException, BusinessException;
	public String getMaxNumFoglio(Integer idIniziativa) throws ItemNotFoundBusinessException, BusinessException;
	
}
