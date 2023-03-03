/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service;

import it.csi.firmip.firmipbo.dto.BuildInfo;
import it.csi.firmip.firmipbo.exception.business.BusinessException;

/**
 * Metodi di business relativi al backend in generale
 *
 */
public interface BackendService {
	
	public String ping(Boolean withDB);
	public String getVersion() throws BusinessException;
	public BuildInfo getBuildInfo() throws BusinessException;

}
