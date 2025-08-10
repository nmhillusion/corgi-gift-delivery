import { WritableSignal } from "@angular/core";
import { IdType } from "../core/id.model";
import { Delivery } from "./delivery.model";
import { Nullable } from "../core/nullable.model";

export interface DeliverAttempt {
  attemptId: number;
  deliveryId: number;
  deliveryTypeId: number;
  deliveryStatusId: number;
  note: string;
}

export interface DeliveryAttemptFE extends DeliverAttempt {
  delivery$: WritableSignal<Nullable<Delivery>>;
  deliveryTypeName$: WritableSignal<string>;
  deliveryStatusName$: WritableSignal<string>;
}
