import { ShipperTypeModel } from "./shipper-type.model";

export interface ShipperModel {
  shipperId?: number;
  shipperTypeId?: string;
  shipperCode?: string;
  shipperName?: string;
}

export interface ShipperFEModel extends ShipperModel {
  shipperType?: ShipperTypeModel;
}
