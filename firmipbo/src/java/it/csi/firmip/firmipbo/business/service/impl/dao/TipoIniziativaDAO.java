/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service.impl.dao;


import java.util.List;

import it.csi.firmip.firmipbo.business.service.impl.entity.TipoIniziativaEntity;
import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundDAOException;

/**
 * DAO per l'accesso alla tipologia di iniziativa
 * 
 * @see TipoIniziativaEntity
 * 
 */
public interface TipoIniziativaDAO {

	public List<TipoIniziativaEntity> findAll() throws DAOException;
	public TipoIniziativaEntity findById(Integer idTipoIniziativa) throws ItemNotFoundDAOException, DAOException;
		
}
