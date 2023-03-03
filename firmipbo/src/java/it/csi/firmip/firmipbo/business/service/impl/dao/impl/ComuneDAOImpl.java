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

import it.csi.firmip.firmipbo.business.service.impl.dao.ComuneDAO;
import it.csi.firmip.firmipbo.business.service.impl.entity.ComuneEntity;
import it.csi.firmip.firmipbo.business.service.impl.entity.ComuneFilter;
import it.csi.firmip.firmipbo.business.service.impl.entity.FirmaIniziativaEntity;
import it.csi.firmip.firmipbo.business.service.impl.entity.FirmaIniziativaFilter;
import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundDAOException;


/**
 * DAO per l'accesso ai comuni italiani
 * 
 * @see ComuneEntity
 * 
 */
@Component
public class ComuneDAOImpl extends JdbcTemplateDAO implements ComuneDAO {

	private static final String CLASS_NAME = "ComuneDAOImpl";
	
	private static final  String SELECT_  = "SELECT id_comune, codice_istat, nome_comune, sigla_provincia";
	
	private static final  String FIND_BY_ID = SELECT_ +
			" FROM firmip_d_comune" +
			" WHERE id_comune = :id_comune";
	
	private static final  String FIND_BY_CD_ISTAT = SELECT_ +
			" FROM firmip_d_comune" +
			" WHERE codice_istat = :codice_istat";
	
	private static final String FIND = SELECT_ +
			" FROM firmip_d_comune";
	
	private static final String FIND_BY_ISTAT_REGIONE = SELECT_ +
			" FROM firmip_d_comune" +
			" WHERE codice_regione = :codice_regione";
	
	@Override
	public List<ComuneEntity> findAll() throws DAOException {
		return (List<ComuneEntity>) getCustomJdbcTemplate().query(FIND, BeanPropertyRowMapper.newInstance(ComuneEntity.class));
	}
	
	@Override
	public List<ComuneEntity> find(ComuneFilter filter) throws DAOException {
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder sb = new StringBuilder(FIND);
		sb.append(readFilter(filter, params));
		return (List<ComuneEntity>) getCustomNamedParameterJdbcTemplateImpl().query(sb.toString(), params, BeanPropertyRowMapper.newInstance(ComuneEntity.class));
	}
	
	private Object readFilter(ComuneFilter filter, MapSqlParameterSource params) {
		StringBuilder sb = new StringBuilder();

		if (filter!=null) {
			if (filter.getIdComune().isPresent()) {
				sb = appendWhereOrAnd(sb).append("id_comune = :id_comune");
				params.addValue("id_comune", filter.getIdComune().get());
				return sb;
			}
			if (filter.getIstatComune().isPresent()) {
				sb = appendWhereOrAnd(sb).append("codice_istat = :codice_istat");
				params.addValue("codice_istat", filter.getIstatComune().get());
			}
			if (filter.getNomeComune().isPresent()) {
				sb = appendWhereOrAnd(sb).append("UPPER(nome_comune) LIKE :nome_comune");
				params.addValue("nome_comune", /*"%"+*/filter.getNomeComune().get().toUpperCase()+"%");
			}
			if (filter.getSiglaProvincia().isPresent()) {
				sb = appendWhereOrAnd(sb).append("sigla_provincia = :sigla_provincia");
				params.addValue("sigla_provincia", filter.getSiglaProvincia().get());
			}
			if (filter.getIstatRegione().isPresent()) {
				sb = appendWhereOrAnd(sb).append("codice_regione = :codice_regione");
				params.addValue("codice_regione", filter.getIstatRegione().get());
			}
			if (filter.getIdIniziativa().isPresent()) {
				sb = appendWhereOrAnd(sb).append("id_comune in (SELECT id_comune FROM firmip_t_firma_iniziativa WHERE id_iniziativa = :id_iniziativa)");
				params.addValue("id_iniziativa", filter.getIdIniziativa().get());
			}
		}
		return sb;
	}

	private StringBuilder appendWhereOrAnd(StringBuilder sb) {
		return sb.append(sb.length()==0?" WHERE ":" AND ");
	}
	
	@Override
	public List<ComuneEntity> findByIstatRegione(String codiceRegione) throws DAOException {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("codice_regione", codiceRegione);
		return (List<ComuneEntity>) getCustomNamedParameterJdbcTemplateImpl().query(FIND_BY_ISTAT_REGIONE, params, BeanPropertyRowMapper.newInstance(ComuneEntity.class));
	}

	@Override
	public ComuneEntity findById(Integer idComune) throws ItemNotFoundDAOException, DAOException {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id_comune", idComune);
			return (ComuneEntity)getCustomNamedParameterJdbcTemplateImpl().queryForObject(FIND_BY_ID, params, BeanPropertyRowMapper.newInstance(ComuneEntity.class) );
		} catch (EmptyResultDataAccessException emptyEx) {
			LOG.error("[" + CLASS_NAME + "::findById] Elemento non trovato: "+emptyEx.getMessage(),emptyEx);
			throw new ItemNotFoundDAOException();
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::findById] Errore database: "+e.getMessage(),e);
			throw new DAOException(getMsgErrDefault());
		}
	}

	@Override
	public ComuneEntity findByCodiceIstat(String codiceIstat) throws ItemNotFoundDAOException, DAOException {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("codice_istat", codiceIstat);
			return (ComuneEntity)getCustomNamedParameterJdbcTemplateImpl().queryForObject(FIND_BY_CD_ISTAT, params, BeanPropertyRowMapper.newInstance(ComuneEntity.class) );
		} catch (EmptyResultDataAccessException emptyEx) {
			LOG.error("[" + CLASS_NAME + "::findByCodiceIstat] Elemento non trovato: "+emptyEx.getMessage(),emptyEx);
			throw new ItemNotFoundDAOException();
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::findByCodiceIstat] Errore database: "+e.getMessage(),e);
			throw new DAOException(getMsgErrDefault());
		}
	}

}
