/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */
import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {IDynamicTable, ITableCmd, ITableHeader} from "./table-models";

@Component({
  selector: 'app-w-table',
  templateUrl: './w-table.component.html',
  styleUrls: ['./w-table.component.css']
})
export class WTableComponent implements OnInit {
  public isCollapsed = true;
  @Input() page: number;
  @Input() pageSize: number
  @Output('cmdEvent') cmdEvent = new EventEmitter();

  public constructor(private changeDetector: ChangeDetectorRef) { }
  tableData: IDynamicTable;

  dragTrace: {src: number, dest: number};

  ngOnInit(): void {
    this.tableData = { headers: [], data: [],cmd:[] };
  }

  render(headers: ITableHeader[], data: any[], cmd: ITableCmd[]) {
    this.tableData = {
      headers: headers.filter(x => x.isSelected),
      data: data,
      cmd: cmd
    }

    this.resetDragTracer();
    this.changeDetector.detectChanges();
  }

  private resetDragTracer() {
    this.dragTrace = {src: -1, dest: -1 };
  }


  emitCmd(row: any, cmd: string) {
    this.cmdEvent.emit({cmd: cmd, data: row});
    console.log('Esecuzione evento');
  }

  toggleHeader(index: number) {
    this.tableData.headers[index].isChecked = !  this.tableData.headers[index].isChecked;
  }

  onDragStart(i: number) {
    console.log('Start: ' + i);
    this.dragTrace.src = i;
  }

  onDragOver(i: number) {
    console.log('End: ' + i);
    this.dragTrace.dest = i;
  }

  onDragEnd() {
    const abort =
      this.dragTrace.src === -1 ||
      this.dragTrace.dest === -1  ||
      this.dragTrace.src === this.dragTrace.dest ||
      (this.dragTrace.src < 0 && this.dragTrace.src >= this.tableData.headers.length) ||
      (this.dragTrace.dest < 0 && this.dragTrace.dest >= this.tableData.headers.length)
    ;

    if (abort) {
      this.resetDragTracer();
      return;
    }

    //header.splice(end,0,header.splice(start,1)[0]);
    this.tableData.headers.splice(this.dragTrace.dest,0,this.tableData.headers.splice(this.dragTrace.src,1)[0]);
    this.resetDragTracer();


  }
}
