import { HttpClient } from "@angular/common/http";
import { Injectable, inject, signal } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { Delivery, DeliveryFE } from "@app/model/business/delivery.model";
import { CoreDeliverySearchDto } from "@app/model/business/dto/core-delivery.search.dto.model";
import { IdType } from "@app/model/core/id.model";
import { Page } from "@app/model/core/page.model";
import { Observable } from "rxjs";
import { BasePage } from "../base.page";
import { DeliveryAttemptService } from "../delivery_attempt/delivery-attempt.service";
import { DeliveryReturnService } from "../delivery_return/delivery-return.service";
import { Nullable } from "@app/model/core/nullable.model";

export interface DeliverySearchDto extends CoreDeliverySearchDto {
  deliveryStatusId: Nullable<IdType>;
  deliveryTypeId: Nullable<IdType>;
  returnStatusId: Nullable<IdType>;
}

@Injectable({
  providedIn: "root",
})
export class DeliveryService {
  private $http = inject(HttpClient);

  buildUrl(partLink: number | string): string {
    return `${environment.LINK.API_BASE_URL}/api/delivery/${partLink}`;
  }

  search(dto: DeliverySearchDto, pageIndex: number, pageSize: number) {
    console.log("Searching deliveries with DTO: ", dto);

    return this.$http.post<Page<Delivery>>(this.buildUrl("search"), dto, {
      params: {
        pageIndex,
        pageSize,
      },
    });
  }

  insertBatchByExcelFile(excelFile: File): Observable<Delivery[]> {
    const formData = new FormData();
    formData.append("excelFile", excelFile);

    return this.$http.post<Delivery[]>(
      this.buildUrl("insert/batch"),
      formData,
      {
        headers: {
          enctype: "multipart/form-data",
        },
      }
    );
  }

  updateBatchByExcelFile(excelFile: File): Observable<Delivery[]> {
    const formData = new FormData();
    formData.append("excelFile", excelFile);

    return this.$http.post<Delivery[]>(
      this.buildUrl("update/batch"),
      formData,
      {
        headers: {
          enctype: "multipart/form-data",
        },
      }
    );
  }

  getById(deliveryId: IdType): Observable<Delivery> {
    return this.$http.get<Delivery>(this.buildUrl(deliveryId));
  }

  getCustomerNameOfDelivery(
    deliveryId: IdType,
    customerId: IdType
  ): Observable<string> {
    return this.$http.get<string>(
      `${this.buildUrl(deliveryId)}/customer/${customerId}/name`,
      {
        responseType: "text" as "json",
      }
    );
  }

  convertToFE(delivery: Delivery, basePage: BasePage): DeliveryFE {
    const feItem = delivery as DeliveryFE;
    feItem.latestDeliveryAttempt$ = signal(null);
    feItem.latestDeliveryReturn$ = signal(null);

    ((_) => {
      const deliveryAttemptService = basePage.$injector.get(
        DeliveryAttemptService
      );
      const deliveryReturnService = basePage.$injector.get(
        DeliveryReturnService
      );

      deliveryAttemptService
        .getLatestDeliveryAttemptByDeliveryId(delivery.deliveryId)
        .subscribe((attempt) => {
          feItem.latestDeliveryAttempt$.set(
            deliveryAttemptService.convertToFE(attempt, basePage)
          );
        });

      deliveryReturnService
        .getLatestDeliveryReturnByDeliveryId(delivery.deliveryId)
        .subscribe((returnItem) => {
          feItem.latestDeliveryReturn$.set(
            deliveryReturnService.convertToFE(returnItem, basePage)
          );
        });
    })();

    return feItem;
  }

  exportSummaryDeliveries(dto: DeliverySearchDto) {
    return this.$http.post(this.buildUrl("export/summary"), dto, {
      responseType: "blob",
    });
  }

  export(dto: DeliverySearchDto) {
    return this.$http.post(this.buildUrl("export"), dto, {
      responseType: "blob",
    });
  }
}
