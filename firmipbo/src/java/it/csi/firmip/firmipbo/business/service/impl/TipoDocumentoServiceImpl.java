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

import it.csi.firmip.firmipbo.business.service.TipoDocumentoService;
import it.csi.firmip.firmipbo.business.service.impl.dao.TipoDocumentoDAO;
import it.csi.firmip.firmipbo.business.service.impl.mapper.TipoDocumentoMapper;
import it.csi.firmip.firmipbo.dto.TipoDocumento;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundBusinessException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundDAOException;
import it.csi.firmip.firmipbo.exception.service.ServiceException;
import it.csi.firmip.firmipbo.util.LoggerAccessor;

/**
 * Metodi di business relativi ai tipi di documento
 *
 * @see TipoDocumento
 *
 */
@Component
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

	private static final String CLASS_NAME = "TipoDocumentoServiceImpl";
	private static final Logger LOG = LoggerAccessor.getLoggerBusiness();

	public TipoDocumentoServiceImpl() {
	}

	@Autowired
	TipoDocumentoDAO tipoDocumentoDAO;

	@Override
	public List<TipoDocumento> getElencoTipoDocumento() {
		try {
			return tipoDocumentoDAO.findAll().stream()
				.map(TipoDocumentoMapper::buildFromEntity)
				.collect(Collectors.toList());
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getElencoTipoDocumento] Errore generico servizio getElencoTipoDocumento", ex);
			throw new ServiceException("Errore generico servizio getElencoTipoDocumento");
		}
	}
	
	@Override
	public TipoDocumento getTipoDocumentoByTipo(String tipo) {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("[" + CLASS_NAME + "::getTipoDocumentoByTipo] IN tipo = " + tipo);
			}
			return TipoDocumentoMapper.buildFromEntity(tipoDocumentoDAO.findByTipo(tipo));
		} catch (ItemNotFoundDAOException notFoundEx) {
			LOG.error("[" + CLASS_NAME + "::getTipoDocumentoByTipo] ItemNotFoundDAOException for tipo = " + tipo);
			throw new ItemNotFoundBusinessException();
		} catch (DAOException e) {
			LOG.error("[" + CLASS_NAME + "::getTipoDocumentoByTipo] DAOException for tipo = " + tipo, e);
			throw new BusinessException("Errore generico servizio getTipoDocumentoByTipo");
		}
	}

}
