/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
package it.csi.firmip.firmipbo.business.service.impl.dao;

import it.csi.firmip.firmipbo.exception.business.DAOException;
import it.csi.firmip.iride.base.Application;
import it.csi.firmip.iride.base.Identita;
import it.csi.firmip.iride.base.Ruolo;
import it.csi.firmip.iride.base.UseCase;



public interface IrideDAO {

	public boolean testResources() throws DAOException;

	public boolean isIdentitaAutentica(Identita identita) throws DAOException;

	public Ruolo[] findRuoliForPersonaInApplication(Identita identita, Application application) throws DAOException;

	public UseCase[] findUseCasesForPersonaInApplication(Identita identita, Application application) throws DAOException;

//	public Actor[] findActorsForPersonaInApplication(Identita identita, Application application) throws DAOException;

}
