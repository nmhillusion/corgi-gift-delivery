import { WritableSignal } from "@angular/core";
import { IdType } from "../core/id.model";
import { ShipperTypeModel } from "./shipper-type.model";
import { Nullable } from "../core/nullable.model";

export interface ShipperModel {
  shipperId?: IdType;
  shipperTypeId?: IdType;
  shipperCode?: string;
  shipperName?: string;
}

export interface ShipperFEModel extends ShipperModel {
  shipperType$: WritableSignal<Nullable<ShipperTypeModel>>;
}