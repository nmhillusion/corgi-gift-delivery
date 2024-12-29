import { DialogRef } from "@angular/cdk/dialog";
import { Component, signal } from "@angular/core";
import { AppCommonModule } from "@app/core/app-common.module";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { RecipientService } from "@app/service/recipient.service";
import { AppInputFileComponent } from "@app/widget/component/input-file/input-file.component";
import { BehaviorSubject } from "rxjs";
import { AppInlineLogMessage } from "../../../../widget/component/inline-log-message/inline-log-message.component";

@Component({
  standalone: true,
  templateUrl: "./import.component.html",
  styleUrls: ["./import.component.scss"],
  imports: [AppCommonModule, AppInputFileComponent, AppInlineLogMessage],
})
export class ImportComponent extends BasePage {
  logMessage$ = signal<Nullable<LogModel>>(null);
  recipientFile$ = new BehaviorSubject<File[]>([]);

  /// Methods
  constructor(
    private $recipientService: RecipientService,
    private $dialogRef: DialogRef<ImportComponent>
  ) {
    super("");
  }

  onConfirm() {
    const currentItem = this.recipientFile$.getValue()[0];

    if (!currentItem) {
      this.logMessage$.set({
        logType: "error",
        message: "Could not determine file to import",
      });
      return;
    }

    this.registerSubscription(
      this.$recipientService.importExcel(currentItem).subscribe({
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

  onClose() {
    this.$dialogRef.close();
  }
}
