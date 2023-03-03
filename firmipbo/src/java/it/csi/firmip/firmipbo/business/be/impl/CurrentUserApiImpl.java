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

import it.csi.firmip.firmipbo.business.be.CurrentUserApi;
import it.csi.firmip.firmipbo.business.service.LoginService;
import it.csi.firmip.firmipbo.dto.UserInfo;
import it.csi.firmip.firmipbo.util.LoggerAccessor;


@Component
public class CurrentUserApiImpl implements CurrentUserApi {
	
	private final static String CLASS_NAME = "CurrentUserApiImpl";
	private Logger log = LoggerAccessor.getLoggerBusiness();
	
	@Autowired
	public LoginService loginService;
	
	public Response getCurrentUser(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest req ) {
		UserInfo currentUser = loginService.getCurrentUser(req);
		log.info("[" + CLASS_NAME + "::getCurrentUser] Accesso eseguito con utente " + currentUser);
		return Response.ok(currentUser).build();
	}

}
