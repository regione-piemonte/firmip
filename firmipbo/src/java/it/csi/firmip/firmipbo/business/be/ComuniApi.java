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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/comuni")
@Produces(MediaType.APPLICATION_JSON)
public interface ComuniApi  {
   
    @GET
    public Response getComuni( @QueryParam("codiceRegione") String codiceRegioneQP, @QueryParam("siglaProvincia") String siglaProvinciaQP,
    	@QueryParam("codiceIstat") String codiceIstatQP, @QueryParam("nome") String nomeQP, 
    	@QueryParam("idIniziativa") String idIniziativaQP, 
    	@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    
    @GET
    @Path("/by-istat-regione/{codiceRegione}")
    public Response getComuniByIstatRegione( @PathParam("codiceRegione") String codiceRegionePP, @QueryParam("fields") String fields,
    	@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    @GET
    @Path("/{id}")
    public Response getComuneById( @PathParam("id") String idPP, @QueryParam("fields") String fields,
    	@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    
    @GET
    @Path("/by-codice-istat/{codiceIstat}")
    public Response getComuniByCodiceIstat( @PathParam("codiceIstat") String codiceIstatPP, @QueryParam("fields") String fields,
    	@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}