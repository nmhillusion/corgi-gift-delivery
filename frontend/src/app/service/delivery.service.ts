import { HttpClient } from "@angular/common/http";
import { Injectable, signal } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { DeliveryFEModel, DeliveryModel } from "@app/model/business/delivery.model";
import { Page } from "@app/model/core/page.model";
import { RecipientService } from "./recipient.service";
import { BasePage } from "@app/pages/base.page";
import { CommodityService } from "@app/pages/commodity/commodity-mgmt/commodity.service";
import { DeliveryStatusService } from "./delivery-status.service";

@Injectable({providedIn: 'root'})
export class DeliveryService {

  constructor(private $http: HttpClient) {}

  private buildApiPath(path: string) {
    return `${environment.LINK.API_BASE_URL}/api/delivery/${path}`;
  }

  search(dto: { name?: string }, pageIndex: number, pageSize: number) {
    return this.$http.post<Page<DeliveryModel>>(  
      this.buildApiPath(`search`),
      dto,
      {
        params: {
          pageIndex,
          pageSize,
        }
      }
    );
  }

  convertToFEModel(delivery: DeliveryModel, basePage: BasePage) {
    const deliveryFE = delivery as DeliveryFEModel;

    deliveryFE.recipient$ = signal(null);
    deliveryFE.commodity$ = signal(null);
    deliveryFE.deliveryStatus$ = signal(null);
    deliveryFE.currentAttempt$ = signal(null);

    basePage.registerSubscription(
      basePage.$injector.get(RecipientService).findById(delivery.recipientId || 0).subscribe((recipient) => {
        deliveryFE.recipient$?.set(recipient);
      }),
      basePage.$injector.get(CommodityService).findById(delivery.commodityId || 0).subscribe((commodity) => {
        deliveryFE.commodity$?.set(commodity);
      }),
      basePage.$injector.get(DeliveryStatusService).findById(delivery.deliveryStatusId || 0).subscribe((deliveryStatus) => {
        deliveryFE.deliveryStatus$?.set(deliveryStatus);
      })
    );

    return deliveryFE;
  }

  findById(deliveryId: number) {
    return this.$http.get<DeliveryModel>(this.buildApiPath(`${deliveryId}`));
  }

  save(dto: DeliveryModel) {
    return this.$http.post<DeliveryModel>(this.buildApiPath('save'), dto);
  }
}