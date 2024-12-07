import { DialogRef } from "@angular/cdk/dialog";
import { Component, inject, signal, WritableSignal } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { CommodityModel } from "@app/model/business/commodity.model";
import { BasePage } from "@app/pages/base.page";
import { CommodityTypeService } from "@app/service/commodity-type.service";
import { CommodityService } from "../commodity.service";
import { CommodityTypeModel } from "@app/model/business/commodity-type.model";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { AppCommonModule } from "@app/core/app-common.module";
import { MatOptionModule } from "@angular/material/core";

@Component({
  selector: "app-edit",
  templateUrl: "./edit.component.html",
  styleUrls: ["./edit.component.scss"],
  imports: [
    AppCommonModule
  ]
})
export class EditComponent extends BasePage {
  loading$ = signal(false);
  logMessage$: WritableSignal<Nullable<LogModel>> = signal(null);

  data: {
    commodityType: CommodityModel;
  } = inject(MAT_DIALOG_DATA);

  formGroup: FormGroup = new FormGroup({
    comName: new FormControl("", [Validators.required]),
    comTypeId: new FormControl("", [Validators.required]),
  });

  commodityTypeList$: WritableSignal<CommodityTypeModel[]> = signal([]);

  /// METHODS

  constructor(
    private $dialogRef: DialogRef<EditComponent>,
    private $commodityTypeService: CommodityTypeService,
    private $commodityService: CommodityService
  ) {
    super("Edit Commodity");
  }

  protected override __ngOnInit__() {
    this.formGroup.patchValue(this.data.commodityType);

    this.registerSubscription(
      this.$commodityTypeService
        .findAll()
        .subscribe((result) => {
          console.log("commodityTypeList: ", result);
          
          this.commodityTypeList$.set(result);
        })
    );
  }

  save() {
    console.log("do save form...", this.formGroup.value);
  }
}
