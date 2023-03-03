/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service.impl.dao;


import java.util.List;

import it.csi.firmip.firmipbo.business.service.impl.entity.FirmaIniziativaEntity;
import it.csi.firmip.firmipbo.business.service.impl.entity.FirmaIniziativaFilter;
import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundDAOException;

/**
 * DAO per l'accesso alle firme di un iniziativa
 * 
 * @see FirmaIniziativaEntity
 * 
 */
public interface FirmaIniziativaDAO {

	public List<FirmaIniziativaEntity> find(FirmaIniziativaFilter filter, String sorter) throws DAOException;
	public List<FirmaIniziativaEntity> find(FirmaIniziativaFilter filter) throws DAOException;
	public List<FirmaIniziativaEntity> findByIdIniziativa(Integer idIniziativa, String sorter) throws DAOException;
	public List<FirmaIniziativaEntity> findByIdIniziativa(Integer idIniziativa) throws DAOException;
	public int countByIdIniziativa(Integer idIniziativa) throws DAOException;
	public FirmaIniziativaEntity findById(Integer idFirmaIniziativa) throws ItemNotFoundDAOException, DAOException;
	public Integer insert(FirmaIniziativaEntity entity) throws DAOException;
	public int update(FirmaIniziativaEntity entity) throws ItemNotFoundDAOException, DAOException;
	public int delete(FirmaIniziativaEntity entity) throws ItemNotFoundDAOException, DAOException;
	public int delete(Integer idFirmaIniziativa) throws ItemNotFoundDAOException, DAOException;
	public String getMaxNumFoglio(Integer idIniziativa) throws DAOException;
	
}
