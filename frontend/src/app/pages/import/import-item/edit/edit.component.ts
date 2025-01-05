import { Component, inject, signal } from "@angular/core";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { CommodityImportModel } from "@app/model/business/commodity-import.model";
import { WarehouseItemModel } from "@app/model/business/warehouse-item.model";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { CommodityImportService } from "@app/service/commodity-import.service";

@Component({
  templateUrl: "./edit.component.html",
  styleUrls: ["./edit.component.scss"],
})
export class EditComponent extends BasePage {
  logMessage$ = signal<Nullable<LogModel>>(null);

  importModel$ = signal<Nullable<CommodityImportModel>>(null);

  dialogData: {
    importItem?: WarehouseItemModel;
  } = inject(MAT_DIALOG_DATA);

  /// methods
  constructor(private $comImportService: CommodityImportService) {
    super("Edit Import Item");
  }

  protected override __ngOnInit__() {
    console.log("pass data: ", this.dialogData);

    if (this.dialogData && this.dialogData.importItem) {
      const importId = this.dialogData.importItem.importId || 0;

      this.registerSubscription(
        this.$comImportService
          .findById(String(importId))
          .subscribe((result) => {
            console.log("import model = ", result);

            this.importModel$.set(result);
          })
      );
    }
  }
}
