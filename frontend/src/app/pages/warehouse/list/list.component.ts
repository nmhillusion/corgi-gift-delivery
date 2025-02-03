import { Component, signal } from "@angular/core";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { WarehouseModel } from "@app/model/business/warehouse.model";
import { BasePage } from "@app/pages/base.page";
import { EditComponent } from "../edit/edit.component";
import { SIZE } from "@app/layout/size.constant";
import { WarehouseService } from "@app/service/warehouse.service";
import { ImportDialogComponent } from "./import-dialog/import-dialog.component";

@Component({
  templateUrl: "./list.component.html",
  styleUrls: ["./list.component.scss"],
  imports: [AppCommonModule, MainLayoutComponent],
})
export class ListComponent extends BasePage {
  warehouseList$ = signal<WarehouseModel[]>([]);

  /// METHODS

  constructor(private $warehouseService: WarehouseService) {
    super("Warehouse");
  }

  override __ngOnInit__() {
    this.search();
  }

  search() {
    this.registerSubscription(
      this.$warehouseService.findAll().subscribe((result) => {
        console.log("warehouseList: ", result);

        this.warehouseList$.set(result);
      })
    );
  }

  addWarehouse() {
    console.log("add warehouse");

    this.openEditDialog();
  }

  editWarehouse(warehouse: WarehouseModel) {
    this.openEditDialog(warehouse);
  }

  private openEditDialog(warehouse?: WarehouseModel) {
    const ref = this.$dialog.open<EditComponent>(EditComponent, {
      width: SIZE.DIALOG.width,
      maxHeight: SIZE.DIALOG.height,
      data: {
        warehouse,
      },
    });

    this.registerSubscription(
      ref.afterClosed().subscribe((result) => {
        this.search();
      })
    );
  }

  importWarehouse() {
    const ref = this.$dialog.open<ImportDialogComponent>(
      ImportDialogComponent,
      {
        width: SIZE.DIALOG.width,
        maxHeight: SIZE.DIALOG.height,
      }
    );

    this.registerSubscription(
      ref.afterClosed().subscribe((result) => {
        this.search();
      })
    );
  }

  importToWarehouse(wh: WarehouseModel) {
    this.$router.navigate([wh.warehouseId, "commodity-import"], {
      relativeTo: this.$activatedRoute.parent,
    });
  }

  listCommodityImport(wh: WarehouseModel) {
    this.$router.navigate([wh.warehouseId, "warehouse-item"], {
      relativeTo: this.$activatedRoute.parent,
    });
  }

  exportFromWarehouse(wh: WarehouseModel) {
    this.$router.navigate([wh.warehouseId, "commodity-export"], {
      relativeTo: this.$activatedRoute.parent,
    });
  }
}
