import { Component, inject, signal } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { CommodityImportModel } from "@app/model/business/commodity-import.model";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { AppInlineLogMessage } from "@app/widget/component/inline-log-message/inline-log-message.component";

@Component({
  templateUrl: "./edit.component.html",
  styleUrls: ["./edit.component.scss"],
  imports: [AppCommonModule, AppInlineLogMessage],
})
export class EditDialog extends BasePage {
  dialogData = inject<{ commodityImport: CommodityImportModel }>(
    MAT_DIALOG_DATA
  );
  logMessage$ = signal<Nullable<LogModel>>(null);

  $dialogRef = inject(MatDialogRef<EditDialog>);

  /// methods
  constructor() {
    super("");
  }

  onConfirm() {
    console.log("on confirm - import items");
  }

  onClose() {
    console.log("on close dialog - import items");
    
    this.$dialogRef.close();
  }
}
