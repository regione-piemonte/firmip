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

import it.csi.firmip.firmipbo.business.service.IniziativaService;
import it.csi.firmip.firmipbo.business.service.TipoIniziativaService;
import it.csi.firmip.firmipbo.business.service.impl.dao.IniziativaDAO;
import it.csi.firmip.firmipbo.business.service.impl.entity.IniziativaCountEntity;
import it.csi.firmip.firmipbo.business.service.impl.entity.IniziativaEntity;
import it.csi.firmip.firmipbo.business.service.impl.mapper.IniziativaMapper;
import it.csi.firmip.firmipbo.dto.Iniziativa;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundBusinessException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundDAOException;
import it.csi.firmip.firmipbo.exception.service.ServiceException;
import it.csi.firmip.firmipbo.util.LoggerAccessor;

/**
 * Metodi di business relativi all'iniziativa
 *
 * @see Iniziativa
 * 
 */
@Component
public class IniziativaServiceImpl implements IniziativaService {

	private static final String CLASS_NAME = "IniziativaServiceImpl";
	private static final Logger LOG = LoggerAccessor.getLoggerBusiness();

	public IniziativaServiceImpl() {
	}

	@Autowired
	IniziativaDAO iniziativaDAO;
	@Autowired
	TipoIniziativaService tipoIniziativaService;

	@Override
	public List<Iniziativa> getElencoIniziative() {
		try {
			List<Iniziativa> result = iniziativaDAO.findAll().stream()
				.map(IniziativaMapper::buildFromEntity)
				.map(i -> completaIniziativa(i))
				.collect(Collectors.toList());
			LOG.info("[" + CLASS_NAME + "::getElencoIniziative] result.size = " + String.valueOf((result!=null)?result.size():"null"));
			return result;
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getElencoIniziative] Errore generico servizio getElencoIniziative", ex);
			throw new ServiceException("Errore generico servizio getElencoIniziative");
		}
	}
	
	private Iniziativa completaIniziativa(Iniziativa iniziativa) {
		iniziativa.setTipo(tipoIniziativaService.getTipoIniziativaById(iniziativa.getTipo().getId()));
		return iniziativa;
	}

	@Override
	public Iniziativa getIniziativaById(Integer idIniziativa) {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("[" + CLASS_NAME + "::getIniziativaById] IN idIniziativa = " + idIniziativa);
			}
			IniziativaCountEntity entity = iniziativaDAO.findById(idIniziativa);
			return IniziativaMapper.buildFromEntities(entity, tipoIniziativaService.getTipoIniziativaById(entity.getIdTipoIniziativa()));
		} catch (ItemNotFoundDAOException notFoundEx) {
			LOG.error("[" + CLASS_NAME + "::getIniziativaById] ItemNotFoundDAOException for idIniziativa = " + idIniziativa);
			throw new ItemNotFoundBusinessException(String.format("Iniziativa %s inesistente", idIniziativa), "FIRMIP-00100");
		} catch (DAOException e) {
			LOG.error("[" + CLASS_NAME + "::getIniziativaById] DAOException for idIniziativa = " + idIniziativa, e);
			throw new BusinessException("Errore generico servizio getIniziativaById");
		}
	}

	@Override
	public Iniziativa createIniziativa(Iniziativa body) {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("[" + CLASS_NAME + "::createIniziativa] IN body = " + body);
			}
			IniziativaEntity entity = IniziativaMapper.buildFromDTO(body);
			Integer id = iniziativaDAO.insert(entity);
			body.setId(id);
			return body;
		} catch (DAOException e) {
			LOG.error("[" + CLASS_NAME + "::createIniziativa] DAOException for body = " + body, e);
			throw new BusinessException("Errore generico servizio createIniziativa");
		}
	}

	@Override
	public Iniziativa updateIniziativa(Iniziativa body) {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("[" + CLASS_NAME + "::updateIniziativa] IN body = " + body);
			}
			IniziativaEntity entity = IniziativaMapper.buildFromDTO(body);
			int rows = iniziativaDAO.update(entity);
			return getIniziativaById(body.getId());
		} catch (ItemNotFoundDAOException notFoundEx) {
			LOG.error("[" + CLASS_NAME + "::updateIniziativa] ItemNotFoundDAOException for idIniziativa = " + body.getId());
			throw new ItemNotFoundBusinessException(String.format("Iniziativa %s inesistente", body.getId()), "FIRMIP-00100");
		} catch (DAOException e) {
			LOG.error("[" + CLASS_NAME + "::updateIniziativa] DAOException for idIniziativa = " + body.getId(), e);
			throw new BusinessException("Errore generico servizio updateIniziativa");
		}
	}

	@Override
	public void deleteIniziativa(Integer idIniziativa) {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("[" + CLASS_NAME + "::deleteIniziativa] IN idIniziativa = " + idIniziativa);
			}
			int rows = iniziativaDAO.delete(idIniziativa);
		} catch (ItemNotFoundDAOException notFoundEx) {
			LOG.error("[" + CLASS_NAME + "::deleteIniziativa] ItemNotFoundDAOException for idIniziativa = " + idIniziativa);
			throw new ItemNotFoundBusinessException(String.format("Iniziativa %s inesistente", idIniziativa), "FIRMIP-00100");
		} catch (DAOException e) {
			LOG.error("[" + CLASS_NAME + "::deleteIniziativa] DAOException for idIniziativa = " + idIniziativa, e);
			throw new BusinessException("Errore generico servizio deleteIniziativa");
		}
	}

}
