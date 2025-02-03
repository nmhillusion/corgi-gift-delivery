import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { DeliveryTypeModel } from "@app/model/business/delivery-type.model";
import { IdType } from "@app/model/core/id.model";

@Injectable({ providedIn: "root" })
export class DeliveryTypeService {
  constructor(private $http: HttpClient) {}

  private buildApiUrl(path: string | IdType) {
    return `${environment.LINK.API_BASE_URL}/delivery-type/${path}`;
  }

  findAll() {
    return this.$http.get<DeliveryTypeModel[]>(this.buildApiUrl("find-all"));
  }

  findById(deliveryTypeId: IdType) {
    return this.$http.get<DeliveryTypeModel>(this.buildApiUrl(deliveryTypeId));
  }
}
