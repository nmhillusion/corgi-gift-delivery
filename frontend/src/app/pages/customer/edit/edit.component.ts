import { DialogRef } from "@angular/cdk/dialog";
import { Component, inject, signal } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { AppCommonModule } from "@app/core/app-common.module";
import { CustomerTypeModel } from "@app/model/business/customer-type.model";
import { CustomerModel } from "@app/model/business/customer.model";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { CustomerTypeService } from "@app/service/customer-type.service";
import { CustomerService } from "@app/service/customer.service";
import { catchError } from "rxjs";

@Component({
  templateUrl: "./edit.component.html",
  styleUrls: ["./edit.component.scss"],
  imports: [AppCommonModule],
})
export class EditComponent extends BasePage {
  data?: { customer: CustomerModel } = inject(MAT_DIALOG_DATA);

  logMessage$ = signal<Nullable<LogModel>>(null);

  $dialogRef = inject(DialogRef<CustomerModel>);

  formGroup = new FormGroup({
    fullName: new FormControl("", [Validators.required]),
    idCardNumber: new FormControl("", [Validators.required]),
    customerTypeId: new FormControl(-1, [Validators.required]),
  });

  customerTypeList$ = signal<CustomerTypeModel[]>([]);

  /// Methods

  constructor(
    private $customerService: CustomerService,
    private $customerTypeService: CustomerTypeService
  ) {
    super("Edit Customer");
  }

  protected override __ngOnInit__() {
    console.log("pass data: ", this.data);

    this.registerSubscription(
      this.$customerTypeService.findAll().subscribe({
        next: (result) => {
          this.customerTypeList$.set(result);
        },
        error: (error) => {
          this.logMessage$.set({
            logType: "error",
            message: "Error: " + error,
          });
        },
        complete: () => {
          if (this.data && this.data.customer) {
            this.formGroup.patchValue(this.data?.customer);
          }
        },
      })
    );
  }

  save() {
    console.log("do save form...", this.formGroup.value);

    if (!this.data) {
      this.data = { customer: {} };
    }

    if (!this.data.customer) {
      this.data.customer = {};
    }

    this.data.customer.fullName = this.formGroup.value.fullName || "";
    this.data.customer.idCardNumber = this.formGroup.value.idCardNumber || "";
    this.data.customer.customerTypeId =
      this.formGroup.value.customerTypeId || 0;

    this.registerSubscription(
      this.$customerService
        .sync(this.data.customer)
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
