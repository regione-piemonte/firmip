/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FirmeStampaComponent } from './firme-stampa.component';

describe('FirmeStampaComponent', () => {
  let component: FirmeStampaComponent;
  let fixture: ComponentFixture<FirmeStampaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FirmeStampaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FirmeStampaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
