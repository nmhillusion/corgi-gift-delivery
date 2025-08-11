import { Nullable } from "@app/model/core/nullable.model";

export interface CoreDeliverySearchDto {
  eventId?: Nullable<string>;
  customerId?: Nullable<string>;
}