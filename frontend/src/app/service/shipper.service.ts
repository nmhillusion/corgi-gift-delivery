import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { ShipperModel } from "@app/model/business/shipper.model";
import { Page } from "@app/model/core/page.model";

@Injectable({
  providedIn: "root",
})
export class ShipperService {
  constructor(private $http: HttpClient) {}

  private buildApiUrl(path: string) {
    return `${environment.LINK.API_BASE_URL}/api/shipper${path}`;
  }

  sync(shipper: ShipperModel) {
    return this.$http.post(this.buildApiUrl("/sync"), shipper);
  }

  search(keyword: string, pageIndex: number, pageSize: number) {
    return this.$http.post<Page<ShipperModel>>(
      this.buildApiUrl(`/search`),
      {
        shipperName: keyword,
      },
      {
        params: {
          pageIndex,
          pageSize,
        },
      }
    );
  }
}
