import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { CustomerModel } from "@app/model/business/customer.model";
import { Page } from "@app/model/core/page.model";

@Injectable({
  providedIn: "root",
})
export class CustomerService {
  constructor(private $http: HttpClient) {}

  private buildApiUrl(path: string) {
    return `${environment.LINK.API_BASE_URL}/api/customer${path}`;
  }

  search(dto: {
    name?: string
  }, pageIndex: number, pageSize: number) {
    return this.$http.post<Page<CustomerModel>>(
      this.buildApiUrl(`/search`),
      dto,
      {
        params: {
          pageIndex,
          pageSize,
        },
      }
    );
  }

  sync(customer: CustomerModel) {
    return this.$http.post<CustomerModel>(
      this.buildApiUrl("/sync"),
      customer
    );
  }
}