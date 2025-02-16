import { HttpClient } from "@angular/common/http";
import { Injectable, Injector, signal } from "@angular/core";
import { environment } from "@app/../environments/environment";
import {
  WarehouseItemFEModel,
  WarehouseItemModel,
} from "@app/model/business/warehouse-item.model";
import { Page } from "@app/model/core/page.model";
import { CommodityService } from "@app/pages/commodity/commodity-mgmt/commodity.service";
import { CommodityImportService } from "./commodity-import.service";
import { BasePage } from "@app/pages/base.page";
import { IdType } from "@app/model/core/id.model";
import { WarehouseService } from "./warehouse.service";

@Injectable({ providedIn: "root" })
export class WarehouseItemService {
  constructor(private $http: HttpClient) {}

  buildApiUrl(path: string): string {
    return `${environment.LINK.API_BASE_URL}/api/warehouse-item${path}`;
  }

  findById(itemId: number) {
    return this.$http.get<WarehouseItemModel>(this.buildApiUrl(`/${itemId}`));
  }

  searchItemsInWarehouse(
    warehouseId: number | string,
    pageIndex: number,
    pageSize: number,
    searchDto: {
      name: string;
    }
  ) {
    return this.$http.post<Page<WarehouseItemModel>>(
      this.buildApiUrl(`/search-in-warehouse/${warehouseId}`),
      searchDto,
      {
        params: {
          pageIndex,
          pageSize,
        },
      }
    );
  }

  convertToWarehouseItemFE(
    warehouseItem: WarehouseItemModel,
    basePage: BasePage
  ) {
    const warehouseItemFE: WarehouseItemFEModel = {
      ...warehouseItem,
      commodity$: signal(null),
      commodityImport$: signal(null),
      warehouse$: signal(null),
    };

    (function (
      commodityService: CommodityService,
      commodityImportService: CommodityImportService,
      warehouseService: WarehouseService
    ) {
      if (warehouseItem.comId) {
        basePage.registerSubscription(
          commodityService
            .findById(warehouseItem.comId)
            .subscribe((commodity) => {
              if (commodity) {
                warehouseItemFE.commodity$?.set(commodity);
              }
            })
        );
      }

      if (warehouseItem.importId) {
        basePage.registerSubscription(
          commodityImportService
            .findById(warehouseItem.importId)
            .subscribe((importModel) => {
              if (importModel) {
                warehouseItemFE.commodityImport$?.set(importModel);
              }
            })
        );
      }

      if (warehouseItem.warehouseId) {
        basePage.registerSubscription(
          warehouseService
            .findById(warehouseItem.warehouseId)
            .subscribe((warehouse) => {
              if (warehouse) {
                warehouseItemFE.warehouse$?.set(warehouse);
              }
            })
        );
      }
    })(
      basePage.$injector.get(CommodityService),
      basePage.$injector.get(CommodityImportService),
      basePage.$injector.get(WarehouseService)
    );

    return warehouseItemFE;
  }

  searchItemsInImport(
    importId: number | string,
    pageIndex: number,
    pageSize: number,
    searchDto: {
      name: string;
    }
  ) {
    return this.$http.post<Page<WarehouseItemModel>>(
      this.buildApiUrl(`/search-in-import/${importId}`),
      searchDto,
      {
        params: {
          pageIndex,
          pageSize,
        },
      }
    );
  }

  sync(warehouseItem: WarehouseItemModel) {
    return this.$http.post<WarehouseItemModel>(
      this.buildApiUrl("/sync"),
      warehouseItem
    );
  }
}
