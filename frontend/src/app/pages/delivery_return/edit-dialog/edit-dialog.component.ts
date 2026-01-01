import { Component, Inject, OnInit, signal } from "@angular/core";
import { FormGroup, FormControl } from "@angular/forms";
import { MAT_DATE_FORMATS } from "@angular/material/core";
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogRef,
} from "@angular/material/dialog";
import { AppCommonModule } from "@app/core/app-common.module";
import { DeliveryAttemptFE } from "@app/model/business/delivery-attempt.model";
import { DeliveryReturnStatus } from "@app/model/business/delivery-return-status.model";
import { DeliveryReturnFE } from "@app/model/business/delivery-return.model";
import { DeliveryStatus } from "@app/model/business/delivery-status.model";
import { DeliveryType } from "@app/model/business/delivery-type.model";
import { DeliveryFE } from "@app/model/business/delivery.model";
import { IdType } from "@app/model/core/id.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { DeliveryReturnStatusService } from "@app/service/delivery-return-status.service";
import { DeliveryStatusService } from "@app/service/delivery-status.service";
import { DeliveryTypeService } from "@app/service/delivery-type.service";

@Component({
  selector: "edit-dialog",
  templateUrl: "./edit-dialog.component.html",
  styleUrls: ["./edit-dialog.component.scss"],
  standalone: true,
  imports: [AppCommonModule],
  providers: [
    {
      provide: MAT_DATE_FORMATS,
      useValue: {
        parse: {
          dateInput: "DD/MM/YYYY",
        },
        display: {
          dateInput: "DD/MM/YYYY",
          monthYearLabel: "MM YYYY",
          dateA11yLabel: "LL",
          monthYearA11yLabel: "MMMM YYYY",
        },
      },
    },
  ],
})
export class EditDialogComponent extends BasePage implements OnInit {
  // fields
  formGroup = new FormGroup({
    returnId: new FormControl<Nullable<IdType>>(null),
    deliveryId: new FormControl<Nullable<IdType>>(null),
    returnStatusId: new FormControl<Nullable<IdType>>(null),
    note: new FormControl<Nullable<string>>(null),
  });

  deliveryReturnStatuses$ = signal<DeliveryReturnStatus[]>([]);

  /// methods
  constructor(
    private $deliveryReturnStatusService: DeliveryReturnStatusService,
    private dialogRef: MatDialogRef<EditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private dialogData: DeliveryReturnFE
  ) {
    super();
    console.log({ dialogData });
  }

  override __ngOnInit__() {
    this.registerSubscription(
      this.$deliveryReturnStatusService.getAll().subscribe({
        next: (list) => {
          console.log("Fetched delivery return status list:", list);
          this.deliveryReturnStatuses$.set(
            list.map((it) => {
              return {
                statusId: Number(it.statusId),
                statusName: it.statusName,
                statusDesc: it.statusDesc,
              };
            })
          );
          this.formGroup.controls.returnStatusId.setValue(
            this.dialogData.returnStatusId
          );
        },
        error: (error) => {
          console.error("Error fetching delivery return status list:", error);
        },
      })
    );

    this.formGroup.patchValue(this.dialogData);

    this.formGroup.controls.returnId.disable();
    this.formGroup.controls.deliveryId.disable();
  }

  onSubmit() {
    this.dialogRef.close(this.formGroup.value);
  }
}
