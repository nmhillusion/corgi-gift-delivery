import { HttpClient } from "@angular/common/http";
import { inject, Injectable, signal } from "@angular/core";
import { environment } from "@app/../environments/environment";
import {
  DeliverAttempt,
  DeliveryAttemptFE,
} from "@app/model/business/delivery-attempt.model";
import { Delivery } from "@app/model/business/delivery.model";
import { Page } from "@app/model/core/page.model";
import { DeliveryStatusService } from "@app/service/delivery-status.service";
import { DeliveryTypeService } from "@app/service/delivery-type.service";
import { Observable } from "rxjs";
import { DeliveryService } from "../delivery/delivery.service";
import { BasePage } from "@app/pages/base.page";
import { IdType } from "@app/model/core/id.model";

@Injectable({
  providedIn: "root",
})
export class DeliveryAttemptService {
  private $http = inject(HttpClient);

  buildUrl(partLink: number | string): string {
    return `${environment.LINK.API_BASE_URL}/api/delivery-attempt/${partLink}`;
  }

  search(
    dto: {},
    pageIndex: number,
    pageSize: number
  ): Observable<Page<DeliverAttempt>> {
    return this.$http.post<Page<DeliverAttempt>>(this.buildUrl("search"), dto, {
      params: {
        pageIndex,
        pageSize,
      },
    });
  }

  convertToFE(
    deliveryAttempt: DeliverAttempt,
    basePage: BasePage
  ): DeliveryAttemptFE {
    const feItem = deliveryAttempt as DeliveryAttemptFE;
    feItem.delivery$ = signal(null);
    feItem.deliveryTypeName$ = signal("");
    feItem.deliveryStatusName$ = signal("");

    (function () {
      const deliveryService = basePage.$injector.get(DeliveryService);
      const deliveryTypeService = basePage.$injector.get(DeliveryTypeService);
      const deliveryStatusService = basePage.$injector.get(
        DeliveryStatusService
      );

      deliveryService
        .getById(deliveryAttempt.deliveryId)
        .subscribe((delivery: Delivery) => {
          feItem.delivery$.set(delivery);
        });

      deliveryTypeService
        .getById(deliveryAttempt.deliveryTypeId)
        .subscribe((type) => {
          feItem.deliveryTypeName$.set(type.typeName);
        });

      deliveryStatusService
        .getById(deliveryAttempt.deliveryStatusId)
        .subscribe((status) => {
          feItem.deliveryStatusName$.set(status.statusName);
        });
    })();

    return feItem;
  }

  insertBatchByExcelFile(excelFile: File) {
    const formData = new FormData();
    formData.append("excelFile", excelFile);

    return this.$http.post<DeliverAttempt[]>(
      this.buildUrl("insert/batch"),
      formData,
      {
        headers: {
          enctype: "multipart/form-data",
        },
      }
    );
  }

  updateBatchByExcelFile(excelFile: File) {
    const formData = new FormData();
    formData.append("excelFile", excelFile);

    return this.$http.post<DeliverAttempt[]>(
      this.buildUrl("update/batch"),
      formData,
      {
        headers: {
          enctype: "multipart/form-data",
        },
      }
    );
  }

  getLatestDeliveryAttemptByDeliveryId(deliveryId: IdType) {
    return this.$http.get<DeliveryAttemptFE>(
      this.buildUrl(`${deliveryId}/latest-attempt`)
    );
  }
}
