import { Component, inject, signal, WritableSignal } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { DeliveryAttemptModel } from "@app/model/business/delivery-attempt.model";
import { DeliveryTypeModel } from "@app/model/business/delivery-type.model";
import { IdType } from "@app/model/core/id.model";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { DeliveryAttemptService } from "@app/service/delivery-attempt.service";
import { DeliveryTypeService } from "@app/service/delivery-type.service";
import { ShipperService } from "@app/service/shipper.service";
import { AppInlineLogMessage } from "@app/widget/component/inline-log-message/inline-log-message.component";
import { AddSelectShipperWidget } from "@app/pages/shared/shipper/app-select-shipper/widget.component";
import { BehaviorSubject } from "rxjs";

@Component({
  standalone: true,
  templateUrl: "./edit.component.html",
  styleUrls: ["./edit.component.scss"],
  imports: [AppCommonModule, AppInlineLogMessage, AddSelectShipperWidget],
})
export class EditComponent extends BasePage {
  formGroup = new FormGroup({
    deliveryTypeId: new FormControl<IdType>("", [Validators.required]),
    shipperId: new FormControl<IdType>("", [Validators.required]),
  });

  logMessage$ = signal<Nullable<LogModel>>(null);

  dialogData = inject<{
    deliveryAttempt: DeliveryAttemptModel;
  }>(MAT_DIALOG_DATA);

  deliveryTypeList$: WritableSignal<DeliveryTypeModel[]> = signal([]);

  formModels = {
    shipperId: {
      $: new BehaviorSubject<Nullable<IdType>>(null),
      validators: [Validators.required],
    },
  };

  /// methods
  constructor(
    private $deliveryAttemptService: DeliveryAttemptService,
    private $deliveryTypeService: DeliveryTypeService,
    private $shipperService: ShipperService
  ) {
    super();
  }

  protected override __ngOnInit__() {
    this.registerSubscription(
      this.$deliveryTypeService.findAll().subscribe((deliveryTypeList) => {
        this.deliveryTypeList$.set(deliveryTypeList);
      })
    );

    this.formGroup.patchValue(this.dialogData.deliveryAttempt);
  }

  save() {
    console.log("do save form...", this.formGroup.value);
  }
}
