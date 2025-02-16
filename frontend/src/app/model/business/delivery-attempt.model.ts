import { WritableSignal } from "@angular/core";
import { IdType } from "@app/model/core/id.model";
import { Nullable } from "../core/nullable.model";
import { DeliveryStatusModel } from "./delivery-status.model";
import { DeliveryTypeModel } from "./delivery-type.model";
import { ShipperFEModel } from "./shipper.model";

export interface DeliveryAttemptModel {
  attemptId?: IdType;
  deliveryId?: IdType;
  deliveryTypeId?: IdType;
  shipperId?: IdType;
  startTime?: Date;
  endTime?: Date;
  deliveryStatusId?: IdType;
}

export interface DeliveryAttemptFEModel extends DeliveryAttemptModel {
  deliveryType$: WritableSignal<Nullable<DeliveryTypeModel>>;
  shipper$: WritableSignal<Nullable<ShipperFEModel>>;
  deliveryStatus$: WritableSignal<Nullable<DeliveryStatusModel>>;
  ableToProcess$: WritableSignal<boolean>;
}
