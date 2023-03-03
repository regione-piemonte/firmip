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
import it.csi.firmip.firmipbo.dto.Iniziativa;

@Path("/iniziative")
@Produces({ "application/json" })
public interface IniziativeApi {

	@GET
	public Response getIniziative( @QueryParam("fields") String fields, 
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	@GET
	@Path("/{id}")
	public Response getIniziativaById( @PathParam("id") String idIniziativaPP, @QueryParam("fields") String fields, 
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postIniziativa( Iniziativa body,
    	@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putIniziativa( @PathParam("id") String idIniziativaPP, Iniziativa body, 
    	@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteIniziativa( @PathParam("id") String idIniziativaPP, 
    	@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    //
    // FIRME
    //
	@GET
	@Path("/{id}/firme")
	public Response getFirmeByIdIniziativa( @PathParam("id") String idIniziativaPP, @QueryParam("sort") String sort,
		@QueryParam("cognome") String cognomeQP, @QueryParam("nome") String nomeQP, 
		@QueryParam("dataNascita") String dataNascitaQP, @QueryParam("luogoNascita") String luogoNascitaQP,
		@QueryParam("indirizzo") String indirizzoQP, 
		@QueryParam("idComune") String idComuneQP, @QueryParam("codiceComune") String codiceComuneQP,
		@QueryParam("tipoDocumento") String tipoDocumentoQP, @QueryParam("numDocumento") String numDocumentoQP,
		@QueryParam("numFoglio") String numFoglioQP,
		@QueryParam("ricercaStringheEsatte") String ricercaStringheEsatteQP,
		@QueryParam("fields") String fields, 
		@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	@GET
	@Path("/{id}/firme/count")
    @Consumes(MediaType.TEXT_PLAIN)
	public Response getCountFirmeByIdIniziativa( @PathParam("id") String idIniziativaPP, @QueryParam("fields") String fields, 
		@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

	@GET
	@Path("/{id}/firme/{idFirma}")
	public Response getFirmaById( @PathParam("id") String idIniziativaPP, @PathParam("idFirma") String idFirmaPP, @QueryParam("fields") String fields, 
		@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    @POST
	@Path("/{id}/firme")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postFirma( @PathParam("id") String idIniziativaPP, Firma body,
    	@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    @PUT
    @Path("/{id}/firme/{idFirma}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putFirma( @PathParam("id") String idIniziativaPP, @PathParam("idFirma") String idFirmaPP, Firma body, 
    	@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @DELETE
    @Path("/{id}/firme/{idFirma}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteFirma( @PathParam("id") String idIniziativaPP, @PathParam("idFirma") String idFirmaPP, 
    	@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

	@GET
	@Path("/{id}/firme/max-num-foglio")
    @Consumes(MediaType.TEXT_PLAIN)
	public Response getMaxNumFoglio( @PathParam("id") String idIniziativaPP, @QueryParam("fields") String fields, 
		@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
}