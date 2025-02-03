import { Component, inject, signal } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { MatSelectChange } from "@angular/material/select";
import { AppCommonModule } from "@app/core/app-common.module";
import { CommodityExportModel } from "@app/model/business/commodity-export.model";
import { CommodityModel } from "@app/model/business/commodity.model";
import { WarehouseExportItemModel } from "@app/model/business/warehouse-export-item.model";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { CommodityService } from "@app/pages/commodity/commodity-mgmt/commodity.service";
import { WarehouseExportItemService } from "@app/service/warehouse-export-item.service";
import { WarehouseService } from "@app/service/warehouse.service";
import { AppInlineLogMessage } from "@app/widget/component/inline-log-message/inline-log-message.component";

@Component({
  templateUrl: "./edit.component.html",
  styleUrls: ["./edit.component.scss"],
  imports: [AppCommonModule, AppInlineLogMessage],
})
export class EditDialog extends BasePage {
  dialogData = inject<{
    commodityExport: CommodityExportModel;
    warehouseExportItem: WarehouseExportItemModel;
  }>(MAT_DIALOG_DATA);

  logMessage$ = signal<Nullable<LogModel>>(null);
  $dialogRef = inject(MatDialogRef<EditDialog>);

  formGroup = new FormGroup({
    comId: new FormControl(0, [Validators.required, Validators.min(1)]),
    quantity: new FormControl(0, [Validators.required, Validators.min(0)]),
  });

  commodityList$ = signal<CommodityModel[]>([]);

  /// methods
  constructor(
    private $commodityService: CommodityService,
    private $warehouseExportItemService: WarehouseExportItemService,
    private $warehouseService: WarehouseService
  ) {
    super("Export Items");
  }

  protected override __ngOnInit__() {
    this.registerSubscription(
      this.$commodityService.findAll().subscribe((commodityList) => {
        this.commodityList$.set(commodityList);
      })
    );
  }

  onConfirm() {
    console.log("on confirm - export items");

    const exportItem = this.dialogData.warehouseExportItem;

    const formData = this.formGroup.value;
    exportItem.comId = formData.comId || "";
    exportItem.quantity = formData.quantity || 0;

    this.registerSubscription(
      this.$warehouseExportItemService.save(exportItem).subscribe({
        next: (res) => {
          this.logMessage$.set({
            logType: "info",
            message: "Updated successfully",
          });
          this.onClose();
        },
        error: (err) => {
          this.logMessage$.set({
            logType: "error",
            message: "Có lỗi xảy ra: " + err,
          });
        },
      })
    );
  }

  onClose() {
    console.log("on close dialog - import items");

    this.$dialogRef.close();
  }

  onChangeComId($event: MatSelectChange) {
    console.log("on change commodity: ", $event);
    
    const selectedComId = $event.value;

    this.registerSubscription(
      this.$warehouseService
        .remainingQuantityOfCommodityOfWarehouse(
          this.dialogData.commodityExport.warehouseId,
          selectedComId
        )
        .subscribe({
          next: (res) => {
            console.log(
              `remaining quantity of commodity [${selectedComId}] is: ${res}`
            );

            this.formGroup.controls.quantity.setValidators([
              Validators.required,
              Validators.min(0),
              Validators.max(Number(res)),
            ]);
          },
        })
    );
  }
}
