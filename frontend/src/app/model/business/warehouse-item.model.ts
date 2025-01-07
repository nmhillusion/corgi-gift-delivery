import { WritableSignal } from "@angular/core";
import { CommodityImportModel } from "./commodity-import.model";
import { CommodityModel } from "./commodity.model";
import { Nullable } from "../core/nullable.model";

export interface WarehouseItemModel {
  itemId?: string;
  importId?: number;
  warehouseId?: number;
  comId?: number;
  quantity?: number;
  createTime?: Date;
}

export interface WarehouseItemFEModel extends WarehouseItemModel {
  commodityImport$?: WritableSignal<Nullable<CommodityImportModel>>;
  commodity$?: WritableSignal<Nullable<CommodityModel>>;
}
