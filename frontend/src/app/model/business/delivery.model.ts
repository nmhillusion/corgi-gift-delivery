import { IdType } from "../core/id.model";

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