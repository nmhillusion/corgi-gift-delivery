import { IdType } from "../core/id.model";

export interface WarehouseExportItemModel {
  itemId: IdType;
  exportId: string;
  warehouseId: IdType;
  comId: IdType;
  quantity: number;
  createTime: Date;
}
