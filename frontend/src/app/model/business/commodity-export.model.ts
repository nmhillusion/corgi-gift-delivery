import { IdType } from "../core/id.model";

export interface CommodityExportEntity {
  exportId: IdType;
  exportName: string;
  exportTime: Date;
  warehouseId: IdType;
}
