import { WritableSignal } from "@angular/core";
import { CommodityModel } from "@app/model/business/commodity.model";
import { RecipientModel } from "@app/model/business/recipient.model";
import { IdType } from "@app/model/core/id.model";
import { Nullable } from "@app/model/core/nullable.model";
import { DeliveryAttemptModel } from "./delivery-attempt.model";
import { DeliveryStatusModel } from "./delivery-status.model";

export interface DeliveryModel {
  deliveryId?: IdType;
  recipientId?: IdType;
  commodityId?: IdType;
  comQuantity?: number;
  startTime?: Date;
  endTime?: Date;
  currentAttemptId?: IdType;
  deliveryStatusId?: IdType;
}

export interface DeliveryFEModel extends DeliveryModel {
  recipient$: WritableSignal<Nullable<RecipientModel>>;
  commodity$: WritableSignal<Nullable<CommodityModel>>;
  currentAttempt$: WritableSignal<Nullable<DeliveryAttemptModel>>;
  deliveryStatus$: WritableSignal<Nullable<DeliveryStatusModel>>;
}


