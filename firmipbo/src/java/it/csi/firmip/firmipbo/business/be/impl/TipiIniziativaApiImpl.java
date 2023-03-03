/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.be.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.firmip.firmipbo.business.be.TipiIniziativaApi;
import it.csi.firmip.firmipbo.business.service.TipoIniziativaService;
import it.csi.firmip.firmipbo.dto.TipoIniziativa;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundBusinessException;
import it.csi.firmip.firmipbo.exception.service.ResourceNotFoundException;
import it.csi.firmip.firmipbo.exception.service.ServiceException;
import it.csi.firmip.firmipbo.exception.service.UnprocessableEntityException;
import it.csi.firmip.firmipbo.util.LoggerAccessor;

@Component
public class TipiIniziativaApiImpl extends BaseApiImpl implements TipiIniziativaApi {
	
	private static final String CLASS_NAME = "TipiIniziativaApiImpl";
	private static final Logger LOG = LoggerAccessor.getLoggerBusiness();
	
	@Autowired
	TipoIniziativaService tipoIniziativaService;
	
	//
	@Override
	public Response getTipiIniziativa(String fields,
		SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			List<TipoIniziativa> ris = tipoIniziativaService.getElencoTipoIniziativa();
			return Response.ok(ris).build();
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getTipiIniziativa] BusinessException");
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getTipiIniziativa] Throwable", ex);
			throw new ServiceException("Errore generico servizio getTipiIniziativa");
		} 
	}
	
	@Override
	public Response getTipoIniziativaById(String idPP, String fields,
		SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			Integer id = validaInteger1BasedRequired(idPP);
			TipoIniziativa ris = tipoIniziativaService.getTipoIniziativaById(id);
			return Response.ok(ris).build();
		} catch (UnprocessableEntityException uee) {
			LOG.error("[" + CLASS_NAME + "::getTipoIniziativaById] UnprocessableEntityException");
			throw uee;
		} catch(ItemNotFoundBusinessException notFoundEx) {
			LOG.error("[" + CLASS_NAME + "::getTipoIniziativaById] ItemNotFoundBusinessException for idPP = " + idPP);
			throw new ResourceNotFoundException(notFoundEx);
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getTipoIniziativaById] BusinessException for idPP = " + idPP);
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getTipoIniziativaById] Throwable for idPP = " + idPP, ex);
			throw new ServiceException("Errore generico servizio getTipoIniziativaById");
		} 
	}
	
}
