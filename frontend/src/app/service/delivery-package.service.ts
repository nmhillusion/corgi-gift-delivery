import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { IdType } from "@app/model/core/id.model";
import { environment } from "@app/../environments/environment";
import { DeliveryPackageModel } from "@app/model/business/delivery-package.model";

@Injectable({
  providedIn: "root",
})
export class DeliveryPackageService {
  constructor(private $httpClient: HttpClient) {}

  private buildApiUrl(path_: string | IdType) {
    return `${environment.LINK.API_BASE_URL}/api/delivery-package/${path_}`;
  }

  save(deliveryPackage: DeliveryPackageModel) {
    return this.$httpClient.post<DeliveryPackageModel>(
      this.buildApiUrl("save"),
      deliveryPackage
    );
  }

  findById(packageId: IdType) {
    return this.$httpClient.get<DeliveryPackageModel>(
      this.buildApiUrl(packageId)
    );
  }
}
