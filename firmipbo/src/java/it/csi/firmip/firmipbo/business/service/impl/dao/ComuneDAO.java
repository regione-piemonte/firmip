/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service.impl.dao;


import java.util.List;

import it.csi.firmip.firmipbo.business.service.impl.entity.ComuneEntity;
import it.csi.firmip.firmipbo.business.service.impl.entity.ComuneFilter;
import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundDAOException;

/**
 * DAO per l'accesso ai comuni italiani
 * 
 * @see ComuneEntity
 * 
 */
public interface ComuneDAO {

	public List<ComuneEntity> findAll() throws DAOException;
	public List<ComuneEntity> find(ComuneFilter filter) throws DAOException;
	public List<ComuneEntity> findByIstatRegione(String codiceRegione) throws DAOException;
	public ComuneEntity findById(Integer idComune) throws ItemNotFoundDAOException, DAOException;
	public ComuneEntity findByCodiceIstat(String codiceIstat) throws ItemNotFoundDAOException, DAOException;
		
}
