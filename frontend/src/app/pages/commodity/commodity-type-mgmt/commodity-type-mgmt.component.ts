import { Component, signal, WritableSignal } from "@angular/core";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { SIZE } from "@app/layout/size.constant";
import { CommodityTypeModel } from "@app/model/business/commodity-type.model";
import { BasePage } from "@app/pages/base.page";
import { CommodityTypeService } from "@app/service/commodity-type.service";
import { BlobUtil } from "@app/util/blob.util";
import { BehaviorSubject } from "rxjs";
import { EditComponent } from "./edit/edit.component";
import { ImportComponent } from "./import/import.component";
import { MatTableDataSource } from "@angular/material/table";
import { PageEvent } from "@angular/material/paginator";
import { PAGE } from "@app/layout/page.constant";

@Component({
  standalone: true,
  templateUrl: "./commodity-type-mgmt.component.html",
  styleUrl: "./commodity-type-mgmt.component.scss",
  imports: [MainLayoutComponent, AppCommonModule],
})
export class CommodityTypeMgmtComponent extends BasePage {
  /// FIELDS
  commodityTypeDataSource = new MatTableDataSource<CommodityTypeModel>([]);

  importFile$ = new BehaviorSubject<File[]>([]);

  displayedColumns = ["typeId", "typeName"];

  paginator = this.generatePaginator();

  /// METHODS
  constructor(private $commodityTypeService: CommodityTypeService) {
    super("Commodity Type Mgmt");
  }

  override __ngOnInit__() {
    this.search(PAGE.DEFAULT_PAGE_EVENT);

    this.registerSubscription(
      this.importFile$.subscribe((evt) => {
        console.log("update import file to ", evt);
      })
    );
  }

  override search(pageEvt: PageEvent): void {
    this.registerSubscription(
      this.$commodityTypeService
        .search(
          {
            keyword: "",
          },
          pageEvt.pageIndex,
          pageEvt.pageSize
        )
        .subscribe((pageResult) => {
          console.log({ pageResult });

          this.handlePageDataUpdate(
            pageResult,
            this.paginator,
            this.commodityTypeDataSource
          );
        })
    );
  }

  addCommodityType() {
    console.log(" do addCommodityType ");

    this.openEditDialog();
  }

  editCommodityType(commodityType: CommodityTypeModel) {
    console.log(" do editCommodityType ", commodityType);

    this.openEditDialog(commodityType);
  }

  private openEditDialog(commodityType?: CommodityTypeModel) {
    const ref = this.$dialog.open<EditComponent>(EditComponent, {
      width: "600px",
      maxHeight: "600px",
      data: {
        commodityType,
      },
    });

    this.registerSubscription(
      ref.afterClosed().subscribe((result) => {
        this.search(PAGE.DEFAULT_PAGE_EVENT);
      })
    );
  }

  importCommodityType() {
    const dialogRef = this.$dialog.open<ImportComponent>(ImportComponent, {
      width: SIZE.DIALOG.width,
      maxHeight: SIZE.DIALOG.height,
    });

    this.registerSubscription(
      dialogRef.afterClosed().subscribe((_) => {
        this.search(PAGE.DEFAULT_PAGE_EVENT);
      })
    );
  }

  exportCommodityType() {
    this.registerSubscription(
      this.$commodityTypeService.exportExcel().subscribe((data) => {
        BlobUtil.downloadBlob(data, "commodityType.xlsx");
      })
    );
  }
}
