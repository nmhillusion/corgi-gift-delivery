import { DialogRef } from "@angular/cdk/dialog";
import { Component, inject, signal, WritableSignal } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { AppCommonModule } from "@app/core/app-common.module";
import { ShipperTypeModel } from "@app/model/business/shipper-type.model";
import { ShipperModel } from "@app/model/business/shipper.model";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { ShipperTypeService } from "@app/service/shipper-type.service";
import { ShipperService } from "@app/service/shipper.service";

@Component({
  templateUrl: "./edit.component.html",
  styleUrls: ["./edit.component.scss"],
  imports: [AppCommonModule],
})
export class EditComponent extends BasePage {
  shipperTypeList$ = signal<ShipperTypeModel[]>([]);
  data?: { shipper: ShipperModel } = inject(MAT_DIALOG_DATA);

  formGroup = new FormGroup({
    shipperName: new FormControl("", [Validators.required]),
    shipperCode: new FormControl("", [Validators.required]),
    shipperTypeId: new FormControl("", [Validators.required]),
  });

  logMessage$: WritableSignal<Nullable<LogModel>> = signal(null);
  $dialogRef: DialogRef<ShipperModel> = inject(DialogRef);

  /// METHODS
  constructor(
    private $shipperTypeService: ShipperTypeService,
    private $shipperService: ShipperService
  ) {
    super("Edit Shipper");
  }

  override __ngOnInit__() {
    this.registerSubscription(
      this.$shipperTypeService.findAll().subscribe((result) => {
        console.log("shipperTypeList: ", result);

        this.shipperTypeList$.set(result);

        if (this.data && this.data.shipper) {
          this.formGroup.patchValue(this.data?.shipper);
        }
      })
    );
  }

  save() {
    console.log("do save form...", this.formGroup.value);

    if (!this.data) {
      this.data = {
        shipper: {},
      };
    }

    if (!this.data.shipper) {
      this.data.shipper = {};
    }

    this.data.shipper.shipperName = this.formGroup.value.shipperName || "";
    this.data.shipper.shipperCode = this.formGroup.value.shipperCode || "";
    this.data.shipper.shipperTypeId = this.formGroup.value.shipperTypeId || "";

    this.registerSubscription(
      this.$shipperService.sync(this.data.shipper).subscribe((result) => {
        this.$dialogRef.close(result);
      })
    );
  }
}
