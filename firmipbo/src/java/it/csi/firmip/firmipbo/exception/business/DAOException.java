/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.exception.business;
/*
 * Eccezione base sollevata dal layer persistenza
 * 
 */
public class DAOException extends BaseException {

	private static final long serialVersionUID = 1L;
	private final static String DEFAULT_MSG = "Riscontrata eccezione durante operazione db";
	
	public DAOException() {
		super();
	}
	public DAOException(String msg) {
		super(msg);
	}
	public DAOException(String msg, String code) {
		super(msg,code);
	}
	
	public DAOException(BaseException be) {
		super(be);
	}
	public DAOException(Throwable msg) {
		super(DEFAULT_MSG + " - "+msg.getMessage());
	}
	
	

}
