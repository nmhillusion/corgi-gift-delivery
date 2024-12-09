import { DialogRef } from "@angular/cdk/dialog";
import { Component, inject } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { AppCommonModule } from "@app/core/app-common.module";
import { CustomerModel } from "@app/model/business/customer.model";
import { BasePage } from "@app/pages/base.page";
import { CustomerService } from "@app/service/customer.service";

@Component({
  templateUrl: "./edit.component.html",
  styleUrls: ["./edit.component.scss"],
  imports: [AppCommonModule],
})
export class EditComponent extends BasePage {
  
  data?: {customer: CustomerModel } = inject(MAT_DIALOG_DATA);

  $dialogRef = inject(DialogRef<CustomerModel>);

  formGroup = new FormGroup({
    fullName: new FormControl("", [Validators.required]),
    idCardNumber: new FormControl("", [Validators.required]),
  });

  /// Methods

  constructor(private $customerService: CustomerService) {
    super("Edit Customer");
  }

  protected override __ngOnInit__() {
    if (this.data && this.data.customer) {
      this.formGroup.patchValue(this.data?.customer);
    }
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

    this.registerSubscription(
      this.$customerService.sync(this.data.customer).subscribe((result) => {
        this.$dialogRef.close(result);
      })
    );
  }
}
