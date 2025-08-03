import { HttpClient } from "@angular/common/http";
import { environment } from "@app/../environments/environment";
import { DeliveryType } from "@app/model/business/delivery-type.model";
import { IdType } from "@app/model/core/id.model";

export class DeliveryTypeService {
  constructor(private $http: HttpClient) {}

  buildUrl(partLink: number | string): string {
    return `${environment.LINK.API_BASE_URL}/api/delivery-type/${partLink}`;
  }

  getById(id: IdType) {
    return this.$http.get<DeliveryType>(this.buildUrl(id));
  }
}
