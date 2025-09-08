import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { DeliveryType } from "@app/model/business/delivery-type.model";
import { IdType } from "@app/model/core/id.model";

@Injectable({
  providedIn: "root",
})
export class DeliveryTypeService {
  constructor(private $http: HttpClient) {}

  buildUrl(partLink: number | string): string {
    return `${environment.LINK.API_BASE_URL}/api/delivery-type/${partLink}`;
  }

  getById(id: IdType) {
    return this.$http.get<DeliveryType>(this.buildUrl(id));
  }

  getAll() {
    return this.$http.get<DeliveryType[]>(this.buildUrl("list"));
  }
}
