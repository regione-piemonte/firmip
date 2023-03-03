/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service.impl.dao;

import it.csi.firmip.firmipbo.business.service.impl.entity.AuditEntity;
import it.csi.firmip.firmipbo.exception.business.DAOException;


public interface AuditDAO {

	public int insert(AuditEntity auditEntity) throws DAOException;
}
