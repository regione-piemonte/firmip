/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.firmip.firmipbo.business.service.ComuneService;
import it.csi.firmip.firmipbo.business.service.impl.dao.ComuneDAO;
import it.csi.firmip.firmipbo.business.service.impl.entity.ComuneFilter;
import it.csi.firmip.firmipbo.business.service.impl.mapper.ComuneMapper;
import it.csi.firmip.firmipbo.dto.Comune;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundBusinessException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundDAOException;
import it.csi.firmip.firmipbo.exception.service.ServiceException;
import it.csi.firmip.firmipbo.util.LoggerAccessor;

/**
 * Metodi di business relativi ai comuni
 *
 * @see Comune
 *
 */
@Component
public class ComuneServiceImpl implements ComuneService {

	private static final String CLASS_NAME = "ComuneServiceImpl";
	private static final Logger LOG = LoggerAccessor.getLoggerBusiness();

	public ComuneServiceImpl() {
	}

	@Autowired
	ComuneDAO comuneDAO;

	@Override
	public List<Comune> getElencoComuni() throws BusinessException {
		try {
			List<Comune> result = comuneDAO.findAll().stream()
				.map(ComuneMapper::buildFromEntity)
				.collect(Collectors.toList());
			return result;
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getElencoComuni] Errore generico servizio getElencoComuni",ex);
			throw new ServiceException("Errore generico servizio getElencoComuni");
		}
	}
	
	@Override
	public List<Comune> getElencoComuni(ComuneFilter filter) throws BusinessException {
		try {
			List<Comune> result = comuneDAO.find(filter).stream()
				.map(ComuneMapper::buildFromEntity)
				.collect(Collectors.toList());
			return result;
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getElencoComuni] Errore generico servizio getElencoComuni",ex);
			throw new ServiceException("Errore generico servizio getElencoComuni");
		}
	}
	
	@Override
	public List<Comune> getElencoComuniByIstatRegione(String codiceRegione) throws BusinessException {
		try {
			List<Comune> result = comuneDAO.findByIstatRegione(codiceRegione).stream()
				.map(ComuneMapper::buildFromEntity)
				.collect(Collectors.toList());
			return result;
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getElencoComuniByIstatRegione] Errore generico servizio getElencoComuniByIstatRegione",ex);
			throw new ServiceException("Errore generico servizio getElencoComuniByIstatRegione");
		}
	}
	
	@Override
	public Comune getComuneById(Integer idComune) throws ItemNotFoundBusinessException, BusinessException {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("[" + CLASS_NAME + "::getComuneById] IN idComune = " + idComune);
			}
			return ComuneMapper.buildFromEntity(comuneDAO.findById(idComune));
		} catch (ItemNotFoundDAOException notFoundEx) {
			LOG.error("[" + CLASS_NAME + "::getComuneById] ItemNotFoundDAOException for idComune = " + idComune);
			throw new ItemNotFoundBusinessException();
		} catch (DAOException e) {
			LOG.error("[" + CLASS_NAME + "::getComuneById] DAOException for idComune = " + idComune, e);
			throw new BusinessException("Errore generico servizio getComuneById");
		}
	}

	@Override
	public Comune getComuneByCodiceIstat(String codiceIstat) throws ItemNotFoundBusinessException, BusinessException {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("[" + CLASS_NAME + "::getComuneByCodiceIstat] IN codiceIstat = " + codiceIstat);
			}
			return ComuneMapper.buildFromEntity(comuneDAO.findByCodiceIstat(codiceIstat));
		} catch (ItemNotFoundDAOException notFoundEx) {
			LOG.error("[" + CLASS_NAME + "::getComuneByCodiceIstat] ItemNotFoundDAOException for codiceIstat = " + codiceIstat);
			throw new ItemNotFoundBusinessException();
		} catch (DAOException e) {
			LOG.error("[" + CLASS_NAME + "::getComuneByCodiceIstat] DAOException for codiceIstat = " + codiceIstat, e);
			throw new BusinessException("Errore generico servizio getComuneByCodiceIstat");
		}
	}

	@Override
	public Integer getIdComuneByCodiceIstat(String codiceIstat) throws ItemNotFoundBusinessException, BusinessException {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("[" + CLASS_NAME + "::getIdComuneByCodiceIstat] IN codiceIstat = " + codiceIstat);
			}
			return comuneDAO.findByCodiceIstat(codiceIstat).getIdComune();
		} catch (ItemNotFoundDAOException notFoundEx) {
			LOG.error("[" + CLASS_NAME + "::getIdComuneByCodiceIstat] ItemNotFoundDAOException for codiceIstat = " + codiceIstat);
			throw new ItemNotFoundBusinessException();
		} catch (DAOException e) {
			LOG.error("[" + CLASS_NAME + "::getIdComuneByCodiceIstat] DAOException for codiceIstat = " + codiceIstat, e);
			throw new BusinessException("Errore generico servizio getIdComuneByCodiceIstat");
		}
	}

}
