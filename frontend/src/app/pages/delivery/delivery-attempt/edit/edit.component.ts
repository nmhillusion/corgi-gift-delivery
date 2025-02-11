import { DialogRef } from "@angular/cdk/dialog";
import { Component, inject, signal, WritableSignal } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { AppCommonModule } from "@app/core/app-common.module";
import { DeliveryAttemptModel } from "@app/model/business/delivery-attempt.model";
import { DeliveryTypeModel } from "@app/model/business/delivery-type.model";
import { IdType } from "@app/model/core/id.model";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { AppSelectShipperWidget } from "@app/pages/shared/shipper/app-select-shipper/widget.component";
import { DeliveryAttemptService } from "@app/service/delivery-attempt.service";
import { DeliveryTypeService } from "@app/service/delivery-type.service";
import { ShipperService } from "@app/service/shipper.service";
import { AppInlineLogMessage } from "@app/widget/component/inline-log-message/inline-log-message.component";

@Component({
  standalone: true,
  templateUrl: "./edit.component.html",
  styleUrls: ["./edit.component.scss"],
  imports: [AppCommonModule, AppInlineLogMessage, AppSelectShipperWidget],
})
export class EditComponent extends BasePage {
  formGroup = new FormGroup({
    deliveryTypeId: new FormControl<IdType>("", [Validators.required]),
    shipperId: new FormControl<IdType>("", [Validators.required]),
  });

  logMessage$ = signal<Nullable<LogModel>>(null);

  dialogData = inject<{
    deliveryId: string;
    deliveryAttempt: DeliveryAttemptModel;
  }>(MAT_DIALOG_DATA);

  deliveryTypeList$: WritableSignal<DeliveryTypeModel[]> = signal([]);

  /// methods
  constructor(
    private $deliveryAttemptService: DeliveryAttemptService,
    private $deliveryTypeService: DeliveryTypeService,
    private $shipperService: ShipperService,
    private $dialogRef: DialogRef<DeliveryAttemptModel>
  ) {
    super();
  }

  protected override __ngOnInit__() {
    this.registerSubscription(
      this.$deliveryTypeService.findAll().subscribe((deliveryTypeList) => {
        this.deliveryTypeList$.set(
          deliveryTypeList.map((it) => {
            it.typeId = String(it.typeId);
            return it;
          })
        );
      })
    );

    this.formGroup.patchValue(this.dialogData.deliveryAttempt);
  }

  save() {
    console.log("do save form...", this.formGroup.value);

    if (!this.dialogData.deliveryAttempt) {
      this.dialogData.deliveryAttempt = {};
    }

    this.dialogData.deliveryAttempt.deliveryId = this.dialogData.deliveryId;
    this.dialogData.deliveryAttempt.deliveryTypeId =
      this.formGroup.controls["deliveryTypeId"].value || 0;
    this.dialogData.deliveryAttempt.shipperId =
      this.formGroup.controls["shipperId"].value || 0;

    this.registerSubscription(
      this.$deliveryAttemptService
        .save(this.dialogData.deliveryAttempt)
        .subscribe((result) => {
          this.$dialogRef.close(result);
        })
    );
  }
}
