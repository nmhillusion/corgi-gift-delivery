import { Component, inject } from "@angular/core";
import { BasePage } from "@app/pages/base.page";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { DeliveryStatusService } from "@app/service/delivery-status.service";
import { AppCommonModule } from "@app/core/app-common.module";

@Component({
  standalone: true,
  imports: [
    MainLayoutComponent,
    AppCommonModule
  ],
  templateUrl: "./list.component.html",
  styleUrl: "./list.component.scss",
})
export class ListComponent extends BasePage {
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
