import { Component, inject, OnDestroy, OnInit } from "@angular/core";
import { Subscription } from "rxjs";
import { DeliveryStatusService } from "../../../service/delivery-status.service";

@Component({
  standalone: true,
  templateUrl: "./list.component.html",
  styleUrl: "./list.component.scss",
})
export class ListComponent implements OnInit, OnDestroy {
  title = "delivery list";
  private $deliveryStatusService = inject(DeliveryStatusService);

  private subscriptions: Subscription[] = [];

  ngOnInit() {
    this.subscriptions.push(
      this.$deliveryStatusService
        .getDeliveryStatus()
        .subscribe((deliveryStatusList) => {
          console.log("list of deliveryStatus = ", deliveryStatusList);
        })
    );
  }

  ngOnDestroy() {
    this.subscriptions.forEach((s) => s.unsubscribe());
  }
}
