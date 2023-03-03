/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.exception.business;

public class BusinessException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static final String  DEFAULT_MSG = "Errore su layer business, operazione non eseguita";
	
	public BusinessException() {
		super(DEFAULT_MSG);
	}
	public BusinessException(String msg) {
		super(msg);
	}
	public BusinessException(String msg, String code) {
		super(msg,code);
	}
	
	//
	public BusinessException(BaseException baseE) {
		super(baseE.getMessage(), baseE.getCode());
	}
	
	//
	public BusinessException(Throwable thr) {
		super(thr.getMessage());
	}
	public BusinessException(Throwable thr, String code) {
		super(thr,code);
	}

}
