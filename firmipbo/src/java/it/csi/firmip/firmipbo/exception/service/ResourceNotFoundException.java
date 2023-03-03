/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */


package it.csi.firmip.firmipbo.exception.service;

import java.io.Serializable;

import it.csi.firmip.firmipbo.exception.business.BusinessException;

public class ResourceNotFoundException extends ServiceException implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static String MSG = "Errore: Risorsa non trovata";
	
	public ResourceNotFoundException() {
		super(MSG);
	}
	 public ResourceNotFoundException(String msg)
	 {
	  super(msg);
	 }
	 public ResourceNotFoundException(String msg,String code)
	 {
	  super(msg,code);
	 }
	 public ResourceNotFoundException(BusinessException be)
	 {
	  super(be);
	 }
}
