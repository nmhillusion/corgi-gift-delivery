import { DialogRef } from "@angular/cdk/dialog";
import { Component, signal } from "@angular/core";
import { AppCommonModule } from "@app/core/app-common.module";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { WarehouseService } from "@app/service/warehouse.service";
import { AppInlineLogMessage } from "@app/widget/component/inline-log-message/inline-log-message.component";
import { AppInputFileComponent } from "@app/widget/component/input-file/input-file.component";
import { BehaviorSubject } from "rxjs";

@Component({
  templateUrl: "./import.component.html",
  styleUrls: ["./import.component.scss"],
  imports: [AppCommonModule, AppInputFileComponent, AppInlineLogMessage],
})
export class ImportComponent extends BasePage {
  logMessage$ = signal<Nullable<LogModel>>(null);

  inputFile$ = new BehaviorSubject<File[]>([]);

  /// Methods
  constructor(private $warehouseService: WarehouseService,
    private $dialogRef: DialogRef<ImportComponent>
  ) {
    super("");
  }

  onClose() {
    this.$dialogRef.close();
  }

  onConfirm() {
    const currentItem = this.inputFile$.getValue()[0];

    if (!currentItem) {
      this.logMessage$.set({
        logType: "error",
        message: "Could not determine file to import",
      });
      return;
    }

    this.registerSubscription(
      this.$warehouseService.importExcel(currentItem).subscribe({
        next: (res_) => {
          this.logMessage$.set({
            logType: "info",
            message: `Imported ${res_.length} items successfully`,
          });
        },
        error: (err) => {
          this.logMessage$.set({
            logType: "error",
            message: `Error: ${err}`,
          });
        },
      })
    );
  }
}
