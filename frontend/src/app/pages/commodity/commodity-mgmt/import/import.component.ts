import { DialogRef } from "@angular/cdk/dialog";
import { BasePage } from "@app/pages/base.page";
import { CommodityService } from "../commodity.service";
import { Component, signal } from "@angular/core";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BehaviorSubject } from "rxjs";
import { AppCommonModule } from "@app/core/app-common.module";
import { AppInputFileComponent } from "../../../../widget/component/input-file/input-file.component";
import { AppInlineLogMessage } from "../../../../widget/component/inline-log-message/inline-log-message.component";

@Component({
  templateUrl: "./import.component.html",
  styleUrls: ["./import.component.scss"],
  imports: [AppCommonModule, AppInputFileComponent, AppInlineLogMessage],
})
export class ImportComponent extends BasePage {
  logMessage$ = signal<Nullable<LogModel>>(null);
  inputFile$ = new BehaviorSubject<File[]>([]);

  /// Methods
  constructor(
    private $commodityService: CommodityService,
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
      this.$commodityService.importExcel(currentItem).subscribe({
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
