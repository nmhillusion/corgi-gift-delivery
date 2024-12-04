import { Component } from "@angular/core";
import { MainLayoutComponent } from "../../../layout/main-layout/main-layout.component";
import { BasePage } from "@app/pages/base.page";
import { AppCommonModule } from "@app/core/app-common.module";

@Component({
  standalone: true,
  templateUrl: "./commodity-mgmt.component.html",
  styleUrl: "./commodity-mgmt.component.scss",
  imports: [MainLayoutComponent, AppCommonModule],
})
export class CommodityMgmtComponent extends BasePage {
  constructor() {
    super("Commodity Mgmt");
  }

  show() {
    this.dialogHandler.confirm("Are you sure?").then((result_) => {
      console.log("==> You sure? ", result_);
    });
  }
}
