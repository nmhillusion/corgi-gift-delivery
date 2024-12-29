import { Component, signal } from "@angular/core";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { BasePage } from "@app/pages/base.page";
import { AppCommonModule } from "@app/core/app-common.module";
import { CommodityService } from "./commodity.service";
import { CommodityModel } from "@app/model/business/commodity.model";
import { EditComponent } from "./edit/edit.component";
import { ImportComponent } from "./import/import.component";
import { SIZE } from "@app/layout/size.constant";

@Component({
  standalone: true,
  templateUrl: "./commodity-mgmt.component.html",
  styleUrl: "./commodity-mgmt.component.scss",
  imports: [MainLayoutComponent, AppCommonModule],
})
export class CommodityMgmtComponent extends BasePage {
  commodityList$ = signal<CommodityModel[]>([]);

  /// METHODS

  constructor(private $commodityService: CommodityService) {
    super("Commodity Mgmt");
  }

  override __ngOnInit__() {
    this.initLoadData();
  }

  private initLoadData() {
    this.registerSubscription(
      this.$commodityService.findAll().subscribe((list) => {
        console.log({ list });

        this.commodityList$.set(list);
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
        this.initLoadData();
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
        this.initLoadData();
      })
    );
  }
}
