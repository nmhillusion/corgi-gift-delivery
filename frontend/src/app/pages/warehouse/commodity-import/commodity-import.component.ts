import { Component, signal } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { PAGE } from "@app/layout/page.constant";
import { SIZE } from "@app/layout/size.constant";
import { CommodityImportModel } from "@app/model/business/commodity-import.model";
import { WarehouseModel } from "@app/model/business/warehouse.model";
import { Nullable } from "@app/model/core/nullable.model";
import { PaginatorHandler } from "@app/model/core/page.model";
import { BasePage } from "@app/pages/base.page";
import { CommodityImportService } from "@app/service/commodity-import.service";
import { WarehouseService } from "@app/service/warehouse.service";
import { EditDialog } from "./edit/edit.component";

@Component({
  templateUrl: "./commodity-import.component.html",
  styleUrls: ["./commodity-import.component.scss"],
  imports: [AppCommonModule, MainLayoutComponent],
})
export class CommodityImportComponent extends BasePage {
  warehouseId: number = 0;
  warehouse$ = signal<Nullable<WarehouseModel>>(null);

  tableDatasource = new MatTableDataSource<CommodityImportModel>();

  paginator: PaginatorHandler = {
    pageIndex$: signal(0),
    pageSize$: signal(10),
    pageSizeOptions$: signal(PAGE.SIZE_OPTIONS),
    length$: signal(0),
    onPageChange: (pageEvt) => {
      this.search(pageEvt);
    },
  };

  displayedColumns = ["importId", "importName", "importTime", "action"];

  /// Methods
  constructor(
    private $warehouseService: WarehouseService,
    private $commodityImportService: CommodityImportService
  ) {
    super("Commodity Import");
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
      this.$commodityImportService
        .search("", pageEvt.pageIndex, pageEvt.pageSize)
        .subscribe((result) => {
          this.handlePageDataUpdate<CommodityImportModel>(
            result,
            this.paginator,
            this.tableDatasource
          );
        })
    );
  }

  onBack() {
    this.$router.navigate(["list"], {
      relativeTo: this.$activatedRoute.parent,
    });
  }

  private openEditDialog(commodityImport?: CommodityImportModel) {
    const dialogRef = this.$dialog.open(EditDialog, {
      data: {
        commodityImport,
      },
      width: SIZE.DIALOG.width,
      maxHeight: SIZE.DIALOG.height,
    });

    this.registerSubscription(
      dialogRef.afterClosed().subscribe((_) => {
        this.search(PAGE.DEFAULT_PAGE_EVENT);
      })
    );
  }

  addCommodityImport() {
    this.openEditDialog({
      warehouseId: this.warehouseId,
    });
  }

  editCommodityImport(commodityImport: CommodityImportModel) {
    this.openEditDialog(commodityImport);
  }

  listItems(commodityImport: CommodityImportModel) {
    this.$router.navigate([commodityImport.importId, "items"], {
      relativeTo: this.$activatedRoute,
    });
  }
}
