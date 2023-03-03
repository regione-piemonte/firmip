/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import it.csi.firmip.firmipbo.business.service.LoginService;
import it.csi.firmip.firmipbo.business.service.impl.dao.IrideDAO;
import it.csi.firmip.firmipbo.dto.UserInfo;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.firmipbo.util.Constants;
import it.csi.firmip.firmipbo.util.EnvProperties;
import it.csi.firmip.firmipbo.util.LoggerAccessor;
import it.csi.firmip.iride.base.Application;
import it.csi.firmip.iride.base.Identita;
import it.csi.firmip.iride.base.Ruolo;	

@Component
public class LoginServiceImpl implements LoginService {

	private final static String CLASS_NAME = "LoginServiceImpl";
	private Logger LOG = LoggerAccessor.getLoggerBusiness();
	private final static boolean BYPASS_IRIDE;
	private final static boolean VERIFY_HAS_RUOLO_IRIDE = "true".equalsIgnoreCase(EnvProperties.readFromFile(EnvProperties.IRIDE_VERIFY_HAS_RUOLO));

	@Autowired
	@Qualifier("soap")
	IrideDAO irideDAO;
	
	static {
		String target = EnvProperties.readFromFile(EnvProperties.TARGET_LINE);
		BYPASS_IRIDE = "dev".equalsIgnoreCase(target);
	}
	
	@Override
	public UserInfo login(Identita identita) throws BusinessException {
		try {
			LOG.info("[" + CLASS_NAME + "::login] IN identita=" + logIdentita(identita));
			if (VERIFY_HAS_RUOLO_IRIDE && !verifyHasRuolo(identita, Constants.IRIDE_RUOLO)) {
				LOG.error("[" + CLASS_NAME + "::login] Ruolo " + Constants.IRIDE_RUOLO + " non presente for identita=" + logIdentita(identita));
				throw new BusinessException();
			}
			UserInfo result = new UserInfo();
			result.setNome(identita.getNome());
			result.setCognome(identita.getCognome());
			result.setCodFisc(identita.getCodFiscale());
			return result;
		} catch (DAOException daoe) {
			LOG.warn("[" + CLASS_NAME + "::login] DAOException for identita=" + logIdentita(identita));
			throw new BusinessException(daoe);
		} catch (Exception e) {
			LOG.error("[" + CLASS_NAME + "::login] Exception for identita=" + logIdentita(identita));
			throw new BusinessException();
		}
	}

	private String logIdentita(Identita identita) {
		return identita.getCodFiscale() + "/" + identita.getCognome() + "/" + identita.getNome();
	}

	private boolean verifyHasRuolo(Identita identita, String ruoloToVerify) throws DAOException {
		if (BYPASS_IRIDE) return true;
		Ruolo[] ruoli = irideDAO.findRuoliForPersonaInApplication(identita, new Application(Constants.IRIDE_APPLICATION_CODE));
		LOG.debug("[" + CLASS_NAME + "::verifyHasRuolo] ruoli=" + ruoliToString(ruoli));
		return ruoli!=null && ruoli.length>0;
	}

	private String ruoliToString(Ruolo[] ruoli) {
		return ruoli==null?"":Arrays.stream(ruoli)
			.map(r -> r.getCodiceRuolo())
			.collect(Collectors.joining(", "));
	}

	@Override
	public UserInfo getCurrentUser(HttpServletRequest req) {
		LOG.debug("[" + CLASS_NAME + "::getCurrentUser] IN req=" + req);
		return (UserInfo)req.getSession().getAttribute(Constants.SESSION_USERINFO);
	}

	@Override
	public void localLogout(HttpServletRequest req) {
		UserInfo userInfo = getCurrentUser(req);
		req.getSession().invalidate();
		LOG.info("[" + CLASS_NAME + "::localLogout] userInfo=" + userInfo);
	}

}
