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

import it.csi.firmip.firmipbo.business.service.impl.dao.TipoIniziativaDAO;
import it.csi.firmip.firmipbo.business.service.impl.entity.TipoIniziativaEntity;
import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundDAOException;


/**
 * DAO per l'accesso alla tipologia di iniziativa
 * 
 * @see TipoIniziativaEntity
 * 
 */
@Component
public class TipoIniziativaDAOImpl extends JdbcTemplateDAO implements TipoIniziativaDAO {

	private static final String CLASS_NAME = "TipoIniziativaDAOImpl";
	
	private static final String SELECT_ = "SELECT id_tipo_iniziativa, des_tipo_iniziativa";
	
	private static final String FIND_BY_ID = SELECT_ +
			" FROM firmip_d_tipo_iniziativa" +
			" WHERE id_tipo_iniziativa = :id_tipo_iniziativa";
	
	private static final String ELENCO = SELECT_ +
			" FROM firmip_d_tipo_iniziativa";
	
	@Override
	public List<TipoIniziativaEntity> findAll() throws DAOException {
		try {
			return (List<TipoIniziativaEntity>) getCustomJdbcTemplate().query(ELENCO, BeanPropertyRowMapper.newInstance(TipoIniziativaEntity.class));
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::findAll] Errore database: "+e.getMessage(), e);
			throw new DAOException();
		}
	}

	@Override
	public TipoIniziativaEntity findById(Integer idTipoIniziativa) throws ItemNotFoundDAOException, DAOException {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id_tipo_iniziativa", idTipoIniziativa);
			return (TipoIniziativaEntity)getCustomNamedParameterJdbcTemplateImpl().queryForObject(FIND_BY_ID, params, BeanPropertyRowMapper.newInstance(TipoIniziativaEntity.class) );
		} catch (EmptyResultDataAccessException emptyEx) {
			LOG.error("[" + CLASS_NAME + "::findById] Elemento non trovato: idTipoIniziativa = "+idTipoIniziativa, emptyEx);
			throw new ItemNotFoundDAOException();
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::findById] Errore database: "+e.getMessage(), e);
			throw new DAOException();
		}
	}

}
