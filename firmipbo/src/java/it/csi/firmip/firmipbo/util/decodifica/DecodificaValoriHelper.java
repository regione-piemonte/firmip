/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.util.decodifica;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe di helper per il calcolo dei valori possibili per la decodifica
 *
 */
public class DecodificaValoriHelper {

	/** Classe di utilit&agrave;, da non instanziare */
	private DecodificaValoriHelper() {
	}
	
	/**
	 * Calcolo dei valori possibili.
	 * 
	 * @param arr gli elementid a cui ottenere i valori
	 * @return i valori possibili
	 */
	public static String getValoriPossibili(Decodifica[] arr) {
		int length = arr.length;
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < length; i++) {
			list.add(arr[i].getCodice());
		}
		Iterable<String> iterable = list;
		return String.join(", ", iterable);
	}
	
}
