/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.be.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.firmip.firmipbo.business.be.IniziativeApi;
import it.csi.firmip.firmipbo.business.service.AuditService;
import it.csi.firmip.firmipbo.business.service.FirmaService;
import it.csi.firmip.firmipbo.business.service.IniziativaService;
import it.csi.firmip.firmipbo.business.service.impl.entity.FirmaIniziativaFilter;
import it.csi.firmip.firmipbo.dto.Firma;
import it.csi.firmip.firmipbo.dto.Iniziativa;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundBusinessException;
import it.csi.firmip.firmipbo.exception.service.ResourceNotFoundException;
import it.csi.firmip.firmipbo.exception.service.ServiceException;
import it.csi.firmip.firmipbo.exception.service.UnprocessableEntityException;
import it.csi.firmip.firmipbo.util.LoggerAccessor;

@Component
public class IniziativeApiImpl extends BaseApiImpl implements IniziativeApi {
	
	private static final String CLASS_NAME = "IniziativeApiImpl";
	private static final Logger LOG = LoggerAccessor.getLoggerBusiness();
	
	@Autowired
	IniziativaService iniziativaService;
	@Autowired
	FirmaService firmaService;
	@Autowired
	AuditService auditService;
	
	//
	@Override
	public Response getIniziative(String fields,
		SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			auditService.read(httpRequest, "getIniziative", "");
			List<Iniziativa> ris = iniziativaService.getElencoIniziative();
			return Response.ok(ris).build();
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getIniziative] BusinessException");
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getIniziative] Throwable", ex);
			throw new ServiceException("Errore generico servizio getIniziative");
		} 
	}
	
	@Override
	public Response getIniziativaById(String idIniziativaPP, String fields,
		SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			LOG.info("[" + CLASS_NAME + "::getIniziativaById] IN idIniziativaPP = " + idIniziativaPP);
			Integer idIniziativa = validaInteger1BasedRequired(idIniziativaPP);
			auditService.read(httpRequest, "getIniziativaById", idIniziativaPP);
			Iniziativa ris = iniziativaService.getIniziativaById(idIniziativa);
			return Response.ok(ris).build();
		} catch (UnprocessableEntityException uee) {
			LOG.warn("[" + CLASS_NAME + "::getIniziativaById] UnprocessableEntityException for idIniziativaPP = " + idIniziativaPP);
			throw uee;
		} catch (ItemNotFoundBusinessException notFoundEx) {
			LOG.warn("[" + CLASS_NAME + "::getIniziativaById] ItemNotFoundBusinessException for idIniziativaPP = " + idIniziativaPP);
			throw new ResourceNotFoundException(notFoundEx);
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getIniziativaById] BusinessException for idIniziativaPP = " + idIniziativaPP);
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getIniziativaById] Throwable for idIniziativaPP = " + idIniziativaPP, ex);
			throw new ServiceException("Errore generico servizio getIniziativaById");
		} 
	}

	@Override
	public Response postIniziativa(Iniziativa body, 
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			LOG.info("[" + CLASS_NAME + "::postIniziativa] IN body = " + body);
			Iniziativa ris = null;
			body = validaIniziativaTrim(body);
			auditService.insert(httpRequest, "postIniziativa", body.toString());
			ris = iniziativaService.createIniziativa(body);
			return Response.ok(ris).build();
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::postIniziativa] BusinessException for body = " + body);
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::postIniziativa] Throwable for body = " + body, ex);
			throw new ServiceException("Errore generico servizio postIniziativa");
		}
	}

	@Override
	public Response putIniziativa(String idIniziativaPP, Iniziativa body, 
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			LOG.info("[" + CLASS_NAME + "::putIniziativa] IN idIniziativaPP = " + idIniziativaPP + " body = " + body);
			Integer idIniziativa = validaInteger1BasedRequired(idIniziativaPP);
			if (!idIniziativa.equals(body.getId())) {
				LOG.error("[" + CLASS_NAME + "::putIniziativa] idIniziativaPP = " + idIniziativaPP + " diverso dal body " + body.getId());
				throw new ServiceException("Errore idIniziativa incongruente");
			}
			body = validaIniziativaTrim(body);
			auditService.update(httpRequest, "putIniziativa", body.toString());
			Iniziativa ris = iniziativaService.updateIniziativa(body);
			return Response.ok(ris).build();
		} catch (UnprocessableEntityException uee) {
			LOG.warn("[" + CLASS_NAME + "::putIniziativa] UnprocessableEntityException for idIniziativaPP = " + idIniziativaPP);
			throw uee;
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::putIniziativa] BusinessException for idIniziativaPP = " + idIniziativaPP);
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::putIniziativa] Throwable for idIniziativaPP = " + idIniziativaPP, ex);
			throw new ServiceException("Errore generico servizio putIniziativa");
		}
	}

	private Iniziativa validaIniziativaTrim(Iniziativa body) {
		body.setTitolo(validaStringTrimRequired(body.getTitolo()));
		if (body.getTipo()==null) {
			LOG.info("[" + CLASS_NAME + "::validaIniziativaTrim] Tipo iniziativa non valorizzato  body = " + body);
			throw new UnprocessableEntityException("Tipo iniziativa obbligatorio non valorizzato.");
		} else {
			validaIntegerRequired(body.getTipo().getId()==null?null:body.getTipo().getId().toString());
		}
		return body;
	}

	@Override
	public Response deleteIniziativa(String idIniziativaPP, 
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			LOG.info("[" + CLASS_NAME + "::deleteIniziativa] IN idIniziativaPP = " + idIniziativaPP);
			Integer idIniziativa = validaInteger1BasedRequired(idIniziativaPP);
			auditService.delete(httpRequest, "deleteIniziativa", idIniziativaPP);
			iniziativaService.deleteIniziativa(idIniziativa);
			return Response.ok().build();
		} catch (UnprocessableEntityException uee) {
			LOG.warn("[" + CLASS_NAME + "::deleteIniziativa] UnprocessableEntityException for idIniziativaPP = " + idIniziativaPP);
			throw uee;
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::deleteIniziativa] BusinessException for idIniziativaPP = " + idIniziativaPP);
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::deleteIniziativa] Throwable for idIniziativaPP = " + idIniziativaPP, ex);
			throw new ServiceException("Errore generico servizio deleteIniziativa");
		}
	}

    //
    // FIRME
    //
	@Override
	public Response getFirmeByIdIniziativa(String idIniziativaPP, String sort, 
			String cognomeQP, String nomeQP, 
			String dataNascitaQP, String luogoNascitaQP,
			String indirizzoQP, 
			String idComuneQP, String codiceComuneQP,
			String tipoDocumentoQP, String numDocumentoQP,
			String numFoglioQP,
			String ricercaStringheEsatteQP,
			String fields,
		SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			LOG.info("[" + CLASS_NAME + "::getFirmeByIdIniziativa] IN idIniziativaPP = " + idIniziativaPP + " sort = " + sort);
			FirmaIniziativaFilter filter = new FirmaIniziativaFilter(validaInteger1BasedRequired(idIniziativaPP));
			filter.setCognome(validaStringTrimUpper(cognomeQP));
			filter.setNome(validaStringTrimUpper(nomeQP));
			filter.setDataNascita(validaDate(dataNascitaQP));
			filter.setLuogoNascita(validaStringTrimUpper(luogoNascitaQP));
			filter.setIndirizzo(validaStringTrimUpper(indirizzoQP));
			filter.setIdComune(validaInteger(idComuneQP));
			filter.setCodiceIstatComune(validaStringTrim(codiceComuneQP));
			filter.setTipoDocumento(validaStringTrimUpper(tipoDocumentoQP));
			filter.setNumDocumento(validaStringTrimUpper(numDocumentoQP));
			filter.setNumFoglio(validaInteger(numFoglioQP));
			filter.setRicercaStringheEsatte(Boolean.TRUE.equals(validaBoolean(ricercaStringheEsatteQP)));
			auditService.read(httpRequest, "getFirmeByIdIniziativa", filter.toString());
			List<Firma> ris = firmaService.getFirme(filter, sort);
			return Response.ok(ris).build();
		} catch (ItemNotFoundBusinessException notFoundEx) {
			LOG.warn("[" + CLASS_NAME + "::getFirmeByIdIniziativa] ItemNotFoundBusinessException for idIniziativaPP = " + idIniziativaPP);
			throw new ResourceNotFoundException(notFoundEx);
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getFirmeByIdIniziativa] BusinessException");
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getFirmeByIdIniziativa] Throwable", ex);
			throw new ServiceException("Errore generico servizio getFirmeByIdIniziativa");
		} 
	}
	
	@Override
	public Response getCountFirmeByIdIniziativa(String idIniziativaPP, String fields,
		SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			LOG.info("[" + CLASS_NAME + "::getCountFirmeByIdIniziativa] IN idIniziativaPP = " + idIniziativaPP);
			Integer idIniziativa = validaInteger1BasedRequired(idIniziativaPP);
			auditService.read(httpRequest, "getCountFirmeByIdIniziativa", idIniziativaPP);
			Integer ris = firmaService.getCountFirmeByIdIniziativa(idIniziativa);
			return Response.ok(ris).build();
		} catch (ItemNotFoundBusinessException notFoundEx) {
			LOG.warn("[" + CLASS_NAME + "::getCountFirmeByIdIniziativa] ItemNotFoundBusinessException for idIniziativaPP = " + idIniziativaPP);
			throw new ResourceNotFoundException(notFoundEx);
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getCountFirmeByIdIniziativa] BusinessException");
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getCountFirmeByIdIniziativa] Throwable", ex);
			throw new ServiceException("Errore generico servizio getCountFirmeByIdIniziativa");
		} 
	}
	
	@Override
	public Response getFirmaById(String idIniziativaPP, String idFirmaPP, String fields,
		SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			LOG.info("[" + CLASS_NAME + "::getFirmaById] IN idIniziativaPP = " + idIniziativaPP + " idFirmaPP = " + idFirmaPP);
			Integer idIniziativa = validaIntegerRequired(idIniziativaPP);
			Integer idFirma = validaInteger1BasedRequired(idFirmaPP);
			auditService.read(httpRequest, "getFirmaById", idIniziativaPP + ";" + idFirmaPP);
			Firma ris = firmaService.getFirmaById(idFirma);
			if (!ris.getIdIniziativa().equals(idIniziativa)) {
				LOG.error("[" + CLASS_NAME + "::getFirmaById] Errore firma incongruente con l'iniziativa idIniziativaPP = " + idIniziativaPP + " idFirmaPP = " + idFirmaPP + " con idIniziativa:" + idIniziativa);
				throw new BusinessException("Errore firma incongruente con l'iniziativa");
			}
			return Response.ok(ris).build();
		} catch (UnprocessableEntityException uee) {
			LOG.warn("[" + CLASS_NAME + "::getFirmaById] UnprocessableEntityException for idIniziativaPP = " + idIniziativaPP + " idFirmaPP = " + idFirmaPP);
			throw uee;
		} catch (ItemNotFoundBusinessException notFoundEx) {
			LOG.warn("[" + CLASS_NAME + "::getFirmaById] ItemNotFoundBusinessException for idIniziativaPP = " + idIniziativaPP + " idFirmaPP = " + idFirmaPP);
			throw new ResourceNotFoundException(notFoundEx);
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getFirmaById] BusinessException for idFirmaPP = " + idFirmaPP);
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getFirmaById] Throwable for idFirmaPP = " + idFirmaPP, ex);
			throw new ServiceException("Errore generico servizio getFirmaById");
		} 
	}

	@Override
	public Response postFirma(String idIniziativaPP, Firma body, 
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			LOG.info("[" + CLASS_NAME + "::postFirma] IN idIniziativaPP = " + idIniziativaPP + " body = " + body);
			Integer idIniziativa = validaInteger1BasedRequired(idIniziativaPP);
			if (!idIniziativa.equals(body.getIdIniziativa())) {
				LOG.error("[" + CLASS_NAME + "::postFirma] idIniziativaPP = " + idIniziativaPP + " diverso dal body " + body.getIdIniziativa());
				throw new BusinessException("Errore idIniziativa incongruente");
			}
			body = validaFirmaTrimUpper(body);
			auditService.insert(httpRequest, "postFirma", idIniziativaPP + ";" + body);
			Firma ris = firmaService.createFirma(body);
			return Response.ok(ris).build();
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::postFirma] BusinessException for body = " + body);
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::postFirma] Throwable for body = " + body, ex);
			throw new ServiceException("Errore generico servizio postFirma");
		}
	}

	@Override
	public Response putFirma(String idIniziativaPP, String idFirmaPP, Firma body, 
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			LOG.info("[" + CLASS_NAME + "::putFirma] IN idIniziativaPP = " + idIniziativaPP + " idFirmaPP = " + idFirmaPP + " body = " + body);
			Integer idIniziativa = validaInteger1BasedRequired(idIniziativaPP);
			Integer idFirma = validaInteger1BasedRequired(idFirmaPP);
			if (!idIniziativa.equals(body.getIdIniziativa())) {
				LOG.error("[" + CLASS_NAME + "::putFirma] idIniziativaPP = " + idIniziativaPP + " diverso dal body " + body.getIdIniziativa());
				throw new BusinessException("Errore idIniziativa incongruente");
			}
			if (!idFirma.equals(body.getId())) {
				LOG.error("[" + CLASS_NAME + "::putFirma] idFirmaPP = " + idFirmaPP + " diverso dal body " + body.getId());
				throw new BusinessException("Errore idFirma incongruente");
			}
			body = validaFirmaTrimUpper(body);
			auditService.update(httpRequest, "putFirma", idIniziativaPP + ";" + body);
			Firma ris = firmaService.updateFirma(body);
			return Response.ok(ris).build();
		} catch (UnprocessableEntityException uee) {
			LOG.warn("[" + CLASS_NAME + "::putFirma] UnprocessableEntityException for idFirmaPP = " + idFirmaPP);
			throw uee;
		} catch (ItemNotFoundBusinessException notFoundEx) {
			LOG.warn("[" + CLASS_NAME + "::putFirma] ItemNotFoundBusinessException for idIniziativaPP = " + idIniziativaPP);
			throw new ResourceNotFoundException(notFoundEx);
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::putFirma] BusinessException for idFirmaPP = " + idFirmaPP);
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::putFirma] Throwable for idFirmaPP = " + idFirmaPP, ex);
			throw new ServiceException("Errore generico servizio putFirma");
		}
	}

	// Valida ed effettua Trim e UpperCase sui dati in input per inserimento e aggiornamento FIRME
	// Obbligatori solo 4 campi: nome, cognome, data di nascità e luogo
	private Firma validaFirmaTrimUpper(Firma body) {
		body.setIdIniziativa(validaInteger1BasedRequired(body.getIdIniziativa()==null?null:body.getIdIniziativa().toString()));
		body.setCognome(validaStringTrimUpper(body.getCognome()==null?null:body.getCognome().toString()));
		body.setNome(validaStringTrimUpper(body.getNome()==null?null:body.getNome().toString()));
		if (body.getDataNascita()==null) {
			LOG.error("[" + CLASS_NAME + "::validaFirmaTrimUpper] DataNascita obbligatorio non valorizzato.");
			throw new UnprocessableEntityException("DataNascita obbligatorio non valorizzato.");
		}
		body.setLuogoNascita(validaStringTrimUpperRequired(body.getLuogoNascita()));
		// Campi OPT
		body.setIndirizzo(validaStringTrimUpper(body.getIndirizzo()));
		if (body.getComune()==null) {
			LOG.warn("[" + CLASS_NAME + "::validaFirmaTrimUpper] Comune optional non valorizzato.");
//			throw new UnprocessableEntityException("Comune obbligatorio non valorizzato.");
		} else {
			body.getComune().setCodice(validaCodiceIstatComune(validaCodiceIstatComune(body.getComune().getCodice())));
		}
		body.setTipoDocumento(validaStringTrimUpper(body.getTipoDocumento()));
		body.setNumDocumento(validaStringTrimUpper(body.getNumDocumento()));
		body.setNumFoglio(validaInteger(body.getNumFoglio()==null?null:body.getNumFoglio().toString()));
		return body;
	}

	@Override
	public Response deleteFirma(String idIniziativaPP, String idFirmaPP, 
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ServiceException {
		try {
			LOG.info("[" + CLASS_NAME + "::deleteFirma] IN idIniziativaPP = " + idIniziativaPP + " idFirmaPP = " + idFirmaPP);
			Integer idIniziativa = validaInteger1BasedRequired(idIniziativaPP);
			Integer idFirma = validaInteger1BasedRequired(idFirmaPP);
			auditService.delete(httpRequest, "deleteFirma", idIniziativaPP + ";" + idFirmaPP);
			firmaService.deleteFirma(idFirma);
			return Response.ok().build();
		} catch (UnprocessableEntityException uee) {
			LOG.warn("[" + CLASS_NAME + "::deleteFirma] UnprocessableEntityException for idFirmaPP = " + idFirmaPP);
			throw uee;
		} catch (ItemNotFoundBusinessException notFoundEx) {
			LOG.warn("[" + CLASS_NAME + "::deleteFirma] ItemNotFoundBusinessException for idIniziativaPP = " + idIniziativaPP);
			throw new ResourceNotFoundException(notFoundEx);
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::deleteFirma] BusinessException for idFirmaPP = " + idFirmaPP);
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::deleteFirma] Throwable for idFirmaPP = " + idFirmaPP, ex);
			throw new ServiceException("Errore generico servizio deleteFirma");
		}
	}

	@Override
	public Response getMaxNumFoglio(String idIniziativaPP, String fields,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		try {
			LOG.info("[" + CLASS_NAME + "::getMaxNumFoglio] IN idIniziativaPP = " + idIniziativaPP);
			Integer idIniziativa = validaInteger1BasedRequired(idIniziativaPP);
			auditService.read(httpRequest, "getMaxNumFoglio", idIniziativaPP);
			String ris = firmaService.getMaxNumFoglio(idIniziativa);
			return Response.ok(ris).build();
		} catch (ItemNotFoundBusinessException notFoundEx) {
			LOG.warn("[" + CLASS_NAME + "::getMaxNumFoglio] ItemNotFoundBusinessException for idIniziativaPP = " + idIniziativaPP);
			throw new ResourceNotFoundException(notFoundEx);
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getMaxNumFoglio] BusinessException");
			throw new ServiceException(be);
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getMaxNumFoglio] Throwable", ex);
			throw new ServiceException("Errore generico servizio getMaxNumFoglio");
		}
	}

}
