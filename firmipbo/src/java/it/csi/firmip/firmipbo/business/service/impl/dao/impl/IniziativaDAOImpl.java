/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import it.csi.firmip.firmipbo.business.service.impl.dao.IniziativaDAO;
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
@Component
public class IniziativaDAOImpl extends JdbcTemplateDAO implements IniziativaDAO {

	private static final String CLASS_NAME = "IniziativaDAOImpl";
	
	private static final String SELECT_ = "SELECT id_iniziativa, id_tipo_iniziativa, titolo, data, anno";
	private static final String SELECT_I_ = "SELECT i.id_iniziativa, i.id_tipo_iniziativa, i.titolo, i.data, i.anno";
	private static final String SELECT_I_COUNT_ = SELECT_I_ +
			", (select COUNT(f.*) from firmip_t_firma_iniziativa f where f.id_iniziativa = i.id_iniziativa) AS numero_firme";
	
	private static final String FIND_BY_ID = SELECT_I_COUNT_ +
			" FROM firmip_t_iniziativa i" +
			" WHERE i.id_iniziativa = :id_iniziativa";
	
	private static final String ELENCO = SELECT_I_COUNT_ +
			" FROM firmip_t_iniziativa i" +
			" WHERE i.fl_eliminata='N'";
	
	private static final String INSERT = "INSERT INTO firmip_t_iniziativa (" + 
			" id_iniziativa, id_tipo_iniziativa, titolo, data, anno)" + 
			" VALUES (:id_iniziativa, :id_tipo_iniziativa, :titolo, :data, :anno)";

	private static final String UPDATE = "UPDATE firmip_t_iniziativa" +
			" SET id_tipo_iniziativa=:id_tipo_iniziativa, titolo=:titolo, data=:data, anno=:anno" +
			" WHERE id_iniziativa = :id_iniziativa";

	private static final String DELETE = "DELETE FROM firmip_t_iniziativa WHERE id_iniziativa = :id_iniziativa";
	private static final String UPDATE_FL_ELIMINATA = "UPDATE firmip_t_iniziativa SET fl_eliminata='S' WHERE id_iniziativa = :id_iniziativa";

	private static final String ORDER_BY_1_DESC = " ORDER BY 1 DESC";
	
	private static final String SEQ_ID = "SELECT nextval('firmip_t_iniziativa_id_iniziativa_seq')";
	
	@Override
	public List<IniziativaCountEntity> findAll() throws DAOException {
		try {
			return (List<IniziativaCountEntity>) getCustomJdbcTemplate().query(ELENCO + ORDER_BY_1_DESC, BeanPropertyRowMapper.newInstance(IniziativaCountEntity.class));
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::findAll] Errore database: "+e.getMessage(), e);
			throw new DAOException();
		}
	}

	@Override
	public IniziativaCountEntity findById(Integer id) throws ItemNotFoundDAOException, DAOException {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id_iniziativa", id);
			return (IniziativaCountEntity)getCustomNamedParameterJdbcTemplateImpl().queryForObject(FIND_BY_ID, params, BeanPropertyRowMapper.newInstance(IniziativaCountEntity.class) );
		} catch (EmptyResultDataAccessException emptyEx) {
			LOG.error("[" + CLASS_NAME + "::findById] Elemento non trovato: idIniziativa = " + id, emptyEx);
			throw new ItemNotFoundDAOException();
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::findById] Errore database: " + e.getMessage(), e);
			throw new DAOException(getMsgErrDefault());
		}
	}

	@Override
	public Integer insert(IniziativaEntity entity) throws DAOException {
		try {
			LOG.debug("[" + CLASS_NAME + "::insert] IN entity: "+entity);
			Integer idIniziativa = getCustomNamedParameterJdbcTemplateImpl().queryForInt(SEQ_ID, new MapSqlParameterSource() );
			entity.setIdIniziativa(idIniziativa);
			int numRecord = getCustomNamedParameterJdbcTemplateImpl().update(INSERT, mapIniziativaEntityParameters(entity));
			LOG.debug("[" + CLASS_NAME + "::insert] Record inseriti: " + numRecord);
			return idIniziativa;
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::insert] Errore database: " + e.getMessage(), e);
			throw new DAOException();
		}
	}

	@Override
	public int update(IniziativaEntity entity) throws ItemNotFoundDAOException, DAOException {
		try {
			LOG.debug("[" + CLASS_NAME + "::update] IN entity: "+entity);
			int numRecord = getCustomNamedParameterJdbcTemplateImpl().update(UPDATE, mapIniziativaEntityParameters(entity));
			LOG.debug("[" + CLASS_NAME + "::update] Record aggiornati: " + numRecord);
			if (numRecord==0) {
				LOG.error("[" + CLASS_NAME + "::update] Elemento non trovato idIniziativa = " + entity.getIdIniziativa());
				throw new ItemNotFoundDAOException();
			}
			return numRecord;
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::update] Errore database: " + e.getMessage(), e);
			throw new DAOException();
		}
	}

	@Override
	public int delete(IniziativaEntity entity) throws ItemNotFoundDAOException, DAOException {
		return delete(entity.getIdIniziativa());
	}
	
	@Override
	public int delete(Integer idIniziativa) throws ItemNotFoundDAOException, DAOException {
		try {
			LOG.debug("[" + CLASS_NAME + "::delete] IN idIniziativa: "+idIniziativa);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id_iniziativa", idIniziativa);
			int numRecord = getCustomNamedParameterJdbcTemplateImpl().update(UPDATE_FL_ELIMINATA, params);
			LOG.debug("[" + CLASS_NAME + "::delete] Record aggiornati: " + numRecord);
			if (numRecord==0) {
				LOG.error("[" + CLASS_NAME + "::delete] Elemento non trovato idIniziativa = "+idIniziativa);
				throw new ItemNotFoundDAOException();
			}
			return numRecord;
		} catch (ItemNotFoundDAOException e) {
			throw e;
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::delete] Errore database: " + e.getMessage(), e);
			throw new DAOException("ERRORE ELIMINAZIONE");
		}
	}
	private MapSqlParameterSource mapIniziativaEntityParameters(IniziativaEntity entity) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id_iniziativa", entity.getIdIniziativa());
		params.addValue("id_tipo_iniziativa", entity.getIdTipoIniziativa());
		params.addValue("titolo", entity.getTitolo(), Types.VARCHAR);
		params.addValue("data", entity.getData(), Types.DATE);
		params.addValue("anno", entity.getAnno());
		return params;
	}
}
