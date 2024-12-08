import { DialogRef } from "@angular/cdk/dialog";
import { Component, inject } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { AppCommonModule } from "@app/core/app-common.module";
import { WarehouseModel } from "@app/model/business/warehouse.model";
import { BasePage } from "@app/pages/base.page";
import { WarehouseService } from "@app/service/warehouse.service";

@Component({
  templateUrl: "./edit.component.html",
  styleUrls: ["./edit.component.scss"],
  imports: [AppCommonModule],
})
export class EditComponent extends BasePage {
  $dialogRef = inject(DialogRef<WarehouseModel>);

  formGroup = new FormGroup({
    warehouseName: new FormControl("", [Validators.required]),
    warehouseAddress: new FormControl("", [Validators.required]),
  });

  data?: { warehouse: WarehouseModel } = inject(MAT_DIALOG_DATA);

  /// METHODS

  constructor(private $warehouseService: WarehouseService) {
    super("Edit Warehouse");
  }

  protected override __ngOnInit__() {
    if (this.data && this.data.warehouse) {
      this.formGroup.patchValue(this.data?.warehouse);
    }
  }

  save() {
    console.log("do save form...", this.formGroup.value);

    if (!this.data) {
      this.data = { warehouse: {} };
    }

    if (!this.data.warehouse) {
      this.data.warehouse = {};
    }

    this.data.warehouse.warehouseName =
      this.formGroup.value.warehouseName || "";
    this.data.warehouse.warehouseAddress =
      this.formGroup.value.warehouseAddress || "";

    this.registerSubscription(
      this.$warehouseService.sync(this.data.warehouse).subscribe((result) => {
        this.$dialogRef.close(result);
      })
    );
  }
}
