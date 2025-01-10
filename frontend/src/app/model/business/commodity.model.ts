import { IdType } from "../core/id.model";

export interface CommodityModel {
  comId?: IdType;
  comName?: string;
  comTypeId?: IdType;
  createTime?: Date;
}
