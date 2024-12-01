import { Component, OnDestroy, OnInit } from "@angular/core";
import { CommodityTypeService } from "@app/service/commodity-type.service";
import { Subscription } from "rxjs";
import { MainLayoutComponent } from "../../../layout/main-layout/main-layout.component";

@Component({
  standalone: true,
  templateUrl: "./commodity-type-mgmt.component.html",
  styleUrl: "./commodity-type-mgmt.component.scss",
  imports: [MainLayoutComponent],
})
export class CommodityTypeMgmtComponent implements OnInit, OnDestroy {
  title = "commodity-type-mgmt";

  private subscriptions: Subscription[] = [];

  constructor(private $commodityTypeService: CommodityTypeService) {}

  ngOnInit() {
    this.subscriptions.push(
      this.$commodityTypeService.findAll().subscribe((list) => {
        console.log({ list });
      })
    );
  }

  ngOnDestroy() {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
