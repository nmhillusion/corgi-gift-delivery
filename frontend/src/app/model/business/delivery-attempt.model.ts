import { WritableSignal } from "@angular/core";
import { IdType } from "../core/id.model";

export interface DeliveryAttempt {
  attemptId: number;
  deliveryId: number;
  deliveryTypeId: number;
  deliveryStatusId: number;
  note: string;
}

export interface DeliveryAttemptFE extends DeliveryAttempt {
  eventId: WritableSignal<IdType>;
  customerId: WritableSignal<IdType>;
  customerName: WritableSignal<string>;
  deliveryTypeName: WritableSignal<string>;
  deliveryStatusName: WritableSignal<string>;
}
