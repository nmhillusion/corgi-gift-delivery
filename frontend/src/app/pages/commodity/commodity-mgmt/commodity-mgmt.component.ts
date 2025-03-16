import { Component } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { PAGE } from "@app/layout/page.constant";
import { SIZE } from "@app/layout/size.constant";
import {
  CommodityFEModel,
  CommodityModel,
} from "@app/model/business/commodity.model";
import { BasePage } from "@app/pages/base.page";
import { CommodityService } from "./commodity.service";
import { EditComponent } from "./edit/edit.component";
import { ImportComponent } from "./import/import.component";

@Component({
  standalone: true,
  templateUrl: "./commodity-mgmt.component.html",
  styleUrl: "./commodity-mgmt.component.scss",
  imports: [MainLayoutComponent, AppCommonModule],
})
export class CommodityMgmtComponent extends BasePage {
  $$tableDataSource = new MatTableDataSource<CommodityFEModel>();

  paginator = this.generatePaginator();

  displayedColumns = [
    "comId",
    "comName",
    "comType",
    ///
    "action",
  ];

  /// METHODS

  constructor(private $commodityService: CommodityService) {
    super("Commodity Mgmt");
  }

  override __ngOnInit__() {
    this.search(PAGE.DEFAULT_PAGE_EVENT);
  }

  override search(pageEvt: PageEvent): void {
    this.registerSubscription(
      this.$commodityService
        .search(
          {
            keyword: "",
          },
          pageEvt.pageIndex,
          pageEvt.pageSize
        )
        .subscribe((pageResult) => {
          console.log({ pageResult });

          this.handlePageDataUpdate<CommodityFEModel>(
            {
              content: pageResult.content.map((it) =>
                this.$commodityService.convertToFe(it, this)
              ),
              page: pageResult.page,
            },
            this.paginator,
            this.$$tableDataSource
          );
        })
    );
  }

  addCommodity() {
    console.log(" do addCommodity ");

    this.openEditDialog();
  }

  editCommodity(commodity: CommodityModel) {
    console.log(" do editCommodity: ", commodity);

    this.openEditDialog(commodity);
  }

  private openEditDialog(commodity?: CommodityModel) {
    const ref = this.$dialog.open<EditComponent>(EditComponent, {
      width: SIZE.DIALOG.width,
      maxHeight: SIZE.DIALOG.height,
      data: {
        commodity,
      },
    });

    this.registerSubscription(
      ref.afterClosed().subscribe((result) => {
        this.search(PAGE.DEFAULT_PAGE_EVENT);
      })
    );
  }

  importCommodity() {
    const ref = this.$dialog.open<ImportComponent>(ImportComponent, {
      width: SIZE.DIALOG.width,
      maxHeight: SIZE.DIALOG.height,
    });

    this.registerSubscription(
      ref.afterClosed().subscribe((result) => {
        this.search(PAGE.DEFAULT_PAGE_EVENT);
      })
    );
  }
}
