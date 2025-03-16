import { WritableSignal } from "@angular/core";
import { IdType } from "../core/id.model";
import { Nullable } from "../core/nullable.model";
import { CommodityTypeModel } from "./commodity-type.model";

export interface CommodityModel {
  comId?: IdType;
  comName?: string;
  comTypeId?: IdType;
  createTime?: Date;
}

export interface CommodityFEModel extends CommodityModel {
  comType$: WritableSignal<Nullable<CommodityTypeModel>>;
}
