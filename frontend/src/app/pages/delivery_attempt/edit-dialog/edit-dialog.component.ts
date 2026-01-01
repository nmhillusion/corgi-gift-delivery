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
import { DeliveryStatus } from "@app/model/business/delivery-status.model";
import { DeliveryType } from "@app/model/business/delivery-type.model";
import { DeliveryFE } from "@app/model/business/delivery.model";
import { IdType } from "@app/model/core/id.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
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
    attemptId: new FormControl<Nullable<IdType>>(null),
    deliveryId: new FormControl<Nullable<IdType>>(null),
    deliveryTypeId: new FormControl<Nullable<IdType>>(null),
    deliveryStatusId: new FormControl<Nullable<IdType>>(null),
    deliveryDate: new FormControl<Nullable<Date>>(null),
    note: new FormControl<Nullable<string>>(null),
  });

  deliveryTypes$ = signal<DeliveryType[]>([]);
  deliveryStatuses$ = signal<DeliveryStatus[]>([]);

  /// methods
  constructor(
    private $deliveryTypeService: DeliveryTypeService,
    private $deliveryStatusService: DeliveryStatusService,
    private dialogRef: MatDialogRef<EditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private dialogData: DeliveryAttemptFE
  ) {
    super();
    console.log({ dialogData });
  }

  override __ngOnInit__() {
    this.registerSubscription(
      this.$deliveryTypeService.getAll().subscribe({
        next: (list) => {
          console.log("Fetched delivery type list:", list);
          this.deliveryTypes$.set(
            list.map((it) => {
              return {
                typeId: Number(it.typeId),
                typeName: it.typeName,
                typeDesc: it.typeDesc,
              };
            })
          );
          this.formGroup.controls.deliveryTypeId.setValue(
            this.dialogData.deliveryTypeId
          );
        },
        error: (error) => {
          console.error("Error fetching delivery type list:", error);
        },
      }),
      this.$deliveryStatusService.getAll().subscribe({
        next: (list) => {
          console.log("Fetched delivery status list:", list);
          this.deliveryStatuses$.set(
            list.map((it) => {
              return {
                statusId: Number(it.statusId),
                statusName: it.statusName,
                statusDesc: it.statusDesc,
              };
            })
          );
          this.formGroup.controls.deliveryStatusId.setValue(
            this.dialogData.deliveryStatusId
          );
        },
        error: (error) => {
          console.error("Error fetching delivery status list:", error);
        },
      })
    );

    this.formGroup.patchValue(this.dialogData);
  }

  onSubmit() {
    this.dialogRef.close(this.formGroup.value);
  }
}
