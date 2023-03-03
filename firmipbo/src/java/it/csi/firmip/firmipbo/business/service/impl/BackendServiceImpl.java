/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.firmip.firmipbo.business.service.BackendService;
import it.csi.firmip.firmipbo.business.service.impl.dao.DummyDAO;
import it.csi.firmip.firmipbo.dto.BuildInfo;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.util.EnvProperties;
import it.csi.firmip.firmipbo.util.LoggerAccessor;

/**
 * Metodi di business relativi al backend in generale
 *
 */

@Component
public class BackendServiceImpl  implements BackendService {
	
	private final static String CLASS_NAME = "BackendServiceImpl";
	private Logger LOG = LoggerAccessor.getLoggerBusiness();
	
	@Autowired
	DummyDAO dummyDAO;
	
	@Override
	public String ping(Boolean withDB) {
		return withDB?dummyDAO.pingDB():"OK";
	}

	@Override
	public String getVersion() throws BusinessException {
		return EnvProperties.readFromFile(EnvProperties.VERSION);
	}
	
	@Override
	public BuildInfo getBuildInfo() throws BusinessException {
		return new BuildInfo(EnvProperties.readFromFile(EnvProperties.VERSION),
			EnvProperties.readFromFile(EnvProperties.BUILD_TIME),
			EnvProperties.readFromFile(EnvProperties.TARGET_LINE));
	}
	
}
