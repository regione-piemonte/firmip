/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.exception.business;

public class ItemNotFoundDAOException extends DAOException {
	private static final long serialVersionUID = 1L;
	private final static String DEFAULT_MSG = "Elemento non trovato su db";
	public ItemNotFoundDAOException() {
		super(DEFAULT_MSG);
	}
	public ItemNotFoundDAOException(String msg) {
		super(msg);
	}
	public ItemNotFoundDAOException(String msg, String code) {
		super(msg,code);
	}
	
	public ItemNotFoundDAOException(Throwable msg) {
		super(DEFAULT_MSG + " - "+msg.getMessage());
	}

}
