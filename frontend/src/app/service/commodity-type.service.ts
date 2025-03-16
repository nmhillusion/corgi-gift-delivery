import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { CommodityTypeModel } from "@app/model/business/commodity-type.model";
import { IdType } from "@app/model/core/id.model";
import { Page } from "@app/model/core/page.model";
import { map } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class CommodityTypeService {
  
  constructor(private http: HttpClient) {}

  private buildApiUrl(path: string): string {
    return `${environment.LINK.API_BASE_URL}/api/commodity-type${path}`;
  }

  search(dto: { keyword: string }, pageIndex: number, pageSize: number) {
    return this.http.post<Page<CommodityTypeModel>>(
      this.buildApiUrl("/search"),
      dto,
      {
        params: {
          pageIndex,
          pageSize,
        },
      }
    );
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

  importExcel(excelFile: File) {
    const submitForm = new FormData();

    submitForm.append("excelFile", new Blob([excelFile]));

    return this.http.post<CommodityTypeModel[]>(
      this.buildApiUrl("/import/excel"),
      submitForm
    );
  }

  findById(comTypeId: IdType) {
    return this.http.get<CommodityTypeModel>(
      this.buildApiUrl(`/${comTypeId}`),
    )
  }
}
