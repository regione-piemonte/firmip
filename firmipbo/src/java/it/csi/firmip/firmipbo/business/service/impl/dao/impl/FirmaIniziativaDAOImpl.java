/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl.dao.impl;

import java.sql.Types;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import it.csi.firmip.firmipbo.business.service.impl.dao.FirmaIniziativaDAO;
import it.csi.firmip.firmipbo.business.service.impl.entity.FirmaIniziativaEntity;
import it.csi.firmip.firmipbo.business.service.impl.entity.FirmaIniziativaFilter;
import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundDAOException;

/**
 * DAO per l'accesso alle firme di un iniziativa
 * 
 * @see FirmaFirmaIniziativaEntity
 * 
 */
@Component
public class FirmaIniziativaDAOImpl extends JdbcTemplateDAO implements FirmaIniziativaDAO {

	private static final String CLASS_NAME = "FirmaIniziativaDAOImpl";

	private static final String SELECT_ = "SELECT id_firma_iniziativa, id_iniziativa, cognome, nome, data_nascita, luogo_nascita, tipo_documento, num_documento, indirizzo, id_comune, num_foglio";
	private static final String SELECT_COUNT_ = "SELECT count(*)";

	private static final String FIND = SELECT_ +
			" FROM firmip_t_firma_iniziativa";

	private static final String FIND_BY_ID = SELECT_ +
			" FROM firmip_t_firma_iniziativa" +
			" WHERE id_firma_iniziativa = :id_firma_iniziativa";

	private static final String ELENCO_BY_ID_INIZIATIVA = SELECT_ +
			" FROM firmip_t_firma_iniziativa" +
			" WHERE id_iniziativa = :id_iniziativa";

	private static final String COUNT_BY_ID_INIZIATIVA = SELECT_COUNT_ +
			" FROM firmip_t_firma_iniziativa" +
			" WHERE id_iniziativa = :id_iniziativa";

	private static final String INSERT = "INSERT INTO firmip_t_firma_iniziativa (" +
			" id_firma_iniziativa, id_iniziativa, cognome, nome, data_nascita, luogo_nascita, tipo_documento, num_documento, indirizzo, id_comune, num_foglio)" +
			" VALUES (:id_firma_iniziativa, :id_iniziativa, :cognome, :nome, :data_nascita, :luogo_nascita, :tipo_documento, :num_documento, :indirizzo, :id_comune, :num_foglio)";

	private static final String UPDATE = "UPDATE firmip_t_firma_iniziativa" +
			" SET id_iniziativa=:id_iniziativa, cognome=:cognome, nome=:nome, data_nascita=:data_nascita, luogo_nascita=:luogo_nascita, tipo_documento=:tipo_documento, num_documento=:num_documento, indirizzo=:indirizzo, id_comune=:id_comune, num_foglio=:num_foglio"
			+
			" WHERE id_firma_iniziativa = :id_firma_iniziativa";

	private static final String DELETE = "DELETE FROM firmip_t_firma_iniziativa WHERE id_firma_iniziativa = :id_firma_iniziativa";

	private static final String MAX_NUM_FOGLIO_BY_ID_INIZIATIVA = "SELECT COALESCE(num_foglio,'0')" +
			" FROM firmip_t_firma_iniziativa" +
			" WHERE id_firma_iniziativa = (select max(id_firma_iniziativa) " +
			" FROM firmip_t_firma_iniziativa WHERE id_iniziativa = :id_iniziativa )";

	private static final String ORDER_BY_1_DESC = " ORDER BY 1 DESC";

	private static final String SEQ_ID = "SELECT nextval('firmip_t_firma_iniziativa_id_firma_iniziativa_seq')";

	@Override
	public List<FirmaIniziativaEntity> find(FirmaIniziativaFilter filter, String sorter) throws DAOException {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder sb = new StringBuilder(FIND);
			sb.append(readFilter(filter, params));
			sb.append(readSorter(sorter));
			return (List<FirmaIniziativaEntity>)getCustomNamedParameterJdbcTemplateImpl().query(sb.toString(), params, BeanPropertyRowMapper.newInstance(FirmaIniziativaEntity.class));
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::find] Errore database: " + e.getMessage(), e);
			throw new DAOException();
		}
	}

	@Override
	public List<FirmaIniziativaEntity> find(FirmaIniziativaFilter filter) throws DAOException {
		return find(filter, null);
	}

	private Object readFilter(FirmaIniziativaFilter filter, MapSqlParameterSource params) {
		StringBuilder sb = new StringBuilder();

		if (filter != null) {
			if (filter.getIdFirmaIniziativa().isPresent()) {
				sb = appendWhereOrAnd(sb).append("id_firma_iniziativa = :id_firma_iniziativa");
				params.addValue("id_firma_iniziativa", filter.getIdFirmaIniziativa().get());
				return sb;
			}
			if (filter.getIdIniziativa().isPresent()) {
				sb = appendWhereOrAnd(sb).append("id_iniziativa = :id_iniziativa");
				params.addValue("id_iniziativa", filter.getIdIniziativa().get());
			}
			if (filter.getCognome().isPresent()) {
				sb = addFilterStringEsattaOrParziale(filter, params, sb, "cognome", filter.getCognome().get());
				
			}
			if (filter.getNome().isPresent()) {
				sb = addFilterStringEsattaOrParziale(filter, params, sb, "nome", filter.getNome().get());
				
			}
			if (filter.getDataNascita().isPresent()) {
				sb = appendWhereOrAnd(sb).append("data_nascita = :data_nascita");
				params.addValue("data_nascita", filter.getDataNascita().get());
			}
			if (filter.getLuogoNascita().isPresent()) {
				sb = addFilterStringEsattaOrParziale(filter, params, sb, "luogo_nascita", filter.getLuogoNascita().get());
				
			}
			if (filter.getTipoDocumento().isPresent()) {
				sb = appendWhereOrAnd(sb).append("tipo_documento = :tipo_documento");
				params.addValue("tipo_documento", filter.getTipoDocumento().get());
			}
			if (filter.getNumDocumento().isPresent()) {
				sb = appendWhereOrAnd(sb).append("num_documento = :num_documento");
				params.addValue("num_documento", filter.getNumDocumento().get());
			}
			if (filter.getIndirizzo().isPresent()) {
				sb = addFilterStringEsattaOrParziale(filter, params, sb, "indirizzo", filter.getIndirizzo().get());
				
			}
			if (filter.getIdComune().isPresent()) {
				sb = appendWhereOrAnd(sb).append("id_comune = :id_comune");
				params.addValue("id_comune", filter.getIdComune().get());
			}
			if (filter.getCodiceIstatComune().isPresent()) {
				sb = appendWhereOrAnd(sb).append("id_comune = (select c.id_comune from firmip_d_comune c where c.codice_istat = :codice_istat_comune)");
				params.addValue("codice_istat_comune", filter.getCodiceIstatComune().get());
			}
			if (filter.getNumFoglio().isPresent()) {
				sb = appendWhereOrAnd(sb).append("num_foglio = :num_foglio");
				params.addValue("num_foglio", filter.getNumFoglio().get());
			}
		}
		return sb;
	}

	protected StringBuilder addFilterStringEsattaOrParziale(FirmaIniziativaFilter filter, MapSqlParameterSource params, StringBuilder sb, String dbFieldName, String value) {
		String namedParam = dbFieldName;
		sb = appendWhereOrAnd(sb).append(dbFieldName).append(filter.isRicercaStringheEsatte() ? " = :" : " LIKE :").append(namedParam);
		String prepost = filter.isRicercaStringheEsatte() ? "" : "%";
		params.addValue(namedParam, prepost + value + prepost);
		return sb;
	}

	private StringBuilder appendWhereOrAnd(StringBuilder sb) {
		return sb.append(sb.length() == 0 ? " WHERE " : " AND ");
	}

	private StringBuilder readSorter(String sorter) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(sorter)) {
			sb.append(" ORDER BY ").append(sorter);
		} else {
			sb.append(ORDER_BY_1_DESC);
		}
		return sb;
	}

	@Override
	public List<FirmaIniziativaEntity> findByIdIniziativa(Integer idIniziativa, String sorter) throws DAOException {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id_iniziativa", idIniziativa);
			StringBuilder sb = new StringBuilder();
			sb.append(ELENCO_BY_ID_INIZIATIVA);
			sb.append(readSorter(sorter));
			return (List<FirmaIniziativaEntity>)getCustomNamedParameterJdbcTemplateImpl().query(sb.toString(), params, BeanPropertyRowMapper.newInstance(FirmaIniziativaEntity.class));
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::findByIdIniziativa] Exception " + e.getMessage(), e);
			throw new DAOException();
		}
	}

	@Override
	public List<FirmaIniziativaEntity> findByIdIniziativa(Integer idIniziativa) throws DAOException {
		return findByIdIniziativa(idIniziativa, null);
	}

	@Override
	public int countByIdIniziativa(Integer idIniziativa) throws DAOException {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id_iniziativa", idIniziativa);
			return getCustomNamedParameterJdbcTemplateImpl().queryForInt(COUNT_BY_ID_INIZIATIVA, params);
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::countByIdIniziativa] Exception " + e.getMessage(), e);
			throw new DAOException(getMsgErrDefault());
		}
	}

	@Override
	public FirmaIniziativaEntity findById(Integer idFirmaIniziativa) throws ItemNotFoundDAOException, DAOException {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id_firma_iniziativa", idFirmaIniziativa);
			return (FirmaIniziativaEntity)getCustomNamedParameterJdbcTemplateImpl().queryForObject(FIND_BY_ID, params, BeanPropertyRowMapper.newInstance(FirmaIniziativaEntity.class));
		} catch (EmptyResultDataAccessException emptyEx) {
			LOG.error("[" + CLASS_NAME + "::findById] Elemento non trovato: idFirmaIniziativa = " + idFirmaIniziativa, emptyEx);
			throw new ItemNotFoundDAOException(String.format("Firma %s inesistente", idFirmaIniziativa), "FIRMIP-00200");
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::findById] Exception " + e.getMessage(), e);
			throw new DAOException(getMsgErrDefault());
		}
	}

	@Override
	public Integer insert(FirmaIniziativaEntity entity) throws DAOException {
		try {
			LOG.debug("[" + CLASS_NAME + "::insert] IN entity: " + entity);
			Integer idFirmaIniziativa = getCustomNamedParameterJdbcTemplateImpl().queryForInt(SEQ_ID, new MapSqlParameterSource());
			entity.setIdFirmaIniziativa(idFirmaIniziativa);
			int numRecord = getCustomNamedParameterJdbcTemplateImpl().update(INSERT, mapFirmaIniziativaEntityParameters(entity));
			LOG.debug("[" + CLASS_NAME + "::insert] Record inseriti: " + numRecord);
			return idFirmaIniziativa;
		} catch (DuplicateKeyException de) {
			LOG.error("[" + CLASS_NAME + "::insert] DuplicateKeyException for " + entity);
			throw new DAOException("Firma già presente", "FIRMIP-02100");
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::insert] Exception " + e.getMessage(), e);
			throw new DAOException();
		}
	}

	@Override
	public int update(FirmaIniziativaEntity entity) throws ItemNotFoundDAOException, DAOException {
		try {
			LOG.debug("[" + CLASS_NAME + "::update] IN entity: " + entity);
			int numRecord = getCustomNamedParameterJdbcTemplateImpl().update(UPDATE, mapFirmaIniziativaEntityParameters(entity));
			LOG.debug("[" + CLASS_NAME + "::update] Record aggiornati: " + numRecord);
			if (numRecord == 0) {
				LOG.error("[" + CLASS_NAME + "::update] Elemento non trovato idIniziativa = " + entity.getIdIniziativa());
				throw new ItemNotFoundDAOException();
			}
			return numRecord;
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::update] Exception " + e.getMessage(), e);
			throw new DAOException();
		}
	}

	@Override
	public int delete(FirmaIniziativaEntity entity) throws ItemNotFoundDAOException, DAOException {
		return delete(entity.getIdIniziativa());
	}

	@Override
	public int delete(Integer idFirmaIniziativa) throws ItemNotFoundDAOException, DAOException {
		try {
			LOG.debug("[" + CLASS_NAME + "::delete] IN idFirmaIniziativa: " + idFirmaIniziativa);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id_firma_iniziativa", idFirmaIniziativa);
			int numRecord = getCustomNamedParameterJdbcTemplateImpl().update(DELETE, params);
			LOG.debug("[" + CLASS_NAME + "::delete] Record aggiornati: " + numRecord);
			if (numRecord == 0) {
				LOG.error("[" + CLASS_NAME + "::delete] Elemento non trovato idFirmaIniziativa = " + idFirmaIniziativa);
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

	// non ritorna il numero foglio massimo di una specifica iniziativa, ma l'ultimo inserito
	@Override
	public String getMaxNumFoglio(Integer idIniziativa) throws DAOException {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id_iniziativa", idIniziativa);
			return getCustomNamedParameterJdbcTemplateImpl().queryForObject(MAX_NUM_FOGLIO_BY_ID_INIZIATIVA, params, String.class);
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::getMaxNumFoglio] Exception " + e.getMessage(), e);
			throw new DAOException(getMsgErrDefault());
		}
	}

	private MapSqlParameterSource mapFirmaIniziativaEntityParameters(FirmaIniziativaEntity entity) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id_firma_iniziativa", entity.getIdFirmaIniziativa());
		params.addValue("id_iniziativa", entity.getIdIniziativa());
		params.addValue("cognome", entity.getCognome(), Types.VARCHAR);
		params.addValue("nome", entity.getNome(), Types.VARCHAR);
		params.addValue("data_nascita", entity.getDataNascita(), Types.DATE);
		params.addValue("luogo_nascita", entity.getLuogoNascita(), Types.VARCHAR);
		params.addValue("tipo_documento", entity.getTipoDocumento(), Types.VARCHAR);
		params.addValue("num_documento", entity.getNumDocumento(), Types.VARCHAR);
		params.addValue("indirizzo", entity.getIndirizzo(), Types.VARCHAR);
		params.addValue("id_comune", entity.getIdComune());
		params.addValue("num_foglio", entity.getNumFoglio());
		return params;
	}

}
