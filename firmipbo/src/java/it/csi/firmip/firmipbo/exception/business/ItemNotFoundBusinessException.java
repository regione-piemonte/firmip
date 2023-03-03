/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.exception.business;

public class ItemNotFoundBusinessException extends BusinessException {
	private static final long serialVersionUID = 1L;
	private final static String DEFAULT_MSG = "Elemento non trovato";
	
	public ItemNotFoundBusinessException() {
		super(DEFAULT_MSG);
	}
	public ItemNotFoundBusinessException(String msg) {
		super(msg);
	}
	public ItemNotFoundBusinessException(String msg, String code) {
		super(msg,code);	
	}
	
	public ItemNotFoundBusinessException(BaseException be) {
		super(be);
	}
	public ItemNotFoundBusinessException(Throwable thr) {
		super(thr.getMessage());
	}
	public ItemNotFoundBusinessException(Throwable thr, String code) {
		super(thr,code);	
	}
}
