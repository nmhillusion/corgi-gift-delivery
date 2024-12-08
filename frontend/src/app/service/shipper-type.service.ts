import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { ShipperTypeModel } from "@app/model/business/shipper-type.model";

@Injectable({
  providedIn: "root",
})
export class ShipperTypeService {
  constructor(private $http: HttpClient) {}

  private buildApiUrl(path: string): string {
    return `${environment.LINK.API_BASE_URL}/api/shipper-type${path}`;
  }

  findAll() {
    return this.$http.get<ShipperTypeModel[]>(this.buildApiUrl("/find-all"));
  }

  findById(id: string) {
    return this.$http.get<ShipperTypeModel>(this.buildApiUrl(`/${id}`));
  }
}
