/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service;

import javax.servlet.http.HttpServletRequest;

public interface AuditService {

	public void login(HttpServletRequest httpRequest, String oggettoOperation, String keyOperation);
	public void logout(HttpServletRequest httpRequest, String oggettoOperation, String keyOperation);
	public void read(HttpServletRequest httpRequest, String oggettoOperation, String keyOperation);
	public void insert(HttpServletRequest httpRequest, String oggettoOperation, String keyOperation);
	public void update(HttpServletRequest httpRequest, String oggettoOperation, String keyOperation);
	public void delete(HttpServletRequest httpRequest, String oggettoOperation, String keyOperation);

}
