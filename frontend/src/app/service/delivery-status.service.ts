import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { DeliveryStatus } from "@app/model/business/delivery-status.model";
import { IdType } from "@app/model/core/id.model";

@Injectable({
  providedIn: "root",
})
export class DeliveryStatusService {
  constructor(private $http: HttpClient) {}

  buildUrl(partLink: number | string): string {
    return `${environment.LINK.API_BASE_URL}/api/delivery-status/${partLink}`;
  }

  getById(id: IdType) {
    return this.$http.get<DeliveryStatus>(this.buildUrl(id));
  }

  getAll() {
    return this.$http.get<DeliveryStatus[]>(this.buildUrl("list"));
  }
}
