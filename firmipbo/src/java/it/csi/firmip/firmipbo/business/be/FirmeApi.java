/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.firmip.firmipbo.business.be;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.firmip.firmipbo.dto.Firma;

@Path("/firme")
@Produces(MediaType.APPLICATION_JSON)
public interface FirmeApi {

	@GET
	@Path("/by-iniziativa/{idIniziativa}")
	public Response getFirmeByIdIniziativa( @PathParam("idIniziativa") String idIniziativaPP, @QueryParam("sort") String sort, @QueryParam("fields") String fields, 
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	@GET
	@Path("/count-by-iniziativa/{idIniziativa}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getCountFirmeByIdIniziativa( @PathParam("idIniziativa") String idIniziativaPP, @QueryParam("fields") String fields, 
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	@GET
	@Path("/{id}")
	public Response getFirmaById( @PathParam("id") String idFirmaPP, @QueryParam("fields") String fields, 
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postFirma( Firma body,
    	@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putFirma( @PathParam("id") String idFirmaPP, Firma body, 
    	@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteFirma( @PathParam("id") String idFirmaPP, 
    	@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

}