import { DialogRef } from "@angular/cdk/dialog";
import { Component, inject, Inject, OnDestroy, OnInit, signal, WritableSignal } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MatFormField, MatLabel } from "@angular/material/form-field";
import { AppCommonModule } from "@app/core/app-common.module";
import { CommodityTypeModel } from "@app/model/business/commodity-type.model";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { CommodityTypeService } from "@app/service/commodity-type.service";
import { Subscription } from "rxjs";

@Component({
  standalone: true,
  templateUrl: "./edit.component.html",
  styleUrl: "./edit.component.scss",
  imports: [AppCommonModule, MatFormField, MatLabel],
})
export class EditComponent implements OnInit, OnDestroy {
  title = "edit-mgmt";

  loading$: WritableSignal<boolean> = signal(false);
  logMessage$: WritableSignal<Nullable<LogModel>> = signal(null);
  private subscriptions: Subscription[] = [];

  data: {
    commodityType: CommodityTypeModel
  } = inject(MAT_DIALOG_DATA);

  formGroup: FormGroup = new FormGroup({
    typeName: new FormControl("", [Validators.required]),
  });

  /// METHODS

  constructor(private $dialogRef: DialogRef<EditComponent>, private $commodityTypeService: CommodityTypeService,
  ) {}

  ngOnInit() {
    console.log({ data: this.data.commodityType });

    this.formGroup.patchValue(this.data.commodityType);
  }

  save() {
    console.log("do save form...", this.formGroup.value);

    this.subscriptions.push(
      this.$commodityTypeService
        .create(this.formGroup.value)
        .subscribe((result) => {
          console.log({ result });

          this.$dialogRef.close();
        })
    );
  }

  ngOnDestroy() {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
