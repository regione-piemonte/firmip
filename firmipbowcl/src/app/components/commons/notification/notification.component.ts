/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import {AfterViewInit, Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {MsgNotificationService} from "../../../services/msg-notification.service";
import {MsgNotifica} from "../../../model/firmipbo-data-fe";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'firmipbo-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit, OnDestroy, AfterViewInit {
  view_notification: boolean = false;

  private sub: Subscription;


 notificationData: MsgNotifica;

  constructor(private msgNorificationService: MsgNotificationService) {
      this.sub = this.msgNorificationService.msgNotification$.subscribe( (msg) => {
       this.notificationData = msg;
       this.view_notification = true;
        setTimeout(() => {
          this.view_notification = false;
        }, environment.msgTimeout);
      }
    )
  }

  ngOnInit(): void {
    this.notificationData = null;
  }


  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  ngAfterViewInit(): void {

  }

}
