import { WritableSignal } from "@angular/core";
import { CommodityImportModel } from "./commodity-import.model";
import { CommodityModel } from "./commodity.model";
import { Nullable } from "../core/nullable.model";
import { IdType } from "../core/id.model";
import { WarehouseModel } from "./warehouse.model";

export interface WarehouseItemModel {
  itemId?: IdType;
  importId?: IdType;
  warehouseId?: IdType;
  comId?: IdType;
  quantity: number;
  usedQuantity: number;
  createTime?: Date;
  updateTime?: Date;
}

export interface WarehouseItemFEModel extends WarehouseItemModel {
  commodityImport$: WritableSignal<Nullable<CommodityImportModel>>;
  commodity$: WritableSignal<Nullable<CommodityModel>>;
  warehouse$: WritableSignal<Nullable<WarehouseModel>>;
}
