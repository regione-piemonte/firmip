/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { Injectable } from '@angular/core';
import {Router} from "@angular/router";
import {IBreadcrumb} from "../model/firmipbo-data-model";


@Injectable()
export class UtilityService {

  constructor() { }

  public changeBreadcrumb(router: Router, path: string, oldLabel: string, newLabel: string, newUrl?: string) : void {
    router.config.find( (r) => r.path === path)?.data!['breadcrumb'].filter( (b: IBreadcrumb) => b['label'] === oldLabel).map ((el: IBreadcrumb) => {el.label = newLabel;
                                                                                                                                                            if (typeof newUrl !== undefined ){
                                                                                                                                                              el.url = newUrl;
                                                                                                                                                            }
                                                                                                                                                            });
  }


  public setInfoRoute(router: Router, path: string,info: any) {
    const d = router.config.find( (r) => r.path === path)?.data;
    d!['info'] = info;
  }

  

}
