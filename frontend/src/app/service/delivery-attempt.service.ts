import { HttpClient } from "@angular/common/http";
import { environment } from "@app/../environments/environment";
import { DeliveryAttemptModel } from "@app/model/business/delivery-attempt.model";
import { IdType } from "@app/model/core/id.model";

export class DeliveryAttemptService {
  constructor(private $http: HttpClient) {}

  buildApiUrl(path: string) {
    return `${environment.LINK.API_BASE_URL}/api/delivery-attempt/${path}`;
  }

  search(dto: { name: string }, pageIndex: number, pageSize: number) {
    return this.$http.post<DeliveryAttemptModel[]>(
      this.buildApiUrl("search"),
      dto,
      { params: { pageIndex, pageSize } }
    );
  }

  findById(deliveryAttemptId: IdType) {
    return this.$http.get<DeliveryAttemptModel>(
      this.buildApiUrl(`${deliveryAttemptId}`)
    );
  }

  save(deliveryAttemptModel: DeliveryAttemptModel) {
    return this.$http.post<DeliveryAttemptModel>(
      this.buildApiUrl("save"),
      deliveryAttemptModel
    );
  }

  deleteById(deliveryAttemptId: IdType) {
    return this.$http.delete<DeliveryAttemptModel>(
      this.buildApiUrl(`${deliveryAttemptId}`)
    );
  }
}
