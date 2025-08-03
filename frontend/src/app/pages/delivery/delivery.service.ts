import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { Page } from "@app/model/core/page.model";
import { Delivery } from "@app/model/business/delivery.model";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class DeliveryService {
  private $http = inject(HttpClient);

  buildUrl(partLink: number | string): string {
    return `${environment.LINK.API_BASE_URL}/api/delivery/${partLink}`;
  }

  search(
    dto: {},
    pageIndex: number,
    pageSize: number
  ): Observable<Page<Delivery>> {
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
}
