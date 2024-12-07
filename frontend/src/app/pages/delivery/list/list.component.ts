import { Component, inject, OnDestroy, OnInit } from "@angular/core";
import { Subscription } from "rxjs";
import { DeliveryStatusService } from "../../../service/delivery-status.service";
import { MainLayoutComponent } from "../../../layout/main-layout/main-layout.component";
import { BasePage } from "@app/pages/base.page";

@Component({
  standalone: true,
  imports: [
    MainLayoutComponent
  ],
  templateUrl: "./list.component.html",
  styleUrl: "./list.component.scss",
})
export class ListComponent extends BasePage {
  title = "delivery list";
  private $deliveryStatusService = inject(DeliveryStatusService);

  override __ngOnInit__() {
    this.registerSubscription(
      this.$deliveryStatusService
        .getDeliveryStatus()
        .subscribe((deliveryStatusList) => {
          console.log("list of deliveryStatus = ", deliveryStatusList);
        })
    );
  }
}
