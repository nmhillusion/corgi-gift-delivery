import { WritableSignal } from "@angular/core";
import { CustomerTypeModel } from "./customer-type.model";
import { Nullable } from "../core/nullable.model";

export interface CustomerModel {
  customerId?: number;
  fullName?: string;
  idCardNumber?: string;
  customerTypeId?: number;
}

export interface CustomerFEModel extends CustomerModel {
  customerType$: WritableSignal<Nullable<CustomerTypeModel>>;
}
