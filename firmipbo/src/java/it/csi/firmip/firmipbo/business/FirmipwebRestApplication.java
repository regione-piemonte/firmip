/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.business;
/*
 * Classe che rappresenta la rest application
 */
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import it.csi.firmip.firmipbo.business.be.impl.ComuniApiImpl;
import it.csi.firmip.firmipbo.business.be.impl.CmdApiImpl;
import it.csi.firmip.firmipbo.exception.service.handlers.ResourceNotFoundExceptionHandler;
import it.csi.firmip.firmipbo.exception.service.handlers.ServiceExceptionHandler;

// il path della annotation ApplicationPath indica la pruma parte della URI ed e' comune a tutte le resource
@ApplicationPath("restfacade/be")
public class FirmipwebRestApplication extends Application{
	private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();
    
    public FirmipwebRestApplication(){
    	/*
    	 * Posso registrare più di una resource, purche' ognuna risponda ad un path diverso.
    	 *
    	 */
    	 // Elenco resource 
         singletons.add(new CmdApiImpl());
         
         // Firmipbo
         singletons.add(new ComuniApiImpl());
         
//         empty.add(ResourceNotFoundExceptionHandler.class);
//         empty.add(ServiceExceptionHandler.class);
    }
    @Override
    public Set<Class<?>> getClasses() {
         return empty;
    }
    @Override
    public Set<Object> getSingletons() {
         return singletons;
    }

}

