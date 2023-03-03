/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.be.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import it.csi.firmip.firmipbo.exception.service.UnprocessableEntityException;

/**
 * 
 * @author Paolo
 *
 * @since 1.0.0 - 20/03/2020
 */
public class BaseApiImpl {
	
    public static final Pattern PATTERN_NUMERIC_SIMPLE_NON_ZERO = Pattern.compile("^([1-9]+[0-9]* | [1-9])$");
    public static final DateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat sdf_yyyyMMdd_HHmmss = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    
    boolean isNumericSimpleNonZero(CharSequence input) {
        return input != null && PATTERN_NUMERIC_SIMPLE_NON_ZERO.matcher(input).matches();
    }
    
    //
    // String
    //
	protected String validaStringRequired(String input) throws UnprocessableEntityException {
		if (StringUtils.isEmpty(input)) {
			throw new UnprocessableEntityException("String required.");
		}
		return input;
	}
	// String Trim
	protected String validaStringTrim(String input) {
		if (input==null) {
			return null;
		}
		return input.trim();
	}
	protected String validaStringTrimRequired(String input) throws UnprocessableEntityException {
		String result = validaStringRequired(input);
		return result.trim();
	}
	// String TrimUpper
	protected String validaStringTrimUpper(String input) {
		if (input==null) {
			return null;
		}
		return input.trim().toUpperCase();
	}
	protected String validaStringTrimUpperRequired(String input) throws UnprocessableEntityException {
		if (StringUtils.isEmpty(input)) {
			throw new UnprocessableEntityException("String required.");
		}
		return input.trim().toUpperCase();
	}
	
	
    //
    // Long
    //
    protected Long validaLong(String input) throws UnprocessableEntityException {
		try {
			if (StringUtils.isEmpty(input)) {
				return null;
			}
			return Long.valueOf(input);
		} catch(NumberFormatException nfe) {
	         throw new UnprocessableEntityException("Long non corretto (expected: Long ; actual:"+input+" )");
		}
	}
    protected Long validaLong(String input, Long defaultValue) throws UnprocessableEntityException {
		Integer result = validaInteger(input);
		return result!=null?result:defaultValue;
	}
	protected Long validaLongRequired(String input) throws UnprocessableEntityException {
		try {
			if (StringUtils.isEmpty(input)) {
				throw new UnprocessableEntityException("Long required.");
			}
			return Long.valueOf(input);
		} catch(NumberFormatException nfe) {
	         throw new UnprocessableEntityException("Long required non corretto (expected: Long ; actual:"+input+" )");
		}
	}
	
	//
	// Integer
	//
    protected Integer validaInteger(String input) throws UnprocessableEntityException {
		try {
			if (StringUtils.isEmpty(input)) {
				return null;
			}
			return Integer.valueOf(input);
		} catch(NumberFormatException nfe) {
	         throw new UnprocessableEntityException("Integer non corretto (expected: Integer ; actual:"+input+" )");
		}
	}
    protected Integer validaInteger(String input, Integer defaultValue) throws UnprocessableEntityException {
		Integer result = validaInteger(input);
		return result!=null?result:defaultValue;
	}
    protected Integer validaInteger0Based(String input) throws UnprocessableEntityException {
    	Integer result = validaInteger(input);
    	if (result!=null && result<0) {
    		throw new UnprocessableEntityException("Integer 0 based.");
    	}
    	return result;
	}
    protected Integer validaInteger1Based(String input) throws UnprocessableEntityException {
    	Integer result = validaInteger(input);
    	if (result!=null && result<1) {
    		throw new UnprocessableEntityException("Integer 1 based.");
    	}
    	return result;
	}
	protected Integer validaIntegerRequired(String input) throws UnprocessableEntityException {
		try {
			if (StringUtils.isEmpty(input)) {
				throw new UnprocessableEntityException("Integer required.");
			}
			return Integer.valueOf(input);
		} catch(NumberFormatException nfe) {
	         throw new UnprocessableEntityException("Integer required non corretto (expected: Integer ; actual:"+input+" )");
		}
	}
    protected Integer validaInteger1BasedRequired(String input) throws UnprocessableEntityException {
    	Integer result = validaIntegerRequired(input);
    	if (result<1) {
    		throw new UnprocessableEntityException("Integer 1 based.");
    	}
    	return result;
	}
    
	//
	// Boolean
	//
    protected Boolean validaBoolean(String input) throws UnprocessableEntityException {
		try {
			if (StringUtils.isEmpty(input)) {
				return null;
			}
			return Boolean.valueOf(input);
		} catch(Exception e) {
	         throw new UnprocessableEntityException("Boolean non corretto (expected: Boolean ; actual:"+input+" )");
		}
	}
    protected Boolean validaBoolean(String input, Boolean defaultValue) throws UnprocessableEntityException {
    	Boolean result = validaBoolean(input);
		return result!=null?result:defaultValue;
	}
	protected Boolean validaBooleanRequired(String input) throws UnprocessableEntityException {
		try {
			if (StringUtils.isEmpty(input)) {
				throw new UnprocessableEntityException("Boolean required.");
			}
			return Boolean.valueOf(input);
		} catch(Exception e) {
	         throw new UnprocessableEntityException("Boolean required non corretto (expected: Boolean ; actual:"+input+" )");
		}
	}
	
	//
	// Date
    protected Date validaDate(String input) throws UnprocessableEntityException {
		try {
			if (StringUtils.isEmpty(input)) {
				return null;
			}
	        Date date = sdf_yyyyMMdd.parse(input);
	        return date;
	    } catch (ParseException e) {
	    	throw new UnprocessableEntityException("Date invalid format (expected: 'yyyy-MM-dd' ; actual:"+input+" )");
	    }
	}
	protected Boolean validaDateRequired(String input) throws UnprocessableEntityException {
		try {
			if (StringUtils.isEmpty(input)) {
				throw new UnprocessableEntityException("Date required.");
			}
			return Boolean.valueOf(input);
		} catch(Exception e) {
			throw new UnprocessableEntityException("Date invalid format (expected: 'yyyy-MM-dd' ; actual:"+input+" )");
		}
	}
	
	//
	// DateTime
	protected Date validaDateTime(String input) {
		try {
			if (StringUtils.isEmpty(input)) {
				return null;
			}
	        Date date = sdf_yyyyMMdd_HHmmss.parse(input);
	        return date;
	    } catch (ParseException e) {
	    	throw new UnprocessableEntityException("DateTime invalid format (expected: 'yyyy-MM-dd'T'HH:mm:ss' ; actual:"+input+" )");
	    }
	}
	protected Boolean validaDateTimeRequired(String input) throws UnprocessableEntityException {
		try {
			if (StringUtils.isEmpty(input)) {
				throw new UnprocessableEntityException("DateTime required.");
			}
			return Boolean.valueOf(input);
		} catch(Exception e) {
			throw new UnprocessableEntityException("DateTime invalid format (expected: 'yyyy-MM-dd'T'HH:mm:ss' ; actual:"+input+" )");
		}
	}
	
	//
	// GENERICO
	protected String validaFields(String input) throws UnprocessableEntityException {
		// TODO Auto-generated method stub
		return input;
	}
	
	//
	// COMUNE
	protected String validaCodiceIstatComune(String input) throws UnprocessableEntityException {
		String result = validaStringTrim(input);
		if (result!=null) {
			validaStringNumericoOfLen(result, 6);
		}
		return result;
	}
	protected String validaCodiceIstatComuneRequired(String input) throws UnprocessableEntityException {
		String result = validaStringRequired(input);
		validaStringNumericoOfLen(result, 6);
		return result;
	}

	protected void validaStringNumericoOfLen(String input, int len) {
		if (input.length()!=len ) {
			throw new UnprocessableEntityException("CodiceIstatComune fornito non valido (not of length "+len+").");
		}
		if (!input.matches("[0-9]+")) {
			throw new UnprocessableEntityException("CodiceIstatComune fornito non valido (not [0-9]+).");
		}
	}
	
}
