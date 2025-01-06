import { Component, inject, signal } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { CommodityImportModel } from "@app/model/business/commodity-import.model";
import { CommodityModel } from "@app/model/business/commodity.model";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { CommodityService } from "@app/pages/commodity/commodity-mgmt/commodity.service";
import { AppInlineLogMessage } from "@app/widget/component/inline-log-message/inline-log-message.component";

@Component({
  templateUrl: "./edit.component.html",
  styleUrls: ["./edit.component.scss"],
  imports: [AppCommonModule, AppInlineLogMessage],
})
export class EditDialog extends BasePage {
  dialogData = inject<{ commodityImport: CommodityImportModel }>(
    MAT_DIALOG_DATA
  );
  logMessage$ = signal<Nullable<LogModel>>(null);
  $dialogRef = inject(MatDialogRef<EditDialog>);

  formGroup = new FormGroup({
    comId: new FormControl(0, [Validators.required, Validators.min(1)]),
    quantity: new FormControl(0, [Validators.required, Validators.min(0)]),
  });

  commodityList$ = signal<CommodityModel[]>([]);

  /// methods
  constructor(private $commodityService: CommodityService) {
    super("");
  }

  protected override __ngOnInit__() {
    this.registerSubscription(
      this.$commodityService.findAll().subscribe((commodityList) => {
        this.commodityList$.set(commodityList);
      })
    );
  }

  onConfirm() {
    console.log("on confirm - import items");
  }

  onClose() {
    console.log("on close dialog - import items");

    this.$dialogRef.close();
  }
}
