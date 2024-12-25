import {
  Component,
  OnDestroy,
  OnInit,
  signal,
  WritableSignal,
} from "@angular/core";
import { CommodityTypeService } from "@app/service/commodity-type.service";
import { Subscription } from "rxjs";
import { MainLayoutComponent } from "../../../layout/main-layout/main-layout.component";
import { BasePage } from "@app/pages/base.page";
import { CommodityTypeModel } from "@app/model/business/commodity-type.model";
import { AppCommonModule } from "@app/core/app-common.module";
import { EditComponent } from "./edit/edit.component";
import { BlobUtil } from "@app/util/blob.util";

@Component({
  standalone: true,
  templateUrl: "./commodity-type-mgmt.component.html",
  styleUrl: "./commodity-type-mgmt.component.scss",
  imports: [MainLayoutComponent, AppCommonModule],
})
export class CommodityTypeMgmtComponent extends BasePage {
  /// FIELDS
  commodityTypeList$: WritableSignal<CommodityTypeModel[]> = signal([]);

  /// METHODS
  constructor(private $commodityTypeService: CommodityTypeService) {
    super("Commodity Type Mgmt");
  }

  override __ngOnInit__() {
    this.initLoadData();
  }

  private initLoadData() {
    this.registerSubscription(
      this.$commodityTypeService.findAll().subscribe((list) => {
        console.log({ list });

        this.commodityTypeList$.set(list);
      })
    );
  }

  addCommodityType() {
    console.log(" do addCommodityType ");

    this.openEditDialog();
  }

  editCommodityType(commodityType: CommodityTypeModel) {
    console.log(" do editCommodityType ", commodityType);

    this.openEditDialog(commodityType);
  }

  private openEditDialog(commodityType?: CommodityTypeModel) {
    const ref = this.$dialog.open<EditComponent>(EditComponent, {
      width: "600px",
      maxHeight: "600px",
      data: {
        commodityType,
      },
    });

    this.registerSubscription(
      ref.afterClosed().subscribe((result) => {
        this.initLoadData();
      })
    );
  }

  importCommodityType() {
    throw new Error("Method not implemented.");
  }

  exportCommodityType() {
    this.registerSubscription(
      this.$commodityTypeService.exportExcel().subscribe((data) => {
        BlobUtil.downloadBlob(data, "commodityType.xlsx");
      })
    );
  }
}
