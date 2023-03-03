/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */


package it.csi.firmip.firmipbo.exception.service;

import java.io.Serializable;

public class UnprocessableEntityException extends ServiceException implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static String MSG = "Errore: Unprocessable Entity - Invalid Input Parameter";
	
	public UnprocessableEntityException() {
		super(MSG);
	}
	 public UnprocessableEntityException(String msg)
	 {
	  super(msg);
	 }
}
