/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl.dao.impl;

import org.springframework.stereotype.Component;

import it.csi.firmip.firmipbo.business.service.impl.dao.DummyDAO;

@Component
public class DummyDAOImpl extends JdbcTemplateDAO implements DummyDAO {

	@Override
	public String pingDB() {
		return (String)getCustomJdbcTemplate().queryForObject("SELECT CONCAT('Ping DB OK at ', current_timestamp )", String.class);
	}

}
