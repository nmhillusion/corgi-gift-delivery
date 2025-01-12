import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { RecipientTypeModel } from "@app/model/business/recipient-type.model";
import { IdType } from "@app/model/core/id.model";

@Injectable({
  providedIn: "root",
})
export class RecipientTypeService {
  constructor(private $http: HttpClient) {}

  private buildApiUrl(path: string) {
    return `${environment.LINK.API_BASE_URL}/api/recipient-type${path}`;
  }

  findAll() {
    return this.$http.get<RecipientTypeModel[]>(this.buildApiUrl("/find-all"));
  }

  findById(recipientTypeId: IdType) {
    return this.$http.get<RecipientTypeModel>(this.buildApiUrl(`/${recipientTypeId}`));
  }
}
