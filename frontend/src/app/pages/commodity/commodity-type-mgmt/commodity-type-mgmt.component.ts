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

@Component({
  standalone: true,
  templateUrl: "./commodity-type-mgmt.component.html",
  styleUrl: "./commodity-type-mgmt.component.scss",
  imports: [MainLayoutComponent, AppCommonModule],
})
export class CommodityTypeMgmtComponent extends BasePage {
  /// FIELDS
  commodityTypeList$: WritableSignal<CommodityTypeModel[]> = signal([]);

  importFile$ = new BehaviorSubject<File[]>([]);

  /// METHODS
  constructor(private $commodityTypeService: CommodityTypeService) {
    super("Commodity Type Mgmt");
  }

  override __ngOnInit__() {
    this.initLoadData();

    this.registerSubscription(
      this.importFile$.subscribe((evt) => {
        console.log("update import file to ", evt);
      })
    );
  }

  private initLoadData() {
    this.registerSubscription(
      this.$commodityTypeService.findAll().subscribe((list) => {
        console.log({ list });

        this.commodityTypeList$.set(list);
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
        this.initLoadData();
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
        this.initLoadData();
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
