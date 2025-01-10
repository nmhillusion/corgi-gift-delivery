import { WritableSignal } from "@angular/core"
import { Nullable } from "../core/nullable.model"
import { WarehouseModel } from "./warehouse.model"
import { IdType } from "../core/id.model"

export interface CommodityImportModel {
  importId?: IdType
  importName?: string
  importTime?: Date
  warehouseId?: IdType
}

export interface CommodityImportFEModel extends CommodityImportModel {
  warehouse$?: WritableSignal<Nullable<WarehouseModel>>
}