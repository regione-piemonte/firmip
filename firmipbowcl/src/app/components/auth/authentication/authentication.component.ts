/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'firmipbo-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css']
})
export class AuthenticationComponent implements OnInit, OnDestroy, AfterViewInit {

  constructor( private router: Router,
               private authService: AuthService) { }

  ngOnInit(): void {

  }

  ngAfterViewInit(): void {
    this.authService.loginUser().subscribe(
      (user) => {
        this.authService.authSub$.next(user);
        console.log('navigate home');
         this.router.navigate(['home']);
      },
      error => {
        if (error.status === 0) { // client side o netowrk error
          console.error ('Errore invocazione servizio http: ', error.error);
          this.router.navigate(['error'])
        } else {
          switch (error.status) {
            case 403:
              this.router.navigate(['/403']);
              break;
            default:
              this.router.navigate(['error']);
          }
        }
      }
    );
    this.router.navigate(['home']);
  }

  ngOnDestroy(): void {
  }




}
