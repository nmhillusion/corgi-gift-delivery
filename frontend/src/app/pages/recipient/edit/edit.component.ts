import { DialogRef } from "@angular/cdk/dialog";
import { Component, inject, signal } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { AppCommonModule } from "@app/core/app-common.module";
import { RecipientTypeModel } from "@app/model/business/recipient-type.model";
import { RecipientModel } from "@app/model/business/recipient.model";
import { IdType } from "@app/model/core/id.model";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { RecipientTypeService } from "@app/service/recipient-type.service";
import { RecipientService } from "@app/service/recipient.service";
import { catchError } from "rxjs";

@Component({
  templateUrl: "./edit.component.html",
  styleUrls: ["./edit.component.scss"],
  imports: [AppCommonModule],
})
export class EditComponent extends BasePage {
  data?: { recipient: RecipientModel } = inject(MAT_DIALOG_DATA);

  logMessage$ = signal<Nullable<LogModel>>(null);

  $dialogRef = inject(DialogRef<RecipientModel>);

  formGroup = new FormGroup({
    fullName: new FormControl("", [Validators.required]),
    idCardNumber: new FormControl("", [Validators.required]),
    recipientTypeId: new FormControl<IdType>(-1, [Validators.required]),
  });

  recipientTypeList$ = signal<RecipientTypeModel[]>([]);

  /// Methods

  constructor(
    private $recipientService: RecipientService,
    private $recipientTypeService: RecipientTypeService
  ) {
    super("Edit Recipient");
  }

  protected override __ngOnInit__() {
    console.log("pass data: ", this.data);

    this.registerSubscription(
      this.$recipientTypeService.findAll().subscribe({
        next: (result) => {
          this.recipientTypeList$.set(result);
        },
        error: (error) => {
          this.logMessage$.set({
            logType: "error",
            message: "Error: " + error,
          });
        },
        complete: () => {
          if (this.data && this.data.recipient) {
            this.formGroup.patchValue(this.data?.recipient);
          }
        },
      })
    );
  }

  save() {
    console.log("do save form...", this.formGroup.value);

    if (!this.data) {
      this.data = { recipient: {} };
    }

    if (!this.data.recipient) {
      this.data.recipient = {};
    }

    this.data.recipient.fullName = this.formGroup.value.fullName || "";
    this.data.recipient.idCardNumber = this.formGroup.value.idCardNumber || "";
    this.data.recipient.recipientTypeId =
      this.formGroup.value.recipientTypeId || 0;

    this.registerSubscription(
      this.$recipientService
        .sync(this.data.recipient)
        .pipe(
          catchError((error) => {
            this.logMessage$.set({
              logType: "error",
              message: "Error: " + error,
            });

            throw error;
          })
        )
        .subscribe((result) => {
          this.$dialogRef.close(result);
        })
    );
  }
}
