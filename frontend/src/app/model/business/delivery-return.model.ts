import { WritableSignal } from "@angular/core";
import { IdType } from "../core/id.model";
import { Nullable } from "../core/nullable.model";

export interface DeliveryReturn {
  returnId: IdType;
  deliveryId: IdType;
  returnStatusId: IdType;
  note: string;
}

export interface DeliveryReturnFE extends DeliveryReturn {
  eventId$: WritableSignal<IdType>;
  customerId$: WritableSignal<IdType>;
  customerName$: WritableSignal<Nullable<string>>;
  returnStatusName$: WritableSignal<Nullable<string>>;
}
