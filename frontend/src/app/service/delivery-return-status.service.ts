import { HttpClient } from "@angular/common/http";
import { environment } from "@app/../environments/environment";
import { DeliveryReturnStatus } from "@app/model/business/delivery-return-status.model";
import { IdType } from "@app/model/core/id.model";

export class DeliveryReturnStatusService {
  constructor(private $http: HttpClient) {}

  buildUrl(partLink: number | string): string {
    return `${environment.LINK.API_BASE_URL}/api/delivery-return-status/${partLink}`;
  }

  getById(id: IdType) {
    return this.$http.get<DeliveryReturnStatus>(this.buildUrl(id));
  }
}
