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

import it.csi.firmip.firmipbo.business.be.LocalLogoutApi;
import it.csi.firmip.firmipbo.business.service.AuditService;
import it.csi.firmip.firmipbo.business.service.LoginService;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.exception.service.ServiceException;
import it.csi.firmip.firmipbo.util.Constants;
import it.csi.firmip.iride.base.Identita;

@Component
public class LocalLogoutApiImpl implements LocalLogoutApi {
	
	private final static String CLASS_NAME = "LocalLogoutApiImpl";
	private Logger LOG;
	
	@Autowired
	public LoginService loginService;
	@Autowired
	AuditService auditService;
	
	public Response localLogout(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			Identita identita = (Identita)httpRequest.getSession().getAttribute(Constants.SESSION_IRIDE_ID);
			auditService.logout(httpRequest, "logout", identita.toString());
			loginService.localLogout(httpRequest);
			return Response.ok().build();
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::localLogout] BusinessException");
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::localLogout] Throwable", ex);
			throw new ServiceException("Errore generico servizio localLogout");
		}
	}
	
}
