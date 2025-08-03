import { HttpClient } from "@angular/common/http";
import { environment } from "@app/../environments/environment";
import { DeliveryStatus } from "@app/model/business/delivery-status.model";
import { IdType } from "@app/model/core/id.model";

export class DeliveryStatusService {
  constructor(private $http: HttpClient) {}

  buildUrl(partLink: number | string): string {
    return `${environment.LINK.API_BASE_URL}/api/delivery-status/${partLink}`;
  }

  getById(id: IdType) {
    return this.$http.get<DeliveryStatus>(this.buildUrl(id));
  }
}
