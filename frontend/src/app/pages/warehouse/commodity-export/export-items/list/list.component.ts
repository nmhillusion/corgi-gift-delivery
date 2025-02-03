import { Component, signal } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { PAGE } from "@app/layout/page.constant";
import { SIZE } from "@app/layout/size.constant";
import { CommodityExportModel } from "@app/model/business/commodity-export.model";
import { WarehouseExportItemModel } from "@app/model/business/warehouse-export-item.model";
import { Nullable } from "@app/model/core/nullable.model";
import { PaginatorHandler } from "@app/model/core/page.model";
import { BasePage } from "@app/pages/base.page";
import { CommodityExportService } from "@app/service/commodity-export.service";
import { WarehouseExportItemService } from "@app/service/warehouse-export-item.service";
import { EditDialog } from "../edit/edit.component";

@Component({
  templateUrl: "./list.component.html",
  styleUrls: ["./list.component.scss"],
  imports: [AppCommonModule, MainLayoutComponent],
})
export class ListComponent extends BasePage {
  private commodityeExportId: string = "";
  commodityExport$ = signal<Nullable<CommodityExportModel>>(null);

  tableDatasource = new MatTableDataSource<WarehouseExportItemModel>();

  paginator = this.generatePaginator();

  displayedColumns = [
    "itemId",
    "exportId",
    "warehouseId",
    "comId",
    "quantity",
    "createTime",
  ];

  /// methods
  constructor(
    private $commodityExportService: CommodityExportService,
    private $warehouseExportItemService: WarehouseExportItemService
  ) {
    super("Export Items");
  }

  protected override __ngOnInit__() {
    this.commodityeExportId = this.paramUtils.getParamOrThrow(
      this.$activatedRoute,
      "commodityExportId"
    );

    this.registerSubscription(
      this.$commodityExportService
        .findById(this.commodityeExportId)
        .subscribe((commodityExport) => {
          this.commodityExport$.set(commodityExport);
        })
    );

    this.search(PAGE.DEFAULT_PAGE_EVENT);
  }

  override search(pageEvt: PageEvent) {
    this.registerSubscription(
      this.$warehouseExportItemService
        .search(
          this.commodityeExportId,
          {
            name: "",
          },
          pageEvt.pageIndex,
          pageEvt.pageSize
        )
        .subscribe((result) => {
          console.log("result of search items in export: ", result);

          this.handlePageDataUpdate<WarehouseExportItemModel>(
            result,
            this.paginator,
            this.tableDatasource
          );
        })
    );
  }

  private openEditDialog(warehouseExportItem?: WarehouseExportItemModel) {
    const dialogRef = this.$dialog.open(EditDialog, {
      data: {
        commodityExport: this.commodityExport$(),
        warehouseExportItem,
      },
      width: SIZE.DIALOG.width,
      maxHeight: SIZE.DIALOG.height,
    });

    this.registerSubscription(
      dialogRef.afterClosed().subscribe((r) => {
        this.search(PAGE.DEFAULT_PAGE_EVENT);
      })
    );
  }

  addImportItems() {
    this.openEditDialog();
  }

  editExportItems(warehouseExportItem: WarehouseExportItemModel) {
    this.openEditDialog(warehouseExportItem);
  }
}
