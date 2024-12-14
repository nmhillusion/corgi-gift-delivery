import { WritableSignal } from "@angular/core"
import { Nullable } from "../core/nullable.model"
import { WarehouseModel } from "./warehouse.model"

export interface CommodityImportModel {
  importId?: number
  importName?: string
  importTime?: Date
  warehouseId?: number
}

export interface CommodityImportFEModel extends CommodityImportModel {
  warehouse$?: WritableSignal<Nullable<WarehouseModel>>
}