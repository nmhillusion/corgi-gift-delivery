import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { DeliveryStatusModel } from "@app/model/business/delivery-status.model";
import { IdType } from "@app/model/core/id.model";

@Injectable({
  providedIn: "root",
})
export class DeliveryStatusService {
  constructor(private http: HttpClient) {}

  private buildApiUrl(path: string): string {
    return `${environment.LINK.API_BASE_URL}/api/v1/delivery-status/${path}`;
  }

  getDeliveryStatusList() {
    return this.http.get<DeliveryStatusModel[]>(this.buildApiUrl("find-all"));
  }

  findById(deliveryStatusId: IdType) {
    return this.http.get<DeliveryStatusModel>(
      this.buildApiUrl(`${deliveryStatusId}`)
    );
  }
}
