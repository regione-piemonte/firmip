/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.firmip.firmipbo.business.service.ComuneService;
import it.csi.firmip.firmipbo.business.service.FirmaService;
import it.csi.firmip.firmipbo.business.service.IniziativaService;
import it.csi.firmip.firmipbo.business.service.impl.dao.FirmaIniziativaDAO;
import it.csi.firmip.firmipbo.business.service.impl.entity.FirmaIniziativaEntity;
import it.csi.firmip.firmipbo.business.service.impl.entity.FirmaIniziativaFilter;
import it.csi.firmip.firmipbo.business.service.impl.mapper.FirmaMapper;
import it.csi.firmip.firmipbo.dto.Comune;
import it.csi.firmip.firmipbo.dto.Firma;
import it.csi.firmip.firmipbo.dto.Iniziativa;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundBusinessException;
import it.csi.firmip.firmipbo.exception.business.ItemNotFoundDAOException;
import it.csi.firmip.firmipbo.exception.service.ServiceException;
import it.csi.firmip.firmipbo.util.LoggerAccessor;

/**
 * Metodi di business relativi alle firme
 *
 * @see Firma
 * 
 */
@Component
public class FirmaServiceImpl implements FirmaService {

	private static final String CLASS_NAME = "FirmaServiceImpl";
	private static final Logger LOG = LoggerAccessor.getLoggerBusiness();

	@Autowired
	FirmaIniziativaDAO firmaIniziativaDAO;
	@Autowired
	IniziativaService iniziativaService;
	@Autowired
	ComuneService comuneService;

	@Override
	public List<Firma> getFirme(FirmaIniziativaFilter filter, String sort) throws ItemNotFoundBusinessException, BusinessException {
		try {
			if (filter.getIdIniziativa().isPresent()) {
				verificaEsistenzaIniziativa(filter.getIdIniziativa().get()); // per tornare 404 se non esiste via ItemNotFoundBusinessException
			}
			List<Firma> result = firmaIniziativaDAO.find(filter, sort).stream().map(f -> FirmaMapper.buildFromEntities(f, getComuneOptional(f))).collect(Collectors.toList());
			LOG.info("[" + CLASS_NAME + "::getFirme] result.size = " + String.valueOf((result != null) ? result.size() : "null"));
			return result;
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getFirme] BusinessException for filter.getIdIniziativa()=" + filter.getIdIniziativa());
			throw be;
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getFirme] Errore generico servizio getFirme", ex);
			throw new ServiceException("Errore generico servizio getFirme");
		}
	}

	private Comune getComuneOptional(FirmaIniziativaEntity f) {
		return f.getIdComune() == null ? null : comuneService.getComuneById(f.getIdComune());
	}

	@Override
	public List<Firma> getFirmeByIdIniziativa(Integer idIniziativa, String sort) throws ItemNotFoundBusinessException, BusinessException {
		try {
			verificaEsistenzaIniziativa(idIniziativa); // per tornare 404 se non esiste via ItemNotFoundBusinessException
			List<Firma> result = firmaIniziativaDAO.findByIdIniziativa(idIniziativa, sort).stream().map(f -> FirmaMapper.buildFromEntities(f, getComuneOptional(f))).collect(Collectors.toList());
			LOG.info("[" + CLASS_NAME + "::getFirmeByIdIniziativa] result.size = " + String.valueOf((result != null) ? result.size() : "null"));
			return result;
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getFirmeByIdIniziativa] BusinessException for idIniziativa=" + idIniziativa);
			throw be;
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getFirmeByIdIniziativa] Errore generico servizio getFirmeByIdIniziativa", ex);
			throw new ServiceException("Errore generico servizio getFirmeByIdIniziativa");
		}
	}

	@Override
	public List<Firma> getFirmeByIdIniziativa(Integer idIniziativa) throws ItemNotFoundBusinessException, BusinessException {
		return getFirmeByIdIniziativa(idIniziativa, null);
	}

	@Override
	public int getCountFirmeByIdIniziativa(Integer idIniziativa) throws ItemNotFoundBusinessException, BusinessException {
		try {
			int result = firmaIniziativaDAO.countByIdIniziativa(idIniziativa);
			if (result == 0) {
				verificaEsistenzaIniziativa(idIniziativa); // per tornare 404 se non esiste via ItemNotFoundBusinessException
			}
			LOG.info("[" + CLASS_NAME + "::getCountFirmeByIdIniziativa] result = " + result);
			return result;
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getCountFirmeByIdIniziativa] BusinessException for idIniziativa=" + idIniziativa);
			throw be;
		} catch (Throwable ex) {
			LOG.error("[" + CLASS_NAME + "::getCountFirmeByIdIniziativa] Errore generico servizio getCountFirmeByIdIniziativa", ex);
			throw new ServiceException("Errore generico servizio getCountFirmeByIdIniziativa");
		}
	}

	private Iniziativa verificaEsistenzaIniziativa(Integer idIniziativa) throws ItemNotFoundBusinessException, BusinessException {
		return iniziativaService.getIniziativaById(idIniziativa);
	}

	@Override
	public Firma getFirmaById(Integer idFirma) throws ItemNotFoundBusinessException, BusinessException {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("[" + CLASS_NAME + "::getFirmaById] IN idFirma = " + idFirma);
			}
			FirmaIniziativaEntity entity = firmaIniziativaDAO.findById(idFirma);
			return FirmaMapper.buildFromEntities(entity, getComuneOptional(entity));
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::getFirmaById] BusinessException for idFirma=" + idFirma);
			throw be;
		} catch (ItemNotFoundDAOException notFoundEx) {
			LOG.error("[" + CLASS_NAME + "::getFirmaById] ItemNotFoundDAOException for idFirma = " + idFirma);
			throw new ItemNotFoundBusinessException(notFoundEx);
		} catch (DAOException e) {
			LOG.error("[" + CLASS_NAME + "::getFirmaById] DAOException for idFirma = " + idFirma, e);
			throw new BusinessException("Errore generico servizio getFirmaById");
		}
	}

	@Override
	public Firma createFirma(Firma body) throws BusinessException {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("[" + CLASS_NAME + "::createFirma] IN body = " + body);
			}
			validaFirma(body);
			FirmaIniziativaEntity entity = FirmaMapper.buildFromDTO(body, getIdComuneByCodiceIstatOptional(body.getComune()));
			Integer id = firmaIniziativaDAO.insert(entity);
			body.setId(id);
			return body;
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::createFirma] BusinessException for idFirma=" + body.getId());
			throw be;
		} catch (DAOException daoe) {
			LOG.error("[" + CLASS_NAME + "::createFirma] DAOException for body = " + body, daoe);
			throw new BusinessException(daoe);
		}
	}

	private Integer getIdComuneByCodiceIstatOptional(Comune c) {
		return (c == null || StringUtils.isEmpty(c.getCodice())) ? null : comuneService.getIdComuneByCodiceIstat(c.getCodice());
	}

	private void validaFirma(Firma f) throws BusinessException {
		if (f == null) {
			LOG.warn("[" + CLASS_NAME + "::validaFirma] BusinessException FIRMIP-02010 Firma non valida for firma=" + f);
			throw new BusinessException("Firma non valida", "FIRMIP-02010");
		}
		if (StringUtils.isBlank(f.getCognome())) {
			LOG.warn("[" + CLASS_NAME + "::validaFirma] BusinessException FIRMIP-02011 Firma con cognome non valido for firma=" + f);
			throw new BusinessException("Firma con cognome non valido", "FIRMIP-02011");
		}
		if (StringUtils.isBlank(f.getNome())) {
			LOG.warn("[" + CLASS_NAME + "::validaFirma] BusinessException FIRMIP-02012 Firma con nome non valido for firma=" + f);
			throw new BusinessException("Firma con nome non valido", "FIRMIP-02012");
		}
		if (StringUtils.isBlank(f.getLuogoNascita())) {
			LOG.warn("[" + CLASS_NAME + "::validaFirma] BusinessException FIRMIP-02013 Firma con luogoNascita non valido for firma=" + f);
			throw new BusinessException("Firma con luogoNascita non valido", "FIRMIP-02013");
		}
		if (!isMaggiorenne(f.getDataNascita())) {
			LOG.warn("[" + CLASS_NAME + "::validaFirma] BusinessException FIRMIP-02014 Firma con dataNascita non valida for firma=" + f);
			throw new BusinessException("Firma con dataNascita non valida", "FIRMIP-02014");
		}
	}

	private boolean isMaggiorenne(LocalDate dataNascita) {
		if (dataNascita == null)
			return false;

		LocalDate today = LocalDate.now();

		Period p = Period.between(dataNascita, today);
		long p2 = ChronoUnit.DAYS.between(dataNascita, today);
		System.out.println("You are " + p.getYears() + " years, " + p.getMonths() +
				" months, and " + p.getDays() +
				" days old. (" + p2 + " days total)");
		return p.getYears() >= 18 ? true : false;
	}

	@Override
	public Firma updateFirma(Firma body) throws ItemNotFoundBusinessException, BusinessException {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("[" + CLASS_NAME + "::updateFirma] IN body = " + body);
			}
			validaFirma(body);
			FirmaIniziativaEntity entity = FirmaMapper.buildFromDTO(body, getIdComuneByCodiceIstatOptional(body.getComune()));
			int rows = firmaIniziativaDAO.update(entity);
			return getFirmaById(body.getId());
		} catch (BusinessException be) {
			LOG.warn("[" + CLASS_NAME + "::updateFirma] BusinessException for idFirma=" + body.getId());
			throw be;
		} catch (ItemNotFoundDAOException notFoundEx) {
			LOG.error("[" + CLASS_NAME + "::updateFirma] ItemNotFoundDAOException for idFirma = " + body.getId());
			throw new ItemNotFoundBusinessException();
		} catch (DAOException e) {
			LOG.error("[" + CLASS_NAME + "::updateFirma] DAOException for body = " + body, e);
			throw new BusinessException("Errore generico servizio updateFirma");
		}
	}

	@Override
	public void deleteFirma(Integer idFirma) throws ItemNotFoundBusinessException, BusinessException {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("[" + CLASS_NAME + "::deleteFirma] IN idFirma = " + idFirma);
			}
			int rows = firmaIniziativaDAO.delete(idFirma);
		} catch (ItemNotFoundDAOException notFoundEx) {
			LOG.error("[" + CLASS_NAME + "::deleteFirma] ItemNotFoundDAOException for idFirma = " + idFirma);
			throw new ItemNotFoundBusinessException();
		} catch (DAOException e) {
			LOG.error("[" + CLASS_NAME + "::deleteFirma] DAOException for idFirma = " + idFirma, e);
			throw new BusinessException("Errore generico servizio deleteFirma");
		}
	}

	@Override
	public String getMaxNumFoglio(Integer idIniziativa) throws ItemNotFoundBusinessException, BusinessException {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("[" + CLASS_NAME + "::getMaxNumFoglio] IN idIniziativa = " + idIniziativa);
			}
			String result = firmaIniziativaDAO.getMaxNumFoglio(idIniziativa);
			LOG.info("[" + CLASS_NAME + "::getMaxNumFoglio] result = " + result);
			return result;
		} catch (ItemNotFoundDAOException notFoundEx) {
			LOG.error("[" + CLASS_NAME + "::getMaxNumFoglio] ItemNotFoundDAOException for idIniziativa = " + idIniziativa);
			throw new ItemNotFoundBusinessException();
		} catch (DAOException e) {
			LOG.error("[" + CLASS_NAME + "::getMaxNumFoglio] DAOException for idIniziativa = " + idIniziativa, e);
			throw new BusinessException("Errore generico servizio deleteFirma");
		}
	}

}
