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

import it.csi.firmip.firmipbo.business.be.FirmeApi;
import it.csi.firmip.firmipbo.business.service.FirmaService;
import it.csi.firmip.firmipbo.dto.Firma;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundBusinessException;
import it.csi.firmip.firmipbo.exception.service.ResourceNotFoundException;
import it.csi.firmip.firmipbo.exception.service.ServiceException;
import it.csi.firmip.firmipbo.exception.service.UnprocessableEntityException;
import it.csi.firmip.firmipbo.util.LoggerAccessor;

@Component
public class FirmeApiImpl extends BaseApiImpl implements FirmeApi {
	
	private static final String CLASS_NAME = "FirmeApiImpl";
	private static final Logger LOG = LoggerAccessor.getLoggerBusiness();
	
	@Autowired
	FirmaService firmeService;
	
	//
	@Override
	public Response getFirmeByIdIniziativa(String idIniziativaPP, String sort, String fields,
		SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			LOG.info("[" + CLASS_NAME + "::getFirmeByIdIniziativa] IN idIniziativaPP = " + idIniziativaPP + " sort = " + sort);
			Integer idIniziativa = validaIntegerRequired(idIniziativaPP);
			List<Firma> ris = firmeService.getFirmeByIdIniziativa(idIniziativa);
			return Response.ok(ris).build();
		} catch (UnprocessableEntityException uee) {
			LOG.warn("[" + CLASS_NAME + "::getFirmeByIdIniziativa] UnprocessableEntityException for idIniziativaPP = " + idIniziativaPP);
			throw uee;
		} catch (ItemNotFoundBusinessException notFoundEx) {
			LOG.warn("[" + CLASS_NAME + "::getFirmeByIdIniziativa] ItemNotFoundBusinessException for idIniziativaPP = " + idIniziativaPP);
			throw new ResourceNotFoundException(notFoundEx);
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getFirmeByIdIniziativa] BusinessException");
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getFirmeByIdIniziativa] Throwable", ex);
			throw new ServiceException("Errore generico servizio getFirmeByIdIniziativa");
		}
	}
	
	@Override
	public Response getCountFirmeByIdIniziativa(String idIniziativaPP, String fields,
		SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			LOG.info("[" + CLASS_NAME + "::getCountFirmeByIdIniziativa] IN idIniziativaPP = " + idIniziativaPP);
			Integer idIniziativa = validaIntegerRequired(idIniziativaPP);
			Integer ris = firmeService.getCountFirmeByIdIniziativa(idIniziativa);
			return Response.ok(ris).build();
		} catch (UnprocessableEntityException uee) {
			LOG.warn("[" + CLASS_NAME + "::getCountFirmeByIdIniziativa] UnprocessableEntityException for idIniziativaPP = " + idIniziativaPP);
			throw uee;
		} catch (ItemNotFoundBusinessException notFoundEx) {
			LOG.warn("[" + CLASS_NAME + "::getCountFirmeByIdIniziativa] ItemNotFoundBusinessException for idIniziativaPP = " + idIniziativaPP);
			throw new ResourceNotFoundException(notFoundEx);
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getCountFirmeByIdIniziativa] BusinessException");
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getCountFirmeByIdIniziativa] Throwable", ex);
			throw new ServiceException("Errore generico servizio getCountFirmeByIdIniziativa");
		}
	}
	
	@Override
	public Response getFirmaById(String idFirmaPP, String fields,
		SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			LOG.info("[" + CLASS_NAME + "::getFirmaById] IN idFirmaPP = " + idFirmaPP);
			Integer idFirma = validaInteger1BasedRequired(idFirmaPP);
			Firma ris = firmeService.getFirmaById(idFirma);
			return Response.ok(ris).build();
		} catch (UnprocessableEntityException uee) {
			LOG.warn("[" + CLASS_NAME + "::getFirmaById] UnprocessableEntityException for idFirmaPP = " + idFirmaPP);
			throw uee;
		} catch (ItemNotFoundBusinessException notFoundEx) {
			LOG.warn("[" + CLASS_NAME + "::getFirmaById] ItemNotFoundBusinessException for idFirmaPP = " + idFirmaPP);
			throw new ResourceNotFoundException(notFoundEx);
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getFirmaById] BusinessException for idFirmaPP = " + idFirmaPP);
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getFirmaById] Throwable for idFirmaPP = " + idFirmaPP, ex);
			throw new ServiceException("Errore generico servizio getFirmaById");
		}
	}

	@Override
	public Response postFirma(Firma body,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			LOG.info("[" + CLASS_NAME + "::postFirma] IN body = " + body);
			Firma ris = firmeService.createFirma(body);
			return Response.ok(ris).build();
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::postFirma] BusinessException for body = " + body);
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::postFirma] Throwable for body = " + body, ex);
			throw new ServiceException("Errore generico servizio postFirma");
		}
	}

	@Override
	public Response putFirma(String idFirmaPP, Firma body, 
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			LOG.info("[" + CLASS_NAME + "::putFirma] IN idFirmaPP = " + idFirmaPP + " body = " + body);
			Integer idFirma = validaInteger1BasedRequired(idFirmaPP);
			if (!idFirma.equals(body.getId())) {
				LOG.error("[" + CLASS_NAME + "::putFirma] idFirmaPP = " + idFirmaPP + " diverso dal body " + body.getId());
				throw new ServiceException("Errore idFirma incongruente");
			}
			Firma ris = firmeService.updateFirma(body);
			return Response.ok(ris).build();
		} catch (UnprocessableEntityException uee) {
			LOG.warn("[" + CLASS_NAME + "::putFirma] UnprocessableEntityException for idFirmaPP = " + idFirmaPP);
			throw uee;
		} catch (ItemNotFoundBusinessException notFoundEx) {
			LOG.warn("[" + CLASS_NAME + "::putFirma] ItemNotFoundBusinessException for idFirmaPP = " + idFirmaPP);
			throw new ResourceNotFoundException(notFoundEx);
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::putFirma] BusinessException for idFirmaPP = " + idFirmaPP);
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::putFirma] Throwable for idFirmaPP = " + idFirmaPP, ex);
			throw new ServiceException("Errore generico servizio putFirma");
		}
	}

	@Override
	public Response deleteFirma(String idFirmaPP, 
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			LOG.info("[" + CLASS_NAME + "::deleteFirma] IN idFirmaPP = " + idFirmaPP);
			Integer idFirma = validaIntegerRequired(idFirmaPP);
			firmeService.deleteFirma(idFirma);
			return Response.ok().build();
		} catch (UnprocessableEntityException uee) {
			LOG.warn("[" + CLASS_NAME + "::deleteFirma] UnprocessableEntityException for idFirmaPP = " + idFirmaPP);
			throw uee;
		} catch (ItemNotFoundBusinessException notFoundEx) {
			LOG.warn("[" + CLASS_NAME + "::deleteFirma] ItemNotFoundBusinessException for idFirmaPP = " + idFirmaPP);
			throw new ResourceNotFoundException(notFoundEx);
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::deleteFirma] BusinessException for idFirmaPP = " + idFirmaPP);
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::deleteFirma] Throwable for idFirmaPP = " + idFirmaPP, ex);
			throw new ServiceException("Errore generico servizio deleteFirma");
		}
	}
	
}
