/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../../services/auth.service";
import {User} from "../../../model/firmipbo-data-fe";
import {environment} from "../../../../environments/environment";
import {Router} from "@angular/router";

@Component({
  selector: 'firmipbo-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  loggedUser: User = {cognome: '',nome: '', codFisc: ''};

  constructor(private authService: AuthService,
              private router: Router) { }

  ngOnInit(): void {
    this.authService.authObs.subscribe( (user) => {
      this.loggedUser = user;
    })
  }

  logout() {
    this.authService.logoutUser().subscribe(
      () => {
        this.router.navigate(['/']).then(
          result => {
            window.location.href = environment.urlLogout;
          }
        );
      }
    )
  }
}
