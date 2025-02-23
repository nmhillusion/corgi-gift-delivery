import { Component, signal, WritableSignal } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { PAGE } from "@app/layout/page.constant";
import { SIZE } from "@app/layout/size.constant";
import { CommodityExportModel } from "@app/model/business/commodity-export.model";
import { WarehouseModel } from "@app/model/business/warehouse.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { CommodityExportService } from "@app/service/commodity-export.service";
import { WarehouseService } from "@app/service/warehouse.service";
import { EditDialog } from "./edit/edit.component";

@Component({
  standalone: true,
  templateUrl: "./commodity-export.component.html",
  styleUrls: ["./commodity-export.component.scss"],
  imports: [AppCommonModule, MainLayoutComponent],
})
export class CommodityExportComponent extends BasePage {
  warehouseId: string = "";
  warehouse$: WritableSignal<Nullable<WarehouseModel>> = signal(null);

  tableDatasource = new MatTableDataSource<CommodityExportModel>();
  paginator = this.generatePaginator();

  displayedColumns = [
    "exportId",
    "exportName",
    "exportTime",
    ///
    "action",
  ];

  /// methods
  constructor(
    private $warehouseService: WarehouseService,
    private $commodityExportService: CommodityExportService
  ) {
    super("Commodity Export");
  }

  protected override __ngOnInit__() {
    this.warehouseId = this.paramUtils.getParamOrThrow(
      this.$activatedRoute,
      "warehouseId"
    );

    this.registerSubscription(
      this.$warehouseService.findById(this.warehouseId).subscribe((res) => {
        this.warehouse$.set(res);
      })
    );

    this.search(PAGE.DEFAULT_PAGE_EVENT);
  }

  override search(pageEvt: PageEvent) {
    this.registerSubscription(
      this.$commodityExportService
        .search(
          this.warehouseId,
          {
            name: "",
          },
          pageEvt.pageIndex,
          pageEvt.pageSize
        )
        .subscribe((pageRes) => {
          this.handlePageDataUpdate(
            pageRes,
            this.paginator,
            this.tableDatasource
          );
        })
    );
  }

  listItems(ex: CommodityExportModel) {
    this.$router.navigate([ex.exportId, "list"], {
      relativeTo: this.$activatedRoute,
    });
  }

  addCommodityExport() {
    this.openEditDialog();
  }

  editCommodityExport(ex: CommodityExportModel) {
    this.openEditDialog(ex);
  }

  private openEditDialog(ex?: CommodityExportModel) {
    const dialogRef = this.$dialog.open(EditDialog, {
      width: SIZE.DIALOG.width,
      maxHeight: SIZE.DIALOG.height,
      data: {
        commodityExport: ex || {
          warehouseId: this.warehouseId,
        },
      },
    });

    this.registerSubscription(
      dialogRef.afterClosed().subscribe((res) => {
        this.search(PAGE.DEFAULT_PAGE_EVENT);
      })
    );
  }
}
