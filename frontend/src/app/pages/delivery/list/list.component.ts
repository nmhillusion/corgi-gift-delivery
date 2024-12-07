import { Component, inject } from "@angular/core";
import { BasePage } from "@app/pages/base.page";
import { MainLayoutComponent } from "../../../layout/main-layout/main-layout.component";
import { DeliveryStatusService } from "../../../service/delivery-status.service";

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
