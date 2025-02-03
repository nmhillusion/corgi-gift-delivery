import { Component, inject, model, signal } from "@angular/core";
import { BasePage } from "@app/pages/base.page";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { DeliveryStatusService } from "@app/service/delivery-status.service";
import { AppCommonModule } from "@app/core/app-common.module";
import { AppSelectCommodityWidget } from "@app/pages/shared/commodity/app-select-commotity/widget.component";
import { CommodityModel } from "@app/model/business/commodity.model";
import { Nullable } from "@app/model/core/nullable.model";
import { IdType } from "@app/model/core/id.model";
import { AppSelectRecipientWidget } from "@app/pages/shared/recipient/app-select-recipient/widget.component";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { PAGE } from "@app/layout/page.constant";
import {
  DeliveryFEModel,
  DeliveryModel,
} from "@app/model/business/delivery.model";
import { PaginatorHandler } from "@app/model/core/page.model";
import { DeliveryService } from "@app/service/delivery.service";
import { EditComponent } from "../edit/edit.component";
import { SIZE } from "@app/layout/size.constant";

@Component({
  standalone: true,
  imports: [
    MainLayoutComponent,
    AppCommonModule,
    ///
  ],
  templateUrl: "./list.component.html",
  styleUrl: "./list.component.scss",
})
export class ListComponent extends BasePage {
  tableDatasource = new MatTableDataSource<DeliveryFEModel>();
  paginator = this.generatePaginator();

  displayedColumns = [
    "deliveryId",
    "recipient",
    "commodity",
    "commodityQuantity",
    "deliveryStatus",
    "startTime",
    "endTime",
    "currentAttempt",
    ///
    "action",
  ];

  /// methods
  constructor(private $deliveryService: DeliveryService) {
    super("Delivery");
  }

  override __ngOnInit__() {
    this.search(PAGE.DEFAULT_PAGE_EVENT);
  }

  override search(pageEvt: PageEvent) {
    this.registerSubscription(
      this.$deliveryService
        .search(
          {
            name: "",
          },
          pageEvt.pageIndex,
          pageEvt.pageSize
        )
        .subscribe((result) => {
          this.handlePageDataUpdate<DeliveryFEModel>(
            {
              page: result.page,
              content: result.content.map((delivery) =>
                this.$deliveryService.convertToFEModel(delivery, this)
              ),
            },
            this.paginator,
            this.tableDatasource
          );
        })
    );
  }

  addDelivery() {
    this.openEditDialog();
  }

  private openEditDialog(delivery?: DeliveryModel) {
    const ref = this.$dialog.open<EditComponent>(EditComponent, {
      width: SIZE.DIALOG.width,
      maxHeight: SIZE.DIALOG.height,
      data: {
        delivery,
      },
    });

    this.registerSubscription(
      ref.afterClosed().subscribe((result) => {
        this.search(PAGE.DEFAULT_PAGE_EVENT);
      })
    );
  }

  editDelivery(delivery: DeliveryModel) {
    this.openEditDialog(delivery);
  }

  viewDeliveryAttempt(delivery: DeliveryModel) {
    console.log("do viewDeliveryAttempt: ", delivery);
  }
}
