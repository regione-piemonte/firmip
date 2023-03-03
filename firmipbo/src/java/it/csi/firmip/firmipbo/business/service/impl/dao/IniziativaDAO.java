/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service.impl.dao;


import java.util.List;

import it.csi.firmip.firmipbo.business.service.impl.entity.IniziativaCountEntity;
import it.csi.firmip.firmipbo.business.service.impl.entity.IniziativaEntity;
import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundDAOException;

/**
 * DAO per l'accesso all'iniziativa
 * 
 * @see IniziativaEntity
 * 
 */
public interface IniziativaDAO {

	public List<IniziativaCountEntity> findAll() throws DAOException;
	public IniziativaCountEntity findById(Integer id) throws ItemNotFoundDAOException, DAOException;
	public Integer insert(IniziativaEntity entity) throws DAOException;
	public int update(IniziativaEntity entity) throws ItemNotFoundDAOException, DAOException;
	public int delete(IniziativaEntity entity) throws ItemNotFoundDAOException, DAOException;
	public int delete(Integer idIniziativa) throws ItemNotFoundDAOException, DAOException;
	
}
