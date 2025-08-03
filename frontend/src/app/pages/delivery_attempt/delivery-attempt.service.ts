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
    return this.$http.post<Page<DeliverAttempt>>(
      this.buildUrl("search"),
      dto,
      {
        params: {
          pageIndex,
          pageSize,
        },
      }
    );
  }

  convertToFE(deliveryAttempt: DeliverAttempt): DeliveryAttemptFE {
    const feItem = deliveryAttempt as DeliveryAttemptFE;
    feItem.eventId = signal(-1);
    feItem.customerId = signal(-1);
    feItem.customerName = signal("");
    feItem.deliveryTypeName = signal("");
    feItem.deliveryStatusName = signal("");

    (function () {
      const deliveryService = inject(DeliveryService);
      const deliveryTypeService = inject(DeliveryTypeService);
      const deliveryStatusService = inject(DeliveryStatusService);

      deliveryService
        .getById(deliveryAttempt.deliveryId)
        .subscribe((delivery: Delivery) => {
          feItem.eventId.set(delivery.eventId);
          feItem.customerId.set(delivery.customerId);
          deliveryService
            .getCustomerNameOfDelivery(
              deliveryAttempt.deliveryId,
              delivery.customerId
            )
            .subscribe((name) => {
              feItem.customerName.set(name);
            });
        });

      deliveryTypeService
        .getById(deliveryAttempt.deliveryTypeId)
        .subscribe((type) => {
          feItem.deliveryTypeName.set(type.typeName);
        });

      deliveryStatusService
        .getById(deliveryAttempt.deliveryStatusId)
        .subscribe((status) => {
          feItem.deliveryStatusName.set(status.statusName);
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
}
