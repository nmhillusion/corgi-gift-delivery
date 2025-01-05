import { Component, inject, signal } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { AppCommonModule } from "@app/core/app-common.module";
import { CommodityImportModel } from "@app/model/business/commodity-import.model";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { CommodityImportService } from "@app/service/commodity-import.service";
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
    commodityImport: CommodityImportModel;
  }>(MAT_DIALOG_DATA);

  formGroup = new FormGroup({
    importName: new FormControl("", [Validators.required]),
    importTime: new FormControl<Nullable<Date>>(null, [Validators.required]),
  });

  /// methods
  constructor(private $commodityImportService: CommodityImportService) {
    super("");
  }

  protected override __ngOnInit__() {
    this.formGroup.patchValue(this.dialogData.commodityImport);
  }

  onConfirm() {
    const formData = this.formGroup.value;

    console.log("confirm to sync import entity: ", formData);

    this.dialogData.commodityImport.importName = formData.importName || "";
    this.dialogData.commodityImport.importTime =
      formData.importTime || new Date();

    this.registerSubscription(
      this.$commodityImportService
        .sync(this.dialogData.commodityImport)
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
