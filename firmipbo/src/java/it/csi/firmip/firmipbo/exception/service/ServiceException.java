/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */


package it.csi.firmip.firmipbo.exception.service;

import java.io.Serializable;
import java.io.StringWriter;

import it.csi.firmip.firmipbo.exception.business.BusinessException;

public class ServiceException extends RuntimeException implements Serializable {
	
	
	private String code = "FORMIP-00000";
	
	private static final long serialVersionUID = 1L;

	public ServiceException()
	 {
	  super();
	 }
	
	 public ServiceException(String message, Throwable cause)
	 {
	  super(message, cause);
	 }
	 
	 public ServiceException(String message, Throwable cause,String code)
	 {
	  super(message, cause);
	  this.code = code;
	 }
	 
	 public ServiceException(Throwable cause)
	 {
	  super(cause);
	 }
	 
	 public ServiceException(String msg)
	 {
	  super(msg);
	 }
	 
	 public ServiceException(String msg,String code)
	 {
	  super(msg);
	  this.code = code;
	 }
	 public ServiceException(BusinessException be)
	 {
	  super(be.getMessage());
	  this.code = be.getCode();
	 }
	 
	 
	 public String getInternalErrorMessage()
	 {
	  Throwable cause = this.getCause();
	  if(cause != null)
	  {
	   StringWriter sw = new StringWriter();
	   return sw.toString();
	  }
	  else return getMessage();
		  
	 }

	public String getErrCode() {
		return code;
	}
	 
	 
}
