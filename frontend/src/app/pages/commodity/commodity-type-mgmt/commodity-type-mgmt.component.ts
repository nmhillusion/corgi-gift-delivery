import { Component, OnDestroy, OnInit } from "@angular/core";
import { CommodityTypeService } from "@app/service/commodity-type.service";
import { Subscription } from "rxjs";
import { MainLayoutComponent } from "../../../layout/main-layout/main-layout.component";
import { BasePage } from "@app/pages/base.page";

@Component({
  standalone: true,
  templateUrl: "./commodity-type-mgmt.component.html",
  styleUrl: "./commodity-type-mgmt.component.scss",
  imports: [MainLayoutComponent],
})
export class CommodityTypeMgmtComponent extends BasePage {
  constructor(private $commodityTypeService: CommodityTypeService) {
    super("Commodity Type Mgmt");
  }

  override __ngOnInit__() {
    this.registerSubscription(
      this.$commodityTypeService.findAll().subscribe((list) => {
        console.log({ list });
      })
    );
  }
}
