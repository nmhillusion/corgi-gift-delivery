import { WritableSignal } from "@angular/core";
import { IdType } from "../core/id.model";
import { Nullable } from "../core/nullable.model";
import { DeliveryAttemptFE } from "./delivery-attempt.model";
import { DeliveryReturnFE } from "./delivery-return.model";

export interface Delivery {
  deliveryId: IdType;
  eventId: IdType;
  deliveryPeriodYear: number;
  deliveryPeriodMonth: number;
  territory: string;
  region: string;
  organId: string;
  receivedOrgan: string;
  amdName: string;
  customerLevel: string;
  customerId: IdType;
  customerName: string;
  idCardNumber: string;
  phoneNumber: string;
  address: string;
  giftName: string;
  note: string;
}

export interface DeliveryFE extends Delivery {
  latestDeliveryAttempt$: WritableSignal<Nullable<DeliveryAttemptFE>>;
  latestDeliveryReturn$: WritableSignal<Nullable<DeliveryReturnFE>>;
}
