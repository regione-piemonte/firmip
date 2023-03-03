/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class EnvProperties {
	
	private final static String CLASS_NAME = "EnvProperties";
	private static Logger LOG = LoggerAccessor.getLoggerApplication();
	
	public static final String VERSION = "version";
	public static final String BUILD_TIME = "build.time";
	public static final String TARGET_LINE = "target";	

	public static final String IRIDE_WSDL = "iride.wsdl";
	public static final String IRIDE_VERIFY_HAS_RUOLO = "iride.verify.has.ruolo";
	
	public static String readFromFile(String param) {
		Properties properties = new Properties();
		InputStream stream = EnvProperties.class.getClassLoader()
				.getResourceAsStream("/env.properties");
		String ret = "";
		try {
			properties.load(stream);
			ret = properties.getProperty(param);
		} catch (IOException e) {
			LOG.error("["+CLASS_NAME+"::readFromFile param:" + param + " ret:" + ret, e);
		}
		return ret;
	}
	
	public static String[] readArrayFromFile(String param) {
		Properties properties = new Properties();
		InputStream stream = EnvProperties.class.getClassLoader()
				.getResourceAsStream("/env.properties");
		String str = "";
		String ret[] = new String[0];
		try {
			properties.load(stream);
			str = properties.getProperty(param);
			ret = Arrays.asList(str.split(",")).stream()
				.filter(x -> !StringUtils.isBlank(x))
				.toArray(String[]::new);
		} catch (IOException e) {
			LOG.error(CLASS_NAME+"::readreadListFromFileFromFile param:" + param + " str:" + str, e);
		}
		return ret;
	}
	
	public static List<String> readListFromFile(String param) {
		Properties properties = new Properties();
		InputStream stream = EnvProperties.class.getClassLoader()
				.getResourceAsStream("/env.properties");
		String str = "";
		List<String> ret = new ArrayList<String>();
		try {
			properties.load(stream);
			str = properties.getProperty(param);
			ret = Arrays.asList(str.split(",")).stream()
				.filter(x -> !StringUtils.isBlank(x))
				.collect(Collectors.toList());
		} catch (IOException e) {
			LOG.error(CLASS_NAME+"::readreadListFromFileFromFile param:" + param + " str:" + str, e);
		}
		return ret;
	}
	
	public static Integer readIntegerFromFile(String param) {
		String retStr = null;
		Integer ret = null;
		try {
			retStr = readFromFile(param);
			if (retStr!=null) {
				ret = Integer.parseInt(retStr.trim());
			}
		} catch (NumberFormatException e) {
			LOG.debug(CLASS_NAME+"::readFromFile NumberFormatException for param: " + param + "  value: " + retStr );
		}
		return ret;
	}
	
}
