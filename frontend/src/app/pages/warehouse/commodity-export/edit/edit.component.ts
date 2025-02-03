import { Component, inject, signal } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { AppCommonModule } from "@app/core/app-common.module";
import { CommodityExportModel } from "@app/model/business/commodity-export.model";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { CommodityExportService } from "@app/service/commodity-export.service";
import { AppInlineLogMessage } from "@app/widget/component/inline-log-message/inline-log-message.component";

@Component({
  templateUrl: "./edit.component.html",
  styleUrls: ["./edit.component.scss"],
  imports: [AppCommonModule, AppInlineLogMessage],
})
export class EditDialog extends BasePage {
  logMessage$ = signal<Nullable<LogModel>>(null);
  $dialogRef = inject(MatDialogRef<EditDialog>);

  dialogData = inject<{
    commodityExport: CommodityExportModel;
  }>(MAT_DIALOG_DATA);

  formGroup = new FormGroup({
    exportName: new FormControl("", [Validators.required]),
    exportTime: new FormControl<Nullable<Date>>(null, [Validators.required]),
  });

  /// methods
  constructor(private $commodityExportService: CommodityExportService) {
    super("");
  }

  protected override __ngOnInit__() {
    this.formGroup.patchValue(this.dialogData.commodityExport);
  }

  onConfirm() {
    const formData = this.formGroup.value;

    console.log("confirm to sync export entity: ", formData);

    this.dialogData.commodityExport.exportName = formData.exportName || "";
    this.dialogData.commodityExport.exportTime =
      formData.exportTime || new Date();

    this.registerSubscription(
      this.$commodityExportService
        .save(this.dialogData.commodityExport)
        .subscribe({
          next: (res) => {
            this.onClose();
          },
          error: (err) => {
            this.logMessage$.set({
              logType: "error",
              message: JSON.stringify(err),
            });
          },
        })
    );
  }

  onClose() {
    console.log("on close edit dialog");

    this.$dialogRef.close();
  }
}
