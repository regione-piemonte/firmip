/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.firmip.firmipbo.business.service.impl.dao.impl.component.CustomJdbcTemplateImpl;
import it.csi.firmip.firmipbo.business.service.impl.dao.impl.component.CustomNamedParameterJdbcTemplateImpl;
import it.csi.firmip.firmipbo.util.LoggerAccessor;

/**
 * @author franc
 * Classe da cui ereditano tutti i DAO che accedono al database
 */
public abstract class JdbcTemplateDAO {
	
	protected final static String MSG_ERR_DEFAULT = "ERORE BLOCCANTE ESECUZIONE COMANDO DATABASE";
	protected static final Logger LOG = LoggerAccessor.getLoggerIntegration();
	
	private CustomNamedParameterJdbcTemplateImpl customNamedParameterJdbcTemplate;
	private CustomJdbcTemplateImpl	customJdbcTemplate;
	
	public CustomNamedParameterJdbcTemplateImpl getCustomNamedParameterJdbcTemplateImpl() {
		return customNamedParameterJdbcTemplate;
	}
	@Autowired
	public void setCustomNamedParameterJdbcTemplate(CustomNamedParameterJdbcTemplateImpl customNamedParameterJdbcTemplate) {
		this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
	}
	public CustomJdbcTemplateImpl getCustomJdbcTemplate() {
		return customJdbcTemplate;
	}
	@Autowired
	public void setCustomJdbcTemplate(CustomJdbcTemplateImpl customJdbcTemplate) {
		this.customJdbcTemplate = customJdbcTemplate;
	}
	
	public String getMsgErrDefault() {
		return this.MSG_ERR_DEFAULT;
	}
	
}
