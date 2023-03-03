/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl.dao.impl;

import java.sql.Types;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.stereotype.Component;

import it.csi.firmip.firmipbo.business.service.impl.dao.AuditDAO;
import it.csi.firmip.firmipbo.business.service.impl.entity.AuditEntity;
import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.firmipbo.util.Constants;

@Component
public class AuditDAOImpl extends JdbcTemplateDAO implements AuditDAO {

	private final static String CLASS_NAME = "AuditDAOImpl";

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String INSERT = " INSERT INTO CSI_LOG_AUDIT " + 
													"(data_ora, id_app, ip_address, utente, operazione, ogg_oper, key_oper) "+
													"VALUES(now(), ?,  ?,  ?,  ?,  ?,  ?) ";

	private static final String AUDIT_ID_APP = Constants.COMPONENT_NAME;

	
	@Override
	public int insert(AuditEntity auditEntity) throws DAOException {
		
		LOG.debug("[" + CLASS_NAME + "::insert] IN entity: " + auditEntity);
		try {
			return jdbcTemplate.update(INSERT,
				new SqlParameterValue(Types.VARCHAR, AUDIT_ID_APP),
				new SqlParameterValue(Types.VARCHAR, StringUtils.left(auditEntity.getIpAddress(),40)),
				new SqlParameterValue(Types.VARCHAR, StringUtils.left(auditEntity.getUtente(),100) ),
				new SqlParameterValue(Types.VARCHAR, StringUtils.left(auditEntity.getOperazione().name(),50)),
				new SqlParameterValue(Types.VARCHAR, StringUtils.left(auditEntity.getOggettoOperazione(),500)),
				new SqlParameterValue(Types.VARCHAR, StringUtils.left(auditEntity.getKeyOperazione(),500))
				);		
		} catch (Exception ex ) {
			LOG.error("[" + CLASS_NAME + "::insert] Errore database: "+ex.getMessage(),ex);
			throw new DAOException("ERRORE INSERIMENTO AUDIT");
		}
	}

}
