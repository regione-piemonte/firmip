/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl.dao.impl;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import it.csi.firmip.firmipbo.business.service.impl.dao.IrideDAO;
import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.firmipbo.util.EnvProperties;
import it.csi.firmip.firmipbo.util.LoggerAccessor;
import it.csi.firmip.iride.base.Application;
import it.csi.firmip.iride.base.CSIException;
import it.csi.firmip.iride.base.Identita;
import it.csi.firmip.iride.base.PolicyEnforcerBaseService;
import it.csi.firmip.iride.base.PolicyEnforcerBaseServiceServiceLocator;
import it.csi.firmip.iride.base.Ruolo;
import it.csi.firmip.iride.base.UseCase;

@Component
@Qualifier("soap")
public class IrideSoapDAOImpl implements IrideDAO {

	private static final String CLASS_NAME = "IrideSoapDAOImpl";
	private static final Logger LOG = LoggerAccessor.getLoggerIntegration();

	public static final URL WSDL_LOCATION;
	public static final QName SERVICE = new QName("http://www.csi.it/iride/Iride2Service", "Iride2ServiceProxy");

	private volatile PolicyEnforcerBaseService port = null;

	static {
		URL url = null;
		try {
			url = new URL(EnvProperties.readFromFile(EnvProperties.IRIDE_WSDL));
		} catch (MalformedURLException e) {
			LOG.error("Can not initialize the default wsdl from " + EnvProperties.readFromFile(EnvProperties.IRIDE_WSDL));
		}
		WSDL_LOCATION = url;
	}

	private PolicyEnforcerBaseService getPort() throws Exception {
		if (port == null) {
			synchronized (this) {
				if (port == null) {
					try {
						PolicyEnforcerBaseServiceServiceLocator locator = new PolicyEnforcerBaseServiceServiceLocator();

						String endpointUrl = EnvProperties.readFromFile(EnvProperties.IRIDE_WSDL);

						locator.setPolicyEnforcerBaseEndpointAddress(endpointUrl);
						port = locator.getPolicyEnforcerBase();
					} catch (Exception e) {
						LOG.error("[IrideSoapDAOImpl::getPort] Exception locator.getPolicyEnforcerBase " + WSDL_LOCATION, e);
						throw new Exception();
					}
				}
			}
		}
		return port;
	}

	// TODO
	@Override
	public boolean testResources() throws DAOException {
		return false;
	}

	// TODO
	@Override
	public boolean isIdentitaAutentica(Identita identita) throws DAOException {
		return false;
	}

	@Override
	public Ruolo[] findRuoliForPersonaInApplication(Identita identita, Application application) throws DAOException {
		try {
			LOG.info("findRuoliForPersonaInApplication] IN identita="+identita.getCodFiscale()+" application="+application.getId());
			return getPort().findRuoliForPersonaInApplication(identita, application);
		} catch (CSIException e) {
			LOG.error("findRuoliForPersonaInApplication] IdentitaNonAutenticaException", e);
			throw new DAOException();
		} catch (Exception e) {
			LOG.error("findRuoliForPersonaInApplication] IRIDE_ERRORE_FIND_RUOLI_FOR_PERSONA", e);
			throw new DAOException();
		}
	}

	@Override
	public UseCase[] findUseCasesForPersonaInApplication(Identita identita, Application application)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}


}
