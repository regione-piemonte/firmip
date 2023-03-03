/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import it.csi.firmip.firmipbo.business.service.AuditService;
import it.csi.firmip.firmipbo.business.service.LoginService;
import it.csi.firmip.firmipbo.business.service.impl.IrideServiceImpl;
import it.csi.firmip.firmipbo.dto.UserInfo;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.util.Constants;
import it.csi.firmip.firmipbo.util.EnvProperties;
import it.csi.firmip.iride.base.Identita;

/**
 * Inserisce in sessione:
 * <ul>
 * <li>l'identit&agrave; digitale relativa all'utente autenticato.
 * <li>l'oggetto <code>currentUser</code>
 * </ul>
 * Funziona da adapter tra il filter del metodo di autenticaizone previsto e la logica applicativa.
 *
 * @author CSIPiemonte
 */
public class IrideIdAdapterFilter implements Filter {

	public static final String AUTH_ID_MARKER = "Shib-Iride-IdentitaDigitale";

	/**  */
	protected static final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME + ".security");

    public static final String target = EnvProperties.readFromFile(EnvProperties.TARGET_LINE);
    public static final boolean test = target.startsWith("tst-");
    
	@Autowired
	LoginService loginService;
	@Autowired
	AuditService auditService;

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fchn) throws IOException, ServletException {
		LOG.debug("[IrideIdAdapterFilter::dofilter()] BEGIN");
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		if (request.getSession().getAttribute(Constants.SESSION_IRIDE_ID) == null) {
			String marker = getToken(request);
			if (marker != null) {
				Identita identita = null;
				try {
					identita = getIdentitaFromToken( normalizeToken ( marker ) );
					LOG.debug("[IrideIdAdapterFilter::dofilter()] IN login " + identita.getCodFiscale());
					UserInfo userInfo = loginService.login(identita);
					request.getSession().setAttribute(Constants.SESSION_IRIDE_ID, identita);
					request.getSession().setAttribute(Constants.SESSION_USERINFO, userInfo);
					auditService.login(request, "login", identita.toString()); // Dopo session
					LOG.debug("[IrideIdAdapterFilter::dofilter()]  logged " + identita.getCodFiscale());
					fchn.doFilter(req, res);
//				} catch (MalformedIdTokenException e) {
//					LOG.error("[IrideIdAdapterFilter::doFilter] " + e.toString(), e);
//					response.sendError(HttpServletResponse.SC_FORBIDDEN, "MalformedIdTokenException");
				} catch (BusinessException e) {
					LOG.error("[IrideIdAdapterFilter::doFilter] BusinessException LoginException per " + identita.getCodFiscale() + " " + identita.getCognome() + " " + identita.getNome());
					response.sendError(HttpServletResponse.SC_FORBIDDEN, "Attenzione! Nessun ruolo e' stato definito per l'utente");
				}
			} else {
				// il marcatore deve sempre essere presente altrimenti e' una
				// condizione di errore (escluse le pagine home e di servizio)
				if (mustCheckPage(request.getRequestURI())) {
					LOG.error("[IrideIdAdapterFilter::doFilter] Tentativo di accesso a pagina senza token di sicurezza");
					response.sendError(HttpServletResponse.SC_FORBIDDEN, "Tentativo di accesso a pagina senza token di sicurezza");
				}
			}
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("[IrideIdAdapterFilter::dofilter()] session already open for " + request.getSession().getAttribute(Constants.SESSION_IRIDE_ID) + "\n"
						+ request.getSession().getAttribute(Constants.SESSION_USERINFO));
			}
			fchn.doFilter(req, res);
		}
	}

	private Identita getIdentitaFromToken(String normalizeToken) {
		if (test) {
			LOG.debug("[IrideIdAdapterFilter::dofilter()] normalizeToken: " + normalizeToken);
		}
		return IrideServiceImpl.getIdentitaFromToken(normalizeToken);
	}

	private boolean mustCheckPage(String requestURI) {
		return true;
	}

	public void destroy() {
		// NOP
	}

	private static final String DEVMODE_INIT_PARAM = "devmode";

	private boolean devmode = false;

	public void init(FilterConfig fc) throws ServletException {
		// must provide autowiring support to inject SpringBean
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, fc.getServletContext());

		String sDevmode = fc.getInitParameter(DEVMODE_INIT_PARAM);
		if ("true".equals(sDevmode)) {
			devmode = true;
		} else {
			devmode = false;
		}
	}

	public String getToken(HttpServletRequest httpreq) {
		String marker = (String)httpreq.getHeader(AUTH_ID_MARKER);
		if (marker == null && devmode) {
			return getTokenDevMode(httpreq);
		} else {
			try {
				// gestione dell'encoding
				String decodedMarker = new String(marker.getBytes("ISO-8859-1"), "UTF-8");
				return decodedMarker;
			} catch (java.io.UnsupportedEncodingException e) {
				// se la decodifica non funziona comunque sempre meglio restituire
				// il marker originale non decodificato
				return marker;
			}
		}
	}

	private String getTokenDevMode(HttpServletRequest httpreq) {
		String marker = (String)httpreq.getParameter(AUTH_ID_MARKER);
		return marker;
	}

	private String normalizeToken(String token) {
		return token;
	}

}
