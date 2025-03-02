import { WritableSignal } from "@angular/core";
import { IdType } from "../core/id.model";
import { Nullable } from "../core/nullable.model";
import { WarehouseModel } from "./warehouse.model";

export interface DeliveryPackageItemModel {
  itemId?: IdType;
  packageId?: IdType;
  warehouseId?: IdType;
  warehouseItemId?: IdType;
  exportId?: IdType;
  comId?: IdType;
  quantity?: IdType;
  createTime?: Date;
}

export interface DeliveryPackageItemFEModel extends DeliveryPackageItemModel {
  warehouse$: WritableSignal<Nullable<WarehouseModel>>;
}
