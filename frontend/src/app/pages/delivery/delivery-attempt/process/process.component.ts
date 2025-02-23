import { DialogRef } from "@angular/cdk/dialog";
import { Component, inject, signal } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { AppCommonModule } from "@app/core/app-common.module";
import { DeliveryAttemptModel } from "@app/model/business/delivery-attempt.model";
import { DeliveryStatusModel } from "@app/model/business/delivery-status.model";
import { DeliveryFEModel } from "@app/model/business/delivery.model";
import { IdType } from "@app/model/core/id.model";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { DeliveryAttemptService } from "@app/service/delivery-attempt.service";
import { DeliveryService } from "@app/service/delivery.service";
import { AppInlineLogMessage } from "@app/widget/component/inline-log-message/inline-log-message.component";

@Component({
  standalone: true,
  templateUrl: "./process.component.html",
  styleUrls: ["./process.component.scss"],
  imports: [AppCommonModule, AppInlineLogMessage],
})
export class ProcessComponent extends BasePage {
  logMessage$ = signal<Nullable<LogModel>>(null);

  dialogData = inject<{
    deliveryId: string;
    deliveryAttempt: DeliveryAttemptModel;
  }>(MAT_DIALOG_DATA);

  delivery$ = signal<Nullable<DeliveryFEModel>>(null);

  filteredDeliveryStatusList$ = signal<DeliveryStatusModel[]>([]);

  formGroup = new FormGroup({
    deliveryStatusId: new FormControl<IdType>("", [Validators.required]),
    actionDate: new FormControl<Date>(new Date(), [Validators.required]),
  });

  /// methods
  constructor(
    private $deliveryService: DeliveryService,
    private $deliveryAttemptService: DeliveryAttemptService,
    private $dialogRef: DialogRef<DeliveryAttemptModel>
  ) {
    super();
  }

  protected override __ngOnInit__() {
    if (!this.dialogData.deliveryAttempt) {
      throw new Error("deliveryAttempt is required");
    }

    if (!this.dialogData.deliveryId) {
      throw new Error("deliveryId is required");
    }

    this.registerSubscription(
      this.$deliveryService
        .findById(this.dialogData.deliveryId)
        .subscribe((delivery) => {
          this.delivery$.set(
            this.$deliveryService.convertToFEModel(delivery, this)
          );
        }),
      this.$deliveryAttemptService
        .getAvailableStatusForProcess(this.dialogData.deliveryId)
        .subscribe((statusList) => {
          this.filteredDeliveryStatusList$.set(statusList);
        })
    );
  }

  save() {
    console.log("do save form...", this.formGroup.value);

    this.formUtils.revalidateForm(this.formGroup);

    if (!this.formGroup.valid) {
      this.logMessage$.set({
        message: "Form is not valid",
        logType: "error",
      });
      throw new Error("Form is not valid");
    }

    this.registerSubscription(
      this.$deliveryAttemptService
        .process(this.dialogData.deliveryId!, {
          deliveryStatusId: this.formGroup.controls.deliveryStatusId.value!,
          actionDate: this.formGroup.controls.actionDate.value!,
        })
        .subscribe({
          next: (res) => {
            this.$dialogRef.close(res);
          },
          error: (err) => {
            this.logMessage$.set({
              message: "Error when process attempt, please check. " + err,
              logType: "error",
            });
          },
        })
    );
  }
}
