import { Component } from "@angular/core";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { BasePage } from "@app/pages/base.page";
import { AppCommonModule } from "@app/core/app-common.module";
import { CommodityService } from "./commodity.service";

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

  show() {
    this.dialogHandler.confirm("Are you sure?").then((result_) => {
      console.log("==> You sure? ", result_);
    });
  }
}
