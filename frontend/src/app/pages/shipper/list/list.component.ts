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

@Component({
  templateUrl: "./list.component.html",
  styleUrls: ["./list.component.scss"],
  imports: [AppCommonModule, MainLayoutComponent],
})
export class ListComponent extends BasePage {
  shipperList$ = signal<ShipperFEModel[]>([]);

  /// METHODS

  constructor(
    private $shipperService: ShipperService,
    private $shipperTypeService: ShipperTypeService
  ) {
    super("Shipper");
  }

  override __ngOnInit__() {
    this.initLoadData();
  }

  private initLoadData() {
    this.registerSubscription(
      this.$shipperService.search("", 0, 10).subscribe(async (list) => {
        const shipperFEList = await Promise.all(
          list.content.map(async (shipper) => {
            const shipperFE = shipper as ShipperFEModel;

            shipperFE.shipperType = await firstValueFrom(
              this.$shipperTypeService.findById(shipper.shipperTypeId || "")
            );

            return shipperFE;
          })
        );

        this.shipperList$.set(shipperFEList);
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
        this.initLoadData();
      })
    );
  }
}
