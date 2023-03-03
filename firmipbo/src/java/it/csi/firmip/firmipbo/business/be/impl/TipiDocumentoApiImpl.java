/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */package it.csi.firmip.firmipbo.business.be.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.firmip.firmipbo.business.be.TipiDocumentoApi;
import it.csi.firmip.firmipbo.business.service.TipoDocumentoService;
import it.csi.firmip.firmipbo.dto.TipoDocumento;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundBusinessException;
import it.csi.firmip.firmipbo.exception.service.ResourceNotFoundException;
import it.csi.firmip.firmipbo.exception.service.ServiceException;
import it.csi.firmip.firmipbo.exception.service.UnprocessableEntityException;
import it.csi.firmip.firmipbo.util.LoggerAccessor;

@Component
public class TipiDocumentoApiImpl extends BaseApiImpl implements TipiDocumentoApi {
	
	private static final String CLASS_NAME = "TipiDocumentoApiImpl";
	private static final Logger LOG = LoggerAccessor.getLoggerBusiness();
	
	@Autowired
	TipoDocumentoService tipoDocumentoService;
	
	//
	@Override
	public Response getTipiDocumento(String fields,
		SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			List<TipoDocumento> ris = tipoDocumentoService.getElencoTipoDocumento();
			return Response.ok(ris).build();
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getTipiDocumento] Throwable", ex);
			throw new ServiceException("Errore generico servizio getTipiDocumento");
		} 
	}
	
	@Override
	public Response getTipoDocumentoByTipo(String tipoPP, String fields,
		SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			String tipo = validaStringRequired(tipoPP);
			TipoDocumento ris = tipoDocumentoService.getTipoDocumentoByTipo(tipo);
			return Response.ok(ris).build();
		} catch (UnprocessableEntityException uee) {
			LOG.error("[" + CLASS_NAME + "::getTipoDocumentoByTipo] UnprocessableEntityException");
			throw uee;
		} catch (ItemNotFoundBusinessException notFoundEx) {
			LOG.error("[" + CLASS_NAME + "::getTipoDocumentoByTipo] ItemNotFoundBusinessException for tipoPP = " + tipoPP);
			throw new ResourceNotFoundException(notFoundEx);
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getTipoDocumentoByTipo] BusinessException for tipoPP = " + tipoPP);
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getTipoDocumentoByTipo] Throwable", ex);
			throw new ServiceException("Errore generico servizio getTipoDocumentoByTipo");
		} 
	}
	
}
