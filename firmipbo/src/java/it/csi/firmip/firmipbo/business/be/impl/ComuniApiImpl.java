/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.be.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.firmip.firmipbo.business.be.ComuniApi;
import it.csi.firmip.firmipbo.business.service.ComuneService;
import it.csi.firmip.firmipbo.business.service.impl.entity.ComuneFilter;
import it.csi.firmip.firmipbo.dto.Comune;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundBusinessException;
import it.csi.firmip.firmipbo.exception.service.ResourceNotFoundException;
import it.csi.firmip.firmipbo.exception.service.ServiceException;
import it.csi.firmip.firmipbo.exception.service.UnprocessableEntityException;
import it.csi.firmip.firmipbo.util.LoggerAccessor;

@Component
public class ComuniApiImpl extends BaseApiImpl implements ComuniApi {
	
	private static final String CLASS_NAME = "ComuniApiImpl";
	private static final Logger LOG = LoggerAccessor.getLoggerBusiness();
	
	@Autowired
	ComuneService comuneService;
	
	//
	@Override
	public Response getComuni(String codiceRegioneQP, String siglaProvinciaQP,
		String codiceIstatQP, String nomeQP, 
		String idIniziativaQP, 
		SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			ComuneFilter filter = new ComuneFilter();
			filter.setIstatRegione(validaStringTrimUpper(codiceRegioneQP));
			filter.setSiglaProvincia(validaStringTrimUpper(siglaProvinciaQP));
			filter.setIstatComune(validaStringTrim(codiceIstatQP));
			filter.setNomeComune(validaStringTrim(nomeQP));
			filter.setIdIniziativa(validaLong(idIniziativaQP));
			List<Comune> ris = comuneService.getElencoComuni(filter);
			return Response.ok(ris).build();
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getComuni] BusinessException");
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getComuni] Throwable", ex);
			throw new ServiceException("Errore generico servizio getComuni");
		} 
	}

	@Override
	public Response getComuniByIstatRegione(String codiceRegionePP, String fields, 
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			String codiceRegione = validaStringTrimUpperRequired(codiceRegionePP);
			List<Comune> ris = comuneService.getElencoComuniByIstatRegione(codiceRegione);
			return Response.ok(ris).build();
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getComuniByIstatRegione] BusinessException");
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getComuniByIstatRegione] Throwable", ex);
			throw new ServiceException("Errore generico servizio getComuniByIstatRegione");
		}
	}
	
	@Override
	public Response getComuneById(String idPP, String fields,
		SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			Integer id = validaIntegerRequired(idPP);
			Comune ris = comuneService.getComuneById(id);
			return Response.ok(ris).build();
		} catch (UnprocessableEntityException uee) {
			LOG.error("[" + CLASS_NAME + "::getComuneById] UnprocessableEntityException");
			throw uee;
		} catch(ItemNotFoundBusinessException notFoundEx) {
			LOG.error("[" + CLASS_NAME + "::getComuneById] ItemNotFoundBusinessException for idPP = " + idPP);
			throw new ResourceNotFoundException(notFoundEx);
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getComuneById] BusinessException for idPP = " + idPP);
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getComuneById] Throwable for idPP = " + idPP, ex);
			throw new ServiceException("Errore generico servizio getComuneById");
		} 
	}

	@Override
	public Response getComuniByCodiceIstat(String codiceIstatPP, String fields,
		SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			String codiceIstat = validaStringRequired(codiceIstatPP);
			Comune ris = comuneService.getComuneByCodiceIstat(codiceIstat);
			return Response.ok(ris).build();
		} catch (UnprocessableEntityException uee) {
			LOG.error("[" + CLASS_NAME + "::getComuniByCodiceIstat] UnprocessableEntityException");
			throw uee;
		} catch(ItemNotFoundBusinessException notFoundEx) {
			LOG.error("[" + CLASS_NAME + "::getComuniByCodiceIstat] ItemNotFoundBusinessException for codiceIstatPP = " + codiceIstatPP);
			throw new ResourceNotFoundException(notFoundEx);
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getComuniByCodiceIstat] BusinessException for codiceIstatPP = " + codiceIstatPP);
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getComuniByCodiceIstat] Throwable for codiceIstatPP = " + codiceIstatPP, ex);
			throw new ServiceException("Errore generico servizio getComuniByCodiceIstat");
		} 
	}
	
}
