import { Component } from "@angular/core";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { BasePage } from "@app/pages/base.page";
import { AppCommonModule } from "@app/core/app-common.module";
import { CommodityService } from "./commodity.service";
import { CommodityModel } from "@app/model/business/commodity.model";
import { EditComponent } from "./edit/edit.component";

@Component({
  standalone: true,
  templateUrl: "./commodity-mgmt.component.html",
  styleUrl: "./commodity-mgmt.component.scss",
  imports: [MainLayoutComponent, AppCommonModule],
})
export class CommodityMgmtComponent extends BasePage {
  constructor(private $commodityService: CommodityService) {
    super("Commodity Mgmt");
  }

  override __ngOnInit__() {
    this.registerSubscription(
      this.$commodityService.findAll().subscribe((list) => {
        console.log({ list });
      })
    );
  }

  addCommodity() {
    console.log(" do addCommodity ");

    this.$dialog.open<EditComponent>(EditComponent, {
      width: "600px",
      maxHeight: "600px",
      data: {},
    })
  }

  editCommodity(commodity: CommodityModel) {
    console.log(" do editCommodity: ", commodity);
  }
}
