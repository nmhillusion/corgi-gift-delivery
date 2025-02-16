import { HttpClient } from "@angular/common/http";
import { Injectable, signal } from "@angular/core";
import { environment } from "@app/../environments/environment";
import {
  DeliveryAttemptFEModel,
  DeliveryAttemptModel,
} from "@app/model/business/delivery-attempt.model";
import { IdType } from "@app/model/core/id.model";
import { Page } from "@app/model/core/page.model";
import { BasePage } from "@app/pages/base.page";
import { DeliveryTypeService } from "./delivery-type.service";
import { ShipperService } from "./shipper.service";
import { DeliveryStatusService } from "./delivery-status.service";

@Injectable({ providedIn: "root" })
export class DeliveryAttemptService {
  constructor(private $http: HttpClient) {}

  private buildApiUrl(path: string) {
    return `${environment.LINK.API_BASE_URL}/api/delivery-attempt/${path}`;
  }

  search(
    deliveryId: IdType,
    dto: { name: string },
    pageIndex: number,
    pageSize: number
  ) {
    return this.$http.post<Page<DeliveryAttemptModel>>(
      this.buildApiUrl(`${deliveryId}/search`),
      dto,
      { params: { pageIndex, pageSize } }
    );
  }

  findById(deliveryAttemptId: IdType) {
    return this.$http.get<DeliveryAttemptModel>(
      this.buildApiUrl(`${deliveryAttemptId}`)
    );
  }

  save(deliveryAttemptModel: DeliveryAttemptModel) {
    return this.$http.post<DeliveryAttemptModel>(
      this.buildApiUrl("save"),
      deliveryAttemptModel
    );
  }

  deleteById(deliveryAttemptId: IdType) {
    return this.$http.delete<DeliveryAttemptModel>(
      this.buildApiUrl(`${deliveryAttemptId}`)
    );
  }

  convertToFEModel(
    deliveryAttemptModel: DeliveryAttemptModel,
    basePage: BasePage
  ) {
    const deliveryAttemptFE = deliveryAttemptModel as DeliveryAttemptFEModel;

    deliveryAttemptFE.deliveryType$ = signal(null);
    deliveryAttemptFE.shipper$ = signal(null);
    deliveryAttemptFE.deliveryStatus$ = signal(null);
    const shipperService = basePage.$injector.get(ShipperService);

    basePage.registerSubscription(
      basePage.$injector
        .get(DeliveryTypeService)
        .findById(deliveryAttemptModel.deliveryTypeId || 0)
        .subscribe((deliveryType) => {
          deliveryAttemptFE.deliveryType$.set(deliveryType);
        }),
      shipperService
        .findById(deliveryAttemptModel.shipperId || 0)
        .subscribe((shipper) => {
          deliveryAttemptFE.shipper$.set(
            shipperService.convertToFEModel(shipper, basePage)
          );
        }),
      basePage.$injector
        .get(DeliveryStatusService)
        .findById(deliveryAttemptModel.deliveryStatusId || 0)
        .subscribe((deliveryStatus) => {
          deliveryAttemptFE.deliveryStatus$.set(deliveryStatus);
        })
    );

    return deliveryAttemptFE;
  }

  process(
    attemptId: IdType,
    processDto: {
      deliveryStatusId: IdType;
      actionTime: Date;
    }
  ) {
    return this.$http.post<DeliveryAttemptModel>(
      this.buildApiUrl(`${attemptId}/process`),
      processDto
    );
  }
}
