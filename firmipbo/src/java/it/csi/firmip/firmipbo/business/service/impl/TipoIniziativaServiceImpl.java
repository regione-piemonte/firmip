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

import it.csi.firmip.firmipbo.business.service.TipoIniziativaService;
import it.csi.firmip.firmipbo.business.service.impl.dao.TipoIniziativaDAO;
import it.csi.firmip.firmipbo.business.service.impl.mapper.TipoIniziativaMapper;
import it.csi.firmip.firmipbo.dto.TipoIniziativa;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundBusinessException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundDAOException;
import it.csi.firmip.firmipbo.exception.service.ServiceException;
import it.csi.firmip.firmipbo.util.LoggerAccessor;

/**
 * Metodi di business relativi alla tipologia di iniziativa
 *
 * @see TipoIniziativa
 * 
 */
@Component
public class TipoIniziativaServiceImpl implements TipoIniziativaService {

	private static final String CLASS_NAME = "TipoIniziativaServiceImpl";
	private static final Logger LOG = LoggerAccessor.getLoggerBusiness();

	public TipoIniziativaServiceImpl() {
	}

	@Autowired
	TipoIniziativaDAO tipoIniziativaDAO;

	@Override
	public List<TipoIniziativa> getElencoTipoIniziativa() {
		try {
			List<TipoIniziativa> result = tipoIniziativaDAO.findAll().stream()
				.map(TipoIniziativaMapper::buildFromEntity)
				.collect(Collectors.toList());
			LOG.info("[" + CLASS_NAME + "::getElencoTipoIniziativa] result.size = " + String.valueOf((result!=null)?result.size():"null"));
			return result;
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getElencoTipoIniziativa] Errore generico servizio getElencoTipoIniziativa", ex);
			throw new ServiceException("Errore generico servizio getElencoTipoIniziativa");
		}
	}
	
	@Override
	public TipoIniziativa getTipoIniziativaById(Integer idTipoIniziativa) {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("[" + CLASS_NAME + "::getTipoIniziativaById] IN idTipoIniziativa = " + idTipoIniziativa);
			}
			return TipoIniziativaMapper.buildFromEntity(tipoIniziativaDAO.findById(idTipoIniziativa));
		} catch (ItemNotFoundDAOException notFoundEx) {
			LOG.error("[" + CLASS_NAME + "::getTipoIniziativaById] ItemNotFoundDAOException for idTipoIniziativa = " + idTipoIniziativa);
			throw new ItemNotFoundBusinessException();
		} catch (DAOException e) {
			LOG.error("[" + CLASS_NAME + "::getTipoIniziativaById] DAOException for idTipoIniziativa = " + idTipoIniziativa, e);
			throw new BusinessException("Errore generico servizio getTipoIniziativaById");
		}
	}

}
