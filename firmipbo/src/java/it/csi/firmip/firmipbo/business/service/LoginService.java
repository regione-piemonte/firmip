/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service;

import javax.servlet.http.HttpServletRequest;

import it.csi.firmip.firmipbo.dto.UserInfo;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.iride.base.Identita;

public interface LoginService {

	public UserInfo login(Identita identita) throws BusinessException;

	public UserInfo getCurrentUser(HttpServletRequest req);

	public void localLogout(HttpServletRequest req);

}
