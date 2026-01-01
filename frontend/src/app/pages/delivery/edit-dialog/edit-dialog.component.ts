import { Component, Inject, OnInit } from "@angular/core";
import { FormGroup, FormControl } from "@angular/forms";
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogRef,
} from "@angular/material/dialog";
import { AppCommonModule } from "@app/core/app-common.module";
import { DeliveryFE } from "@app/model/business/delivery.model";
import { IdType } from "@app/model/core/id.model";
import { Nullable } from "@app/model/core/nullable.model";

@Component({
  selector: "edit-dialog",
  templateUrl: "./edit-dialog.component.html",
  styleUrls: ["./edit-dialog.component.scss"],
  standalone: true,
  imports: [AppCommonModule],
})
export class EditDialogComponent implements OnInit {
  // fields
  formGroup = new FormGroup({
    deliveryId: new FormControl<Nullable<IdType>>(null),
    eventId: new FormControl<Nullable<IdType>>(null),
    deliveryPeriodYear: new FormControl<Nullable<number>>(null),
    deliveryPeriodMonth: new FormControl<Nullable<number>>(null),
    territory: new FormControl<Nullable<IdType>>(null),
    region: new FormControl<Nullable<IdType>>(null),
    organId: new FormControl<Nullable<IdType>>(null),
    receivedOrgan: new FormControl<Nullable<IdType>>(null),
    amdName: new FormControl<Nullable<string>>(null),
    customerLevel: new FormControl<Nullable<IdType>>(null),
    customerId: new FormControl<Nullable<IdType>>(null),
    customerName: new FormControl<Nullable<string>>(null),
    idCardNumber: new FormControl<Nullable<string>>(null),
    phoneNumber: new FormControl<Nullable<string>>(null),
    address: new FormControl<Nullable<string>>(null),
    giftName: new FormControl<Nullable<string>>(null),
    note: new FormControl<Nullable<string>>(null),
  });

  /// methods
  constructor(
    private dialogRef: MatDialogRef<EditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private dialogData: DeliveryFE
  ) {}

  ngOnInit(): void {
    this.formGroup.patchValue(this.dialogData);

    this.formGroup.controls.deliveryId.disable();
  }

  onSubmit() {
    this.dialogRef.close(this.formGroup.value);
  }
}
