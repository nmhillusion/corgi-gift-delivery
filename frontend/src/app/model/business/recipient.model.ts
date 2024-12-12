import { WritableSignal } from "@angular/core";
import { RecipientTypeModel } from "./recipient-type.model";
import { Nullable } from "../core/nullable.model";

export interface RecipientModel {
  customerId?: number;
  fullName?: string;
  idCardNumber?: string;
  recipientTypeId?: number;
}

export interface RecipientFEModel extends RecipientModel {
  recipientType$: WritableSignal<Nullable<RecipientTypeModel>>;
}
