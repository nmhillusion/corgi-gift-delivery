import { IdType } from "../core/id.model";

export interface DeliveryPackageModel {
  packageId?: IdType;
  deliveryId?: IdType;
  packageName?: string;
  packageTime?: Date;
}
