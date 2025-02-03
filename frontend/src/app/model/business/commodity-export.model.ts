import { IdType } from "../core/id.model";

export interface CommodityExportModel {
  exportId: IdType;
  exportName: string;
  exportTime: Date;
  warehouseId: IdType;
}
