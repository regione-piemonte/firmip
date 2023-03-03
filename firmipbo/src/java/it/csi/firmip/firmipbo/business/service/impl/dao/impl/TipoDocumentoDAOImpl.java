/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl.dao.impl;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import it.csi.firmip.firmipbo.business.service.impl.dao.TipoDocumentoDAO;
import it.csi.firmip.firmipbo.business.service.impl.entity.TipoDocumentoEntity;
import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundDAOException;


/**
 * DAO per l'accesso ai tipi di documento
 * 
 * @see TipoDocumentoEntity
 * 
 */
@Component
public class TipoDocumentoDAOImpl extends JdbcTemplateDAO implements TipoDocumentoDAO {

	private static final String CLASS_NAME = "TipoDocumentoDAOImpl";
	
	private static final String SELECT_ = "SELECT tipo_documento, nome_documento";
	
	private static final String FIND_BY_TIPO = SELECT_ +
			" FROM firmip_d_tipo_documento" +
			" WHERE tipo_documento = :tipo_documento";
	
	private static final String ELENCO = SELECT_ +
			" FROM firmip_d_tipo_documento";
	
	@Override
	public List<TipoDocumentoEntity> findAll() throws DAOException {
		try {
			return (List<TipoDocumentoEntity>) getCustomJdbcTemplate().query(ELENCO, BeanPropertyRowMapper.newInstance(TipoDocumentoEntity.class));
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::findAll] Errore database: "+e.getMessage(), e);
			throw new DAOException();
		}
	}

	@Override
	public TipoDocumentoEntity findByTipo(String tipoDocumento) throws ItemNotFoundDAOException, DAOException {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("tipo_documento", tipoDocumento);
			return (TipoDocumentoEntity)getCustomNamedParameterJdbcTemplateImpl().queryForObject(FIND_BY_TIPO, params, BeanPropertyRowMapper.newInstance(TipoDocumentoEntity.class) );
		} catch (EmptyResultDataAccessException emptyEx) {
			LOG.error("[" + CLASS_NAME + "::findByTipo] Elemento non trovato: tipoDocumento = "+tipoDocumento, emptyEx);
			throw new ItemNotFoundDAOException();
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::findByTipo] Errore database: "+e.getMessage(),e);
			throw new DAOException();
		}
	}

}
