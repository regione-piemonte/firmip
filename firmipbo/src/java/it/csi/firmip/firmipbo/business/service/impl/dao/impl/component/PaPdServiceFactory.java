/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service.impl.dao.impl.component;

import java.io.InputStream;

import org.apache.log4j.Logger;

import it.csi.csi.porte.HttpPDProxyLoader;
import it.csi.csi.porte.InfoPortaDelegata;
import it.csi.csi.porte.proxy.PDProxy;
import it.csi.csi.util.xml.PDConfigReader;
import it.csi.firmip.firmipbo.util.Constants;

public class PaPdServiceFactory {
	
	protected static Logger log = Logger.getLogger(Constants.COMPONENT_NAME + ".business");

	@SuppressWarnings("rawtypes")
	public static Object getProxy(Class nameClass, String nameFilePD) {
		log.debug("[PaPdServiceFactory::getProxy] BEGIN");
		Object pd = null;
		try {
			InputStream inputStream = nameClass.getClassLoader().getResourceAsStream("META-INF/" + nameFilePD);	
			if(inputStream != null) {
				InfoPortaDelegata info = PDConfigReader.read(inputStream);
				pd = PDProxy.newInstance(info);
			} else {
				log.error("[PaPdServiceFactory::getProxy] Configurazione della PD " + nameFilePD + " non trovata");
				throw new IllegalArgumentException("Configurazione della PD " + nameFilePD + " non trovata");				
			}
		} 
		catch (Exception e) {
			log.error("[PaPdServiceFactory::getProxy] Errore nel caricamento del file della pd: " + nameFilePD, e);
		}
		finally {
			log.debug("[PaPdServiceFactory::getProxy] END");
		}
		return pd;
	}
	
	public static Object getProxyFromRegistry(String namePropertiesPD) {
		log.debug("[PaPdServiceFactory::getProxyFromRegistry] BEGIN");
		Object pd = null;
		try {
			pd = HttpPDProxyLoader.loadProxy(namePropertiesPD);
		} catch (Exception e) {
			log.error("Errore nel caricamento del file della pd:" + namePropertiesPD, e);
		}
		log.debug("[PaPdServiceFactory::getProxyFromRegistry] END");
		return pd;
	}
}