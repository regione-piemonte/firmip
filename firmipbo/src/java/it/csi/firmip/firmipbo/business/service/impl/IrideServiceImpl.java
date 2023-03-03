/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import it.csi.firmip.firmipbo.business.service.IrideService;
import it.csi.firmip.firmipbo.exception.business.BusinessException;
import it.csi.firmip.firmipbo.util.LoggerAccessor;
import it.csi.firmip.iride.base.Identita;




@Component
public class IrideServiceImpl implements IrideService /*, Monitorable*/ {

	private static final String CLASS_NAME = "IniziativaServiceImpl";
	private static final Logger LOG = LoggerAccessor.getLoggerBusiness();



    // Metodo di utility
    public static Identita getIdentitaFromToken ( String token ) throws BusinessException {
        Identita identita = new Identita ();

        int slash1Index = token.indexOf ( '/' );
        if ( slash1Index == -1 ) {
            throwTokenNonAutentico ();
        }
        identita.setCodFiscale ( token.substring ( 0, slash1Index ) );
        int slash2Index = token.indexOf ( '/', slash1Index + 1 );
        if ( slash2Index == -1 ) {
            throwTokenNonAutentico ();
        }
        identita.setNome ( token.substring ( slash1Index + 1, slash2Index ) );
        int slash3Index = token.indexOf ( '/', slash2Index + 1 );
        if ( slash3Index == -1 ) {
            throwTokenNonAutentico ();
        }
        identita.setCognome ( token.substring ( slash2Index + 1, slash3Index ) );
        int slash4Index = token.indexOf ( '/', slash3Index + 1 );
        if ( slash4Index == -1 ) {
            throwTokenNonAutentico ();
        }
        identita.setIdProvider ( token.substring ( slash3Index + 1, slash4Index ) );
        int slash5Index = token.indexOf ( '/', slash4Index + 1 );
        if ( slash5Index == -1 ) {
            throwTokenNonAutentico ();
        }
        identita.setTimestamp ( token.substring ( slash4Index + 1, slash5Index ) );
        int slash6Index = token.indexOf ( '/', slash5Index + 1 );
        if ( slash6Index == -1 ) {
            throwTokenNonAutentico ();
        } else {
            identita.setLivelloAutenticazione ( Integer.parseInt ( token.substring ( slash5Index + 1, slash6Index ) ) );
            identita.setMac ( token.substring ( slash6Index + 1 ) );
        }
        return identita;
    }

    private static void throwTokenNonAutentico() {
        throw new BusinessException();
    }


}
