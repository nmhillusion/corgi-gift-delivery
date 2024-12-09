import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { CustomerTypeModel } from "@app/model/business/customer-type.model";

@Injectable({
  providedIn: "root",
})
export class CustomerTypeService {
  constructor(private $http: HttpClient) {}

  private buildApiUrl(path: string) {
    return `${environment.LINK.API_BASE_URL}/api/customer-type${path}`;
  }

  findAll() {
    return this.$http.get<CustomerTypeModel[]>(this.buildApiUrl("/find-all"));
  }

  findById(customerTypeId: number) {
    return this.$http.get<CustomerTypeModel>(this.buildApiUrl(`/${customerTypeId}`));
  }
}
