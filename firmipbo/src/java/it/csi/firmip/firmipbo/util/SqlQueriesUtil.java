/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;


public class SqlQueriesUtil {
	 /** Logger to trace info */
    private final static Logger LOG = LoggerAccessor.getLoggerIntegration();
   
    /**
    * @return the setShowSql
    */
   private static boolean isSetShowSql() {
	   String variable = System.getenv("setShowSQL");	   
      return LOG.isDebugEnabled() || (variable != null && variable.equals("true"));
   }
   
   private static boolean isSetReplaceSqlParameters() {
       return true;
   }
   
   
   private static void print(String msg) {
	   String variable = System.getenv("local");
 	   if (variable != null && "true".equals(variable)) {
		    System.out.println(msg);
	   }
		 else {
			 LOG.debug(msg);
	   }
   }
   
   
   private static String formatParamValue(String name, Object theValue){
       if (theValue == null){
           return ("NULL");
       } else if (String.class.isInstance(theValue)){          
           return theValue.toString();
       } else if (Date.class.isInstance(theValue)){
           final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
           final String fecha = "'".concat(df.format(theValue)).concat("'");
           return fecha;
       } else if (Timestamp.class.isInstance(theValue)){
           final DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
           final String fechaHora= "'".concat(df.format(theValue)).concat("'");
           return fechaHora;
       } else if (ArrayList.class.isInstance(theValue)){
          final String lista = theValue.toString();
          final String nuevaLista = lista.replaceAll("\\[", "").replaceAll("\\]", "");           
          return nuevaLista;
       } else if (Number.class.isInstance(theValue)){          
           return theValue.toString();
       }  else {
    	   print("formatParamValue Parameter type not implemented=".concat(name).concat(" TYPE=").concat(theValue.getClass().toString()));
       }
       return(theValue.toString());        
   }  
   
    /**
     * Display the query with parameters if setShowSql=true
     * @param sql
     * @param parameter
     */
    public static void debugSQL(String sql, SqlParameterSource parameter) {
        MapSqlParameterSource paramMap=null;
        BeanPropertySqlParameterSource paramBean=null;
        EmptySqlParameterSource paramEmpty=null;
        
        if (isSetShowSql()){
            if (parameter!=null && isSetReplaceSqlParameters()){
                try {
                    if (MapSqlParameterSource.class.isAssignableFrom(parameter.getClass())){                    
                        paramMap = MapSqlParameterSource.class.cast(parameter);
                        debugSQL(sql, paramMap);
                    } else if (BeanPropertySqlParameterSource.class.isAssignableFrom(parameter.getClass())){    
                        paramBean = BeanPropertySqlParameterSource.class.cast(parameter);
                        debugSQL(sql, paramBean);
                    } else if (EmptySqlParameterSource.class.isAssignableFrom(parameter.getClass())){    
                        debugSQL(sql, paramEmpty);
                    } else {
                        LOG.debug("The parameter don't have a indentified type");
                    }                        
                } catch (ClassCastException e){
                    LOG.error("Casting error with the parameter: " +  parameter.getClass().getName());
                    paramMap=null;
                }
            } else {
                debugSQL(sql);
            }
        }
    }
    /**
     * Display the query with parameters if setShowSql=true
     * @param sql
     */
    public static void debugSQL(String sql) {
    	 String variable = System.getenv("local");
    	 boolean isLocal = false;
    	 
    	 if (variable != null && "true".equals(variable)) {
    		 isLocal = true;
    	 }
    	 else {
    		 isLocal = false;
    	 }
    		 
        if (isSetShowSql() && sql!=null && !isLocal){
            LOG.debug("SQL_ORIGINAL: \t".concat(sql));              
        }else if(isSetShowSql() && sql!=null && isLocal) {
        	System.out.println("SQL_ORIGINAL: \t".concat(sql));
        }
    }
    /**
     * Display the query with parameters if setShowSql=true
     * @param sql
     * @param parameter
     */
    private static void debugSQL(String sql, BeanPropertySqlParameterSource parameter) {
        String newSql = sql;
        StringBuilder paramResult = new StringBuilder();
        String[] paramsArray = parameter.getReadablePropertyNames();
        String variable = System.getenv("local");
   	 boolean isLocal = false;
   	 
   	 if (variable != null && "true".equals(variable)) {
   		 isLocal = true;
   	 }
   	 else {
   		 isLocal = false;
   	 }
        for (String paramName : paramsArray) {
            final String paramValue = parameter.getValue(paramName).toString();
            newSql = newSql.replaceAll(":".concat(paramName), paramValue);
            paramResult.append(paramName).append("=").append(paramValue).append(", ");
        }
        
        if (isSetShowSql() && sql!=null && !isLocal){
        	LOG.debug("SQL_BEAN_REPLACED: \t".concat(newSql));            
        }else if(isSetShowSql() && sql!=null && isLocal) {
        	System.out.println("SQL_BEAN_REPLACED: \t".concat(newSql));
        }
        
        
    }
    /**
     * Display the query with parameters if setShowSql=true
     * 
     * @param sql
     * @param parameter
     */
    private static void debugSQL(String sql, MapSqlParameterSource parameter) {
        String newSql = sql;
        Iterator<Entry<String, Object>> parametros = parameter.getValues().entrySet().iterator();
        while (parametros.hasNext() && newSql.lastIndexOf(":") != -1) {
            final Entry<String, Object> nuevoParam = parametros.next();
            final String paramName = nuevoParam.getKey();
            final Object valor = nuevoParam.getValue();
            final String paramValue = formatParamValue(paramName, valor);
            newSql = newSql.replaceAll(":".concat(paramName), paramValue.concat(" "));
            newSql = newSql.replaceAll(":".concat(paramName).concat(" "), paramValue.concat(" "));
            newSql = newSql.replaceAll(":".concat(paramName).concat(","), paramValue.concat(","));
            newSql = newSql.replaceAll(":".concat(paramName).concat("\\)"), paramValue.concat(")"));
        }
        print("SQL_MAP_REPLACED: \t".concat(newSql).toString());
    }
    /**
     * Display the query with parameters if setShowSql=true
     * @param sql
     * @param parameter
     */
    private static void debugSQL(String sql, EmptySqlParameterSource parameter) {    
        if (isSetShowSql()){
        	print("SQL_NO_PARAMETER: \t".concat(sql));
        }
    }
    /**
     * Display the query with parameters if setShowSql=true
     * @param sql
     * @param paramMap
     */
    public static void debugSQL(String sql, Map<String, ?> paramMap) {
        if (isSetShowSql()){
            print("SQL_ORIGINAL: \t".concat(sql));
            if (paramMap!=null && !paramMap.isEmpty()){
                print("PARAMS: \t".concat(Arrays.toString(paramMap.entrySet().toArray())));
            }
        }        
    }
    /**
     * Display the query with parameters if setShowSql=true
     * @param sql
     */
    public static void debugSQL(String[] listaSql) {
        for (int i = 0; i < listaSql.length; i++) {
            debugSQL(listaSql[i]);            
        }        
    }
    public static void debugSQL(String sql, Map<String, ?>[] batchValues) {
        if (isSetShowSql()){
        	print("SQL_ORIGINAL: \t".concat(sql));
            if (batchValues!=null && !(batchValues.length > 0)){
            	print("PARAMS: \t".concat(batchValues.toString()));
            }
        }
    }
    public static void debugSQL(String sql, SqlParameterSource[] batchArgs) {
        // TODO Auto-generated method stub
    }

}
