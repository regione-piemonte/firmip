/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.firmip.firmipbo.business.service.AuditService;
import it.csi.firmip.firmipbo.business.service.impl.dao.AuditDAO;
import it.csi.firmip.firmipbo.business.service.impl.entity.AuditEntity;
import it.csi.firmip.firmipbo.dto.UserInfo;
import it.csi.firmip.firmipbo.util.Constants;
import it.csi.firmip.firmipbo.util.LoggerAccessor;

@Component
public class AuditServiceImpl implements AuditService {
	
	private final static String CLASS_NAME = "AuditServiceImpl";
	private Logger log = LoggerAccessor.getLoggerBusiness();
	
	@Autowired
	AuditDAO auditDAO;
	
	@Override
	public void login(HttpServletRequest httpRequest, String oggettoOperation, String keyOperation) {
		try {
			auditDAO.insert(new AuditEntity(retrieveIpAddress(httpRequest), retrieveUserCodFisc(httpRequest), AuditEntity.EnumOperazione.LOGIN, oggettoOperation, keyOperation));
		} catch (Exception e) {
			log.error("[" + CLASS_NAME + "::login]" + e.toString(), e);
		}
	}

	@Override
	public void logout(HttpServletRequest httpRequest, String oggettoOperation, String keyOperation) {
		try {
			auditDAO.insert(new AuditEntity(retrieveIpAddress(httpRequest), retrieveUserCodFisc(httpRequest), AuditEntity.EnumOperazione.LOGOUT, oggettoOperation, keyOperation));
		} catch (Exception e) {
			log.error("[" + CLASS_NAME + "::logout]" + e.toString(), e);
		}
	}
	
	@Override
	public void read(HttpServletRequest httpRequest, String oggettoOperation, String keyOperation) {
		try {
			auditDAO.insert(new AuditEntity(retrieveIpAddress(httpRequest), retrieveUserCodFisc(httpRequest), AuditEntity.EnumOperazione.READ, oggettoOperation, keyOperation));
		} catch (Exception e) {
			log.error("[" + CLASS_NAME + "::read]" + e.toString(), e);
		}
	}
	
	@Override
	public void insert(HttpServletRequest httpRequest, String oggettoOperation, String keyOperation) {
		try {
			auditDAO.insert(new AuditEntity(retrieveIpAddress(httpRequest), retrieveUserCodFisc(httpRequest), AuditEntity.EnumOperazione.INSERT, oggettoOperation, keyOperation));
		} catch (Exception e) {
			log.error("[" + CLASS_NAME + "::insert]" + e.toString(), e);
		}
	}
	
	@Override
	public void update(HttpServletRequest httpRequest, String oggettoOperation, String keyOperation) {
		try {
			auditDAO.insert(new AuditEntity(retrieveIpAddress(httpRequest), retrieveUserCodFisc(httpRequest), AuditEntity.EnumOperazione.UPDATE, oggettoOperation, keyOperation));
		} catch (Exception e) {
			log.error("[" + CLASS_NAME + "::update]" + e.toString(), e);
		}
	}
	
	@Override
	public void delete(HttpServletRequest httpRequest, String oggettoOperation, String keyOperation) {
		try {
			auditDAO.insert(new AuditEntity(retrieveIpAddress(httpRequest), retrieveUserCodFisc(httpRequest), AuditEntity.EnumOperazione.DELETE, oggettoOperation, keyOperation));
		} catch (Exception e) {
			log.error("[" + CLASS_NAME + "::delete]" + e.toString(), e);
		}
	}
	
	//
	private String retrieveIpAddress(HttpServletRequest httpRequest) {
		return httpRequest.getRemoteAddr();
	}

	private String retrieveUserCodFisc(HttpServletRequest httpRequest) {
		return retrieveUser(httpRequest).getCodFisc();
	}
	
	private UserInfo retrieveUser(HttpServletRequest httpRequest) {
		return (UserInfo) httpRequest.getSession ().getAttribute(Constants.SESSION_USERINFO);
	}
	
}
