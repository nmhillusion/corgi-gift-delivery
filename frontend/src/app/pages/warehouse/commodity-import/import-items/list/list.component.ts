import { Component, signal } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { WarehouseItemModel } from "@app/model/business/warehouse-item.model";
import { PaginatorHandler } from "@app/model/core/page.model";
import { BasePage } from "@app/pages/base.page";
import { EditDialog } from "../edit/edit.component";
import { SIZE } from "@app/layout/size.constant";
import { PAGE } from "@app/layout/page.constant";

@Component({
  templateUrl: "./list.component.html",
  styleUrls: ["./list.component.scss"],
  imports: [AppCommonModule, MainLayoutComponent],
})
export class ListComponent extends BasePage {
  tableDatasource = new MatTableDataSource<WarehouseItemModel>();

  paginator: PaginatorHandler = {
    pageIndex$: signal(0),
    pageSizeOptions$: signal(PAGE.SIZE_OPTIONS),
    length$: signal(0),
    pageSize$: signal(0),
    onPageChange: (evt) => {
      this.search(evt);
    },
  };

  displayedColumns = [
    "itemId",
    "importId",
    "warehouseId",
    "comId",
    "quantity",
    "createTime",
  ];

  /// methods
  constructor() {
    super("Import Items");
  }

  search(pageEvt: PageEvent) {}

  private openEditDialog(warehouseItem?: WarehouseItemModel) {
    const dialogRef = this.$dialog.open(EditDialog, {
      data: {
        /// input commodity import
        warehouseItem,
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

  editImportItems(warehouseItem: WarehouseItemModel) {
    this.openEditDialog(warehouseItem);
  }
}
