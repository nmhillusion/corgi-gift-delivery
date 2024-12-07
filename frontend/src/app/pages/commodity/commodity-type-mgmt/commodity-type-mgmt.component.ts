import { Component, OnDestroy, OnInit, signal, WritableSignal } from "@angular/core";
import { CommodityTypeService } from "@app/service/commodity-type.service";
import { Subscription } from "rxjs";
import { MainLayoutComponent } from "../../../layout/main-layout/main-layout.component";
import { BasePage } from "@app/pages/base.page";
import { CommodityTypeModel } from "@app/model/business/commodity-type.model";
import { AppCommonModule } from "@app/core/app-common.module";
import { EditComponent } from "./edit/edit.component";

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
    this.registerSubscription(
      this.$commodityTypeService.findAll().subscribe((list) => {
        console.log({ list });

        this.commodityTypeList$.set(list);
      })
    );
  }

  addCommodityType() {
    console.log(" do addCommodityType ");

    this.$dialog.open<EditComponent>(EditComponent, {
      width: "600px",
      maxHeight: "600px",
      data: {},
    })
  }

  editCommodityType(commodityType: CommodityTypeModel) {
    console.log(" do editCommodityType ", commodityType);

    this.$dialog.open<EditComponent>(EditComponent, {
      width: "600px",
      maxHeight: "600px",
      data: {
        commodityType,
      },
    })
  }
}
