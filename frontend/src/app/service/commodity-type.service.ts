import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { CommodityTypeModel } from "@app/model/business/commodity-type.model";
import { map } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class CommodityTypeService {
  constructor(private http: HttpClient) {}

  private buildApiUrl(path: string): string {
    return `${environment.LINK.API_BASE_URL}/api/commodity-type${path}`;
  }

  findAll() {
    return this.http.get<CommodityTypeModel[]>(this.buildApiUrl("/find-all"));
  }

  sync(commodityType: CommodityTypeModel) {
    return this.http.post<CommodityTypeModel>(
      this.buildApiUrl("/sync"),
      commodityType
    );
  }

  exportExcel() {
    return this.http
      .get(this.buildApiUrl("/export/excel"), {
        responseType: "arraybuffer",
      })
      .pipe(
        map((response) => {
          return new Blob([response], { type: "application/vnd.ms-excel" });
        })
      );
  }
}
