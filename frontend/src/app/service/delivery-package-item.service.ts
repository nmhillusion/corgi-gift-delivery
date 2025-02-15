import { Injectable, signal } from "@angular/core";
import { IdType } from "@app/model/core/id.model";
import { environment } from "../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { Page } from "@app/model/core/page.model";
import {
  DeliveryPackageItemFEModel,
  DeliveryPackageItemModel,
} from "@app/model/business/delivery-package-item.model";
import { BasePage } from "@app/pages/base.page";
import { WarehouseService } from "./warehouse.service";

@Injectable({
  providedIn: "root",
})
export class DeliveryPackageItemService {
  constructor(private $httpClient: HttpClient) {}

  private buildApiUrl(path_: string | IdType) {
    return `${environment.LINK.API_BASE_URL}/api/delivery-package/item/${path_}`;
  }

  search(dto: { packageId: IdType }, pageIndex: number, pageSize: number) {
    return this.$httpClient.post<Page<DeliveryPackageItemModel>>(
      this.buildApiUrl("search"),
      dto,
      {
        params: {
          pageIndex,
          pageSize,
        },
      }
    );
  }

  save(item: DeliveryPackageItemModel) {
    return this.$httpClient.post<DeliveryPackageItemModel>(
      this.buildApiUrl("save"),
      item
    );
  }

  findById(itemId: IdType) {
    return this.$httpClient.get<DeliveryPackageItemModel>(
      this.buildApiUrl(itemId)
    );
  }

  convertToFEModel(item: DeliveryPackageItemModel, basePage: BasePage) {
    const feModel = item as DeliveryPackageItemFEModel;

    feModel.warehouse$ = signal(null);

    basePage.registerSubscription(
      basePage.$injector
        .get(WarehouseService)
        .findById(item.warehouseId!)
        .subscribe((wh) => {
          feModel.warehouse$.set(wh);
        })
    );

    return feModel;
  }
}
