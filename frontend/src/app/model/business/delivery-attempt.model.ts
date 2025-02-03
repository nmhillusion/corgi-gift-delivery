import { IdType } from "@app/model/core/id.model";


export interface DeliveryAttemptModel {
  attemptId?: IdType;
  deliveryId?: IdType;
  deliveryTypeId?: IdType;
  shipperId?: IdType;
  startTime?: Date;
  endTime?: Date;
  deliveryStatusId?: IdType;
}