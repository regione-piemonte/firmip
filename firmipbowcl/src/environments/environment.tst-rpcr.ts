/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import {defaultEnvironment} from "./default.environment";

export const environment = {
  ...defaultEnvironment,
  APIUrl: './restfacade/be/',
  identitaIrideParameter : "",
  production: true,
  urlLogout: 'https://tst-firmip-intranet.cr.piemonte.it/tst-firmip-intranetsliv1intranet_icon/Shibboleth.sso/Logout'
};
