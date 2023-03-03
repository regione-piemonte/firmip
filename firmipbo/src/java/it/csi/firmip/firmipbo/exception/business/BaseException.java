/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.exception.business;

public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_EXCEPTION_MSG = "Errore generico ";

	private String code = "FIRMIP-00000";
	
	public BaseException() {
		super(DEFAULT_EXCEPTION_MSG);
		
	}

	public BaseException(String msg) {
		super(msg);
	}
	public BaseException(String msg, String code) {
		super(msg);
		this.code = code;
	}
	
	public BaseException(Throwable thr) {
		super(thr);
	}
	public BaseException(Throwable thr, String code) {
		super(thr);
		this.code = code;
	}
	
	//
	public String getCode() {
		return code;
	}
	
}
