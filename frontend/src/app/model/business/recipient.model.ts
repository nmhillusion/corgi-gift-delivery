import { WritableSignal } from "@angular/core";
import { RecipientTypeModel } from "./recipient-type.model";
import { Nullable } from "../core/nullable.model";
import { IdType } from "../core/id.model";

export interface RecipientModel {
  recipientId?: IdType;
  fullName?: string;
  idCardNumber?: string;
  recipientTypeId?: IdType;
}

export interface RecipientFEModel extends RecipientModel {
  recipientType$: WritableSignal<Nullable<RecipientTypeModel>>;
}
