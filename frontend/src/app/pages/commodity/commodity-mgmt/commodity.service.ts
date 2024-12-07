import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { CommodityModel } from "@app/model/business/commodity.model";

@Injectable({ providedIn: "root" })
export class CommodityService {
  
  constructor(private $httpClient: HttpClient) {}
  
  private buildApiUrl(path: string): string {
    return `${environment.LINK.API_BASE_URL}/api/commodity${path}`
  }

  findAll() {
    return this.$httpClient.get<CommodityModel[]>(this.buildApiUrl("/find-all"));
  }

  sync(commodity: CommodityModel) {
    return this.$httpClient.post(this.buildApiUrl("/sync"), commodity);
  }
}