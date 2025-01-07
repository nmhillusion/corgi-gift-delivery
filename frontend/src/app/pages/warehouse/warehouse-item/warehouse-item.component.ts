import { Component, signal } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { PAGE } from "@app/layout/page.constant";
import {
  WarehouseItemFEModel,
  WarehouseItemModel,
} from "@app/model/business/warehouse-item.model";
import { Page, PaginatorHandler } from "@app/model/core/page.model";
import { BasePage } from "@app/pages/base.page";
import { WarehouseItemService } from "@app/service/warehouse-item.service";

@Component({
  templateUrl: "./warehouse-item.component.html",
  styleUrls: ["./warehouse-item.component.scss"],
  imports: [AppCommonModule, MainLayoutComponent],
})
export class WarehouseItemComponent extends BasePage {
  warehouseId: string = "";
  tableDatasource = new MatTableDataSource<WarehouseItemFEModel>();

  paginator: PaginatorHandler = {
    length$: signal(0),
    pageIndex$: signal(0),
    pageSize$: signal(10),
    pageSizeOptions$: signal(PAGE.SIZE_OPTIONS),
    onPageChange: (evt) => {
      this.search(evt);
    },
  };

  displayedColumns = ["itemId", "importId", "comId", "quantity", "createTime"];

  /// methods
  constructor(private $warehouseItemService: WarehouseItemService) {
    super("Warehouse Items");
  }

  protected override __ngOnInit__() {
    this.warehouseId = this.paramUtils.getParamOrThrow(
      this.$activatedRoute,
      "warehouseId"
    );

    this.search(PAGE.DEFAULT_PAGE_EVENT);
  }

  search(pageEvt: PageEvent) {
    this.registerSubscription(
      this.$warehouseItemService
        .searchItemsInWarehouse(
          this.warehouseId,
          pageEvt.pageIndex,
          pageEvt.pageSize,
          {
            name: "",
          }
        )
        .subscribe((res) => {
          const convertedFEList = res.content.map((item) => {
            return this.$warehouseItemService.convertToWarehouseItemFE(
              item,
              this
            );
          });

          const convertedRes: Page<WarehouseItemFEModel> = {
            ...res,
            content: convertedFEList,
          };
          this.handlePageDataUpdate(
            convertedRes,
            this.paginator,
            this.tableDatasource
          );
        })
    );
  }
}
