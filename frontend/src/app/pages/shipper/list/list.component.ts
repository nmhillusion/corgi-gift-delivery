import { Component } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { PAGE } from "@app/layout/page.constant";
import { SIZE } from "@app/layout/size.constant";
import {
  ShipperFEModel,
  ShipperModel,
} from "@app/model/business/shipper.model";
import { BasePage } from "@app/pages/base.page";
import { ShipperTypeService } from "@app/service/shipper-type.service";
import { ShipperService } from "@app/service/shipper.service";
import { EditComponent } from "../edit/edit.component";

@Component({
  templateUrl: "./list.component.html",
  styleUrls: ["./list.component.scss"],
  imports: [AppCommonModule, MainLayoutComponent],
})
export class ListComponent extends BasePage {
  tableDatasource = new MatTableDataSource<ShipperFEModel>();

  paginator = this.generatePaginator();

  displayedColumns = [
    "shipperId",
    "shipperCode",
    "shipperName",
    "shipperType",
    ///
    "action",
  ];

  /// METHODS

  constructor(
    private $shipperService: ShipperService,
    private $shipperTypeService: ShipperTypeService
  ) {
    super("Shipper");
  }

  override __ngOnInit__() {
    this.search(PAGE.DEFAULT_PAGE_EVENT);
  }

  override search(pageEvt: PageEvent) {
    this.registerSubscription(
      this.$shipperService
        .search(
          {
            name: "",
          },
          pageEvt.pageIndex,
          pageEvt.pageSize
        )
        .subscribe(async (list) => {
          const shipperFEList = list.content.map((shipper) =>
            this.$shipperService.convertToFEModel(shipper, this)
          );

          this.handlePageDataUpdate(
            {
              page: list.page,
              content: shipperFEList,
            },
            this.paginator,
            this.tableDatasource
          );
        })
    );
  }

  addShipper() {
    console.log("add shipper");

    this.openEditDialog();
  }

  editShipper(shipper: ShipperModel) {
    console.log("edit shipper");

    this.openEditDialog(shipper);
  }

  private openEditDialog(shipper?: ShipperModel) {
    const ref = this.$dialog.open<EditComponent>(EditComponent, {
      width: SIZE.DIALOG.width,
      maxHeight: SIZE.DIALOG.height,
      data: {
        shipper,
      },
    });

    this.registerSubscription(
      ref.afterClosed().subscribe((result) => {
        console.log({ result });
        this.search(PAGE.DEFAULT_PAGE_EVENT);
      })
    );
  }
}
