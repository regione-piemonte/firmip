/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import {Directive, ElementRef, HostListener, Input, Renderer2} from '@angular/core';
import {Sort} from "../../../util/sort";


@Directive({
  selector: '[appSort]'
})
export class SortDirective {

  @Input() appSort: Array<any>;
  @Input() keyColumn: string;
  constructor(private renderer: Renderer2, private targetElem: ElementRef) { }
  @HostListener("click")
  sortData() {
    const sort = new Sort();
    // reference clicked element
    const elem = this.targetElem.nativeElement;
    // leggo attributo per ordine
    const order = elem.getAttribute("data-order");
    // leggo nome della proprietá
    // const property = elem.getAttribute("data-name");
    const property = this.keyColumn;
    // leggo type della property
    const type = elem.getAttribute("data-type");
    if (order === "desc") {
      this.appSort.sort(sort.startSort(property, order, type));
      elem.setAttribute("data-order", "asc");
    } else {
      this.appSort.sort(sort.startSort(property, order, type));
      elem.setAttribute("data-order", "desc");
    }


  }
}
