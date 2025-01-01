import { Component, signal } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { PAGE } from "@app/layout/page.constant";
import { WarehouseItemModel } from "@app/model/business/warehouse-item.model";
import { WarehouseModel } from "@app/model/business/warehouse.model";
import { Nullable } from "@app/model/core/nullable.model";
import { PaginatorHandler } from "@app/model/core/page.model";
import { BasePage } from "@app/pages/base.page";
import { WarehouseItemService } from "@app/service/warehouse-item.service";
import { WarehouseService } from "@app/service/warehouse.service";

@Component({
  templateUrl: "./import-item.component.html",
  styleUrls: ["./import-item.component.scss"],
  imports: [AppCommonModule, MainLayoutComponent],
})
export class ImportItemComponent extends BasePage {
  warehouseId: number = 0;
  warehouse$ = signal<Nullable<WarehouseModel>>(null);

  tableDatasource = new MatTableDataSource<WarehouseItemModel>();

  paginator: PaginatorHandler = {
    pageIndex$: signal(0),
    pageSize$: signal(10),
    pageSizeOptions$: signal(PAGE.SIZE_OPTIONS),
    length$: signal(0),
    onPageChange: (pageEvt) => {
      this.search(pageEvt);
    },
  };

  /// Methods
  constructor(
    private $warehouseService: WarehouseService,
    private $warehouseItemService: WarehouseItemService
  ) {
    super("Import Item");
  }

  protected override __ngOnInit__() {
    const warehouseIdParam =
      this.$activatedRoute.snapshot.paramMap.get("warehouseId");
    if (warehouseIdParam) {
      this.warehouseId = parseInt(warehouseIdParam);
    }

    this.registerSubscription(
      this.$warehouseService
        .findById(this.warehouseId)
        .subscribe((warehouse) => {
          this.warehouse$.set(warehouse);
        })
    );

    this.search(PAGE.DEFAULT_PAGE_EVENT);
  }

  private search(pageEvt: PageEvent) {
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
        .subscribe((result) => {
          this.handlePageDataUpdate<WarehouseItemModel>(
            result,
            this.paginator,
            this.tableDatasource
          );
        })
    );
  }
}
