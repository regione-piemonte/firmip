/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service.impl.dao;


import java.util.List;

import it.csi.firmip.firmipbo.business.service.impl.entity.TipoDocumentoEntity;
import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundDAOException;

/**
 * DAO per l'accesso ai tipi di documento
 * 
 * @see TipoDocumentoEntity
 * 
 */
public interface TipoDocumentoDAO {

	public List<TipoDocumentoEntity> findAll() throws DAOException;
	public TipoDocumentoEntity findByTipo(String tipoDocumento) throws ItemNotFoundDAOException, DAOException;
		
}
