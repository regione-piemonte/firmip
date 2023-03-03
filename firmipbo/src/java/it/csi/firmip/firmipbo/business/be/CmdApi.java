/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.firmip.firmipbo.business.be;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/cmd")
public interface CmdApi  {
   
    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public Response ping( @QueryParam("db") String dbQP,
        	@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    
    @GET
    @Path("/version")
    @Produces({ MediaType.TEXT_PLAIN })
    public Response version(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders);
    
    @GET
    @Path("/build-info")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response buildInfo(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders);
    
}
