import { WritableSignal } from "@angular/core";
import { IdType } from "../core/id.model";

export interface DeliveryReturn {
  returnId: IdType;
  deliveryId: IdType;
  attemptId: IdType;
  returnStatusId: IdType;
  note: string;
}

export interface DeliveryReturnFE extends DeliveryReturn {
  eventId$: WritableSignal<IdType>;
  customerId$: WritableSignal<IdType>;
  customerName$: WritableSignal<string>;
  returnStatusName$: WritableSignal<string>;
}
