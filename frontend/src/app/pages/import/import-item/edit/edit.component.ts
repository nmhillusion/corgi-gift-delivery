import { Component, inject, signal } from "@angular/core";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";

@Component({
  templateUrl: "./edit.component.html",
  styleUrls: ["./edit.component.scss"],
})
export class EditComponent extends BasePage {
  logMessage$ = signal<Nullable<LogModel>>(null);

  dialogData: {
    importItem: any;
  } = inject(MAT_DIALOG_DATA);

  /// methods
  constructor() {
    super("Edit Import Item");
  }
}
