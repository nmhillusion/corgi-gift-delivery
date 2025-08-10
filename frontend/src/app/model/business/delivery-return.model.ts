import { WritableSignal } from "@angular/core";
import { IdType } from "../core/id.model";
import { Nullable } from "../core/nullable.model";
import { Delivery } from "./delivery.model";

export interface DeliveryReturn {
  returnId: IdType;
  deliveryId: IdType;
  returnStatusId: IdType;
  note: string;
}

export interface DeliveryReturnFE extends DeliveryReturn {
  delivery$: WritableSignal<Nullable<Delivery>>;
  returnStatusName$: WritableSignal<Nullable<string>>;
}
