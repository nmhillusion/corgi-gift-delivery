import { WritableSignal } from "@angular/core";
import { IdType } from "../core/id.model";

export interface DeliverAttempt {
  attemptId: number;
  deliveryId: number;
  deliveryTypeId: number;
  deliveryStatusId: number;
  note: string;
}

export interface DeliveryAttemptFE extends DeliverAttempt {
  eventId: WritableSignal<IdType>;
  customerId: WritableSignal<IdType>;
  customerName: WritableSignal<string>;
  deliveryTypeName: WritableSignal<string>;
  deliveryStatusName: WritableSignal<string>;
}
