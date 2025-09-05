import { HttpClient } from "@angular/common/http";
import { inject, Injectable, signal } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { DeliverAttempt } from "@app/model/business/delivery-attempt.model";
import {
  DeliveryReturn,
  DeliveryReturnFE,
} from "@app/model/business/delivery-return.model";
import { Delivery } from "@app/model/business/delivery.model";
import { CoreDeliverySearchDto } from "@app/model/business/dto/core-delivery.search.dto.model";
import { IdType } from "@app/model/core/id.model";
import { Page } from "@app/model/core/page.model";
import { BasePage } from "@app/pages/base.page";
import { DeliveryReturnStatusService } from "@app/service/delivery-return-status.service";
import { DeliveryService } from "../delivery/delivery.service";

export interface DeliveryReturnSearchDto extends CoreDeliverySearchDto {}

@Injectable({
  providedIn: "root",
})
export class DeliveryReturnService {
  private $http = inject(HttpClient);

  buildUrl(partLink: number | string): string {
    return `${environment.LINK.API_BASE_URL}/api/delivery-return/${partLink}`;
  }

  search(dto: DeliveryReturnSearchDto, pageIndex: number, pageSize: number) {
    return this.$http.post<Page<DeliveryReturn>>(this.buildUrl("search"), dto, {
      params: {
        pageIndex,
        pageSize,
      },
    });
  }

  convertToFE(deliveryReturn: DeliveryReturn, basePage: BasePage) {
    const feItem = deliveryReturn as DeliveryReturnFE;
    feItem.delivery$ = signal(null);
    feItem.returnStatusName$ = signal(null);

    (function () {
      const deliveryService = basePage.$injector.get(DeliveryService);
      const deliveryReturnStatusService = basePage.$injector.get(
        DeliveryReturnStatusService
      );

      deliveryService
        .getById(deliveryReturn.deliveryId)
        .subscribe((delivery: Delivery) => {
          feItem.delivery$.set(delivery);
        });

      deliveryReturnStatusService
        .getById(deliveryReturn.returnStatusId)
        .subscribe((status) => {
          feItem.returnStatusName$.set(status.statusName);
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

  getLatestDeliveryReturnByDeliveryId(deliveryId: IdType) {
    return this.$http.get<DeliveryReturnFE>(
      this.buildUrl(`${deliveryId}/latest-return`)
    );
  }

  export(dto: DeliveryReturnSearchDto) {
    return this.$http.post(this.buildUrl("export"), dto, {
      responseType: "blob",
    });
  }
}
