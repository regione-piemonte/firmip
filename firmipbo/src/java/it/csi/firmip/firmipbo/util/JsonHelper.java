/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.util;

import com.google.gson.GsonBuilder;

public class JsonHelper {

	private static GsonBuilder gb = new GsonBuilder();
	
	public static String getJsonFromObject(Object obj) {
		
		return gb.create().toJson(obj);
	}
	
}
