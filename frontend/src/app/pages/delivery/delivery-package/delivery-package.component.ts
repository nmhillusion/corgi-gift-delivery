import { Component, computed, signal } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { PAGE } from "@app/layout/page.constant";
import { DeliveryPackageItemModel } from "@app/model/business/delivery-package-item.model";
import { DeliveryFEModel } from "@app/model/business/delivery.model";
import { WarehouseItemFEModel } from "@app/model/business/warehouse-item.model";
import { WarehouseModel } from "@app/model/business/warehouse.model";
import { IdType } from "@app/model/core/id.model";
import { Nullable } from "@app/model/core/nullable.model";
import { mapPage } from "@app/model/core/page.model";
import { BasePage } from "@app/pages/base.page";
import { DeliveryPackageItemService } from "@app/service/delivery-package-item.service";
import { DeliveryService } from "@app/service/delivery.service";
import { WarehouseExportItemService } from "@app/service/warehouse-export-item.service";
import { WarehouseService } from "@app/service/warehouse.service";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { WarehouseItemService } from "@app/service/warehouse-item.service";

@Component({
  standalone: true,
  templateUrl: "./delivery-package.component.html",
  styleUrls: ["./delivery-package.component.scss"],
  imports: [AppCommonModule, MainLayoutComponent],
})
export class DeliveryPackageComponent extends BasePage {
  packageId?: IdType;
  delivery$ = signal<Nullable<DeliveryFEModel>>(null);

  warehouseList$ = signal<WarehouseModel[]>([]);
  warehouseItemList$ = signal<WarehouseItemFEModel[]>([]);

  deliveryPackageItemDatasource$ =
    new MatTableDataSource<DeliveryPackageItemModel>();

  currentCollectedComQuantity$ = signal<number>(0);
  currectLackingOfQuantity$ = computed(() => {
    return (
      (this.delivery$()?.comQuantity || 0) - this.currentCollectedComQuantity$()
    );
  });

  paginator = this.generatePaginator();

  formGroup = new FormGroup({
    warehouseId: new FormControl<Nullable<IdType>>(null, [Validators.required]),
    warehouseItemId: new FormControl<Nullable<IdType>>(null, [
      Validators.required,
    ]),
    quantity: new FormControl<number>(0, [
      Validators.required,
      Validators.min(0.1),
    ]),
  });

  displayedColumns = [
    "itemId",
    "warehouse",
    "quantity",
    ///
    "action",
  ];

  /// methods
  constructor(
    private $warehouseService: WarehouseService,
    private $warehouseItemService: WarehouseItemService,
    private $deliveryService: DeliveryService,
    private $deliveryPackageItemService: DeliveryPackageItemService
  ) {
    super("Collect package for delivery");
  }

  protected override __ngOnInit__() {
    const deliveryId: IdType = this.paramUtils.getParamOrThrow(
      this.$activatedRoute,
      "deliveryId"
    );

    this.registerSubscription(
      this.$deliveryService.findById(deliveryId).subscribe((delivery) => {
        this.delivery$.set(
          this.$deliveryService.convertToFEModel(delivery, this)
        );

        this.loadCurrentCollectedCommodityOfDelivery();
      })
    );

    console.log({ deliveryId });

    this.registerSubscription(
      this.$deliveryService
        .packageDelivery(deliveryId)
        .subscribe((persistentPackage) => {
          this.packageId = persistentPackage.packageId;

          console.log({ persistentPackage });

          this.search(PAGE.DEFAULT_PAGE_EVENT);
        })
    );

    this.registerSubscription(
      this.$warehouseService.findAll().subscribe((whList) => {
        this.warehouseList$.set(whList);
      })
    );

    this.registerSubscription(
      this.formGroup.controls.warehouseId.valueChanges.subscribe((nextWhId) => {
        const comId = this.delivery$()?.commodityId;

        if (nextWhId && comId) {
          this.registerSubscription(
            this.$warehouseItemService
              .getAvailableItemsInWarehouse(
                nextWhId,
                this.delivery$()?.commodityId || ""
              )
              .subscribe((items) => {
                this.warehouseItemList$.set(
                  items.map((it) =>
                    this.$warehouseItemService.convertToWarehouseItemFE(
                      it,
                      this
                    )
                  )
                );
              })
          );
        }
      }),

      this.formGroup.controls.warehouseItemId.valueChanges.subscribe(
        (itemId) => {
          if (itemId) {
            this.registerSubscription(
              this.$warehouseItemService
                .findById(itemId)
                .subscribe((itemEl) => {
                  this.formGroup.controls.quantity.setValidators([
                    Validators.required,
                    Validators.min(0.1),
                    Validators.max(itemEl.quantity - itemEl.usedQuantity),
                  ]);
                  this.formGroup.controls.quantity.updateValueAndValidity();
                })
            );
          }
        }
      )
    );
  }

  private loadCurrentCollectedCommodityOfDelivery() {
    const commodityId = this.delivery$()?.commodityId!;
    const deliveryId = this.delivery$()?.deliveryId!;

    this.registerSubscription(
      this.$deliveryService
        .getCurrentCollectedComQuantity(deliveryId, commodityId)
        .subscribe((collectedQuantityRaw) => {
          const collectedQuantity = Number(collectedQuantityRaw);

          if (Number.isNaN(collectedQuantity)) {
            throw new Error(
              "collectedQuantity is not valid: " + collectedQuantityRaw
            );
          }

          this.currentCollectedComQuantity$.set(collectedQuantity);
        })
    );
  }

  override search(pageEvt: PageEvent) {
    if (!this.packageId) {
      throw new Error("Missing packageId, thus cannot load items");
    }

    this.registerSubscription(
      this.$deliveryPackageItemService
        .search(
          {
            packageId: this.packageId,
          },
          pageEvt.pageIndex,
          pageEvt.pageSize
        )
        .subscribe((itemPage) => {
          this.handlePageDataUpdate(
            mapPage(itemPage, (it) =>
              this.$deliveryPackageItemService.convertToFEModel(it, this)
            ),
            this.paginator,
            this.deliveryPackageItemDatasource$
          );

          this.loadCurrentCollectedCommodityOfDelivery();
        })
    );
  }

  deletePackageItem(item: DeliveryPackageItemModel) {
    throw new Error("Method not implemented.");
  }

  addPackageItem() {
    this.formUtils.revalidateForm(this.formGroup);

    if (!this.formGroup.valid) {
      throw new Error("Form is not valid");
    }

    const delivery = this.delivery$()!;
    const warehouseId = this.formGroup.controls.warehouseId.value!;

    this.registerSubscription(
      this.$warehouseService
        .requestExportForDelivery(warehouseId, delivery.deliveryId!)
        .subscribe((exportEl) => {
          const exportId = exportEl.exportId;

          const entity: DeliveryPackageItemModel = {
            exportId,
            comId: delivery.commodityId,
            packageId: this.packageId,
            warehouseId,
            warehouseItemId: this.formGroup.controls.warehouseItemId.value!,
            quantity: Number(this.formGroup.controls.quantity.value),
          };

          console.log("prepare for package item: ", entity);

          this.registerSubscription(
            this.$deliveryPackageItemService.save(entity).subscribe((res) => {
              this.search(PAGE.DEFAULT_PAGE_EVENT);
            })
          );
        })
    );
  }

  goBack() {
    console.log("go back");

    this.$router.navigate([""], {
      relativeTo: this.$activatedRoute.parent,
    });
  }
}
