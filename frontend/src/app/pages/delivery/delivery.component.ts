import { Component } from "@angular/core";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { BasePage } from "../base.page";
import { MatTableDataSource, MatTableModule } from "@angular/material/table";
import { Delivery } from "@app/model/business/delivery.model";
import { AppCommonModule } from "@app/core/app-common.module";

@Component({
  standalone: true,
  templateUrl: "./delivery.component.html",
  styleUrl: "./delivery.component.scss",
  imports: [MainLayoutComponent, MatTableModule, AppCommonModule],
})
export class DeliveryComponent extends BasePage {
  // fields
  displayedColumns = [
    "deliveryId",
    "eventId",
    "deliveryPeriodYear",
    "deliveryPeriodMonth",
    "territory",
    "region",
    "organId",
    "receivedOrgan",
    "amdName",
    "customerLevel",
    "customerId",
    "customerName",
    "idCardNumber",
    "phoneNumber",
    "address",
    "giftName",
    "note",
  ];
  dataSource = new MatTableDataSource<Delivery>([]);

  paginator = this.generatePaginator();

  // methods
  constructor() {
    super();
  }
}
