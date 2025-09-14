import { IdType } from "@app/model/core/id.model";
import { Nullable } from "@app/model/core/nullable.model";

export interface CoreDeliverySearchDto {
  eventId?: Nullable<IdType>;
  customerId?: Nullable<IdType>;
}