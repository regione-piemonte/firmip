/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.be.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.firmip.firmipbo.business.be.CmdApi;
import it.csi.firmip.firmipbo.business.service.BackendService;
import it.csi.firmip.firmipbo.dto.BuildInfo;
import it.csi.firmip.firmipbo.exception.service.ServiceException;
import it.csi.firmip.firmipbo.util.LoggerAccessor;

@Component
public class CmdApiImpl extends BaseApiImpl implements CmdApi {
	
	private final static String CLASS_NAME = "PingApiImpl";
	private Logger LOG = LoggerAccessor.getLoggerBusiness();
	
	@Autowired
	BackendService beService;
	
	@Override
	public Response ping(String dbQP,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			LOG.debug("[" + CLASS_NAME + "::ping] IN dbQP = " + dbQP);
			Boolean withDb = validaBoolean(dbQP, Boolean.FALSE);
			String response = beService.ping(withDb);
			return Response.ok(response).build();
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::ping] Errore generico servizio ping",ex);
			throw new ServiceException("Errore generico servizio ping");
		} 
	}
	
	@Override
	public Response version(SecurityContext securityContext, HttpHeaders httpHeaders ) {
		try {
			LOG.debug("[" + CLASS_NAME + "::version] BEGIN");
			String response = beService.getVersion();
			return Response.ok(response).build();
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::version] Errore generico servizio version",ex);
			throw new ServiceException("Errore generico servizio version");
		} 
	}
	
	@Override
	public Response buildInfo(SecurityContext securityContext, HttpHeaders httpHeaders ) {
		try {
			LOG.debug("[" + CLASS_NAME + "::buildInfo] BEGIN");
			BuildInfo response = beService.getBuildInfo();
			return Response.ok(response).build();
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::buildInfo] Errore generico servizio buildInfo",ex);
			throw new ServiceException("Errore generico servizio buildInfo");
		} 
	}
	
}
