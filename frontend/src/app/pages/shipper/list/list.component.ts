import { Component, signal } from "@angular/core";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import {
  ShipperFEModel,
  ShipperModel,
} from "@app/model/business/shipper.model";
import { BasePage } from "@app/pages/base.page";
import { EditComponent } from "../edit/edit.component";
import { SIZE } from "@app/layout/size.constant";
import { ShipperService } from "@app/service/shipper.service";
import { ShipperTypeService } from "@app/service/shipper-type.service";
import { firstValueFrom } from "rxjs";
import { MatTableDataSource } from "@angular/material/table";
import { Page, PaginatorHandler } from "@app/model/core/page.model";
import { PageEvent } from "@angular/material/paginator";

@Component({
  templateUrl: "./list.component.html",
  styleUrls: ["./list.component.scss"],
  imports: [AppCommonModule, MainLayoutComponent],
})
export class ListComponent extends BasePage {
  shipperList$ = signal<ShipperFEModel[]>([]);
  shipperDataSource = new MatTableDataSource<ShipperFEModel>([]);
  displayedColumns = ["shipperCode", "shipperName", "shipperType", "action"];

  FIRST_PAGE_EVENT: PageEvent = { pageIndex: 0, pageSize: 10, length: 0 };

  paginatorHandler: PaginatorHandler = {
    length$: signal(0),
    pageSize$: signal(10),
    pageIndex$: signal(0),
    onPageChange: (pageEvent: PageEvent) => {
      this.searchData(pageEvent);
    },
  };

  /// METHODS

  constructor(
    private $shipperService: ShipperService,
    private $shipperTypeService: ShipperTypeService
  ) {
    super("Shipper");
  }

  override __ngOnInit__() {
    this.searchData(this.FIRST_PAGE_EVENT);
  }

  private searchData(pageEvent: PageEvent) {
    this.registerSubscription(
      this.$shipperService
        .search("", pageEvent.pageIndex, pageEvent.pageSize)
        .subscribe(async (pageResult) => {
          const shipperFEList = await Promise.all(
            pageResult.content.map(async (shipper) => {
              const shipperFE = shipper as ShipperFEModel;

              shipperFE.shipperType = await firstValueFrom(
                this.$shipperTypeService.findById(shipper.shipperTypeId || "")
              );

              return shipperFE;
            })
          );

          this.shipperList$.set(shipperFEList);
          this.loadTableData(pageResult);
        })
    );
  }

  private loadTableData(pageResult: Page<ShipperModel>) {
    this.handlePageDataUpdate(
      pageResult,
      this.paginatorHandler,
      this.shipperDataSource
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
        this.searchData(this.FIRST_PAGE_EVENT);
      })
    );
  }
}
