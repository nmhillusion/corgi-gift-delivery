import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { CommodityExportModel } from "@app/model/business/commodity-export.model";
import { WarehouseExportItemModel } from "@app/model/business/warehouse-export-item.model";
import { IdType } from "@app/model/core/id.model";
import { Page } from "@app/model/core/page.model";

@Injectable({
  providedIn: "root",
})
export class CommodityExportService {
  constructor(private $http: HttpClient) {}

  private buildApiUrl(apiPath: string) {
    return `${environment.LINK.API_BASE_URL}/api/commodity-export${apiPath}`;
  }

  search(
    warehouseId: IdType,
    dto: {
      name: string;
    },
    pageIndex: number,
    pageSize: number
  ) {
    return this.$http.post<Page<CommodityExportModel>>(
      this.buildApiUrl("/search"),
      dto,
      {
        params: {
          warehouseId,
          pageIndex,
          pageSize,
        },
      }
    );
  }

  deleteById(exportId: IdType) {
    return this.$http.delete(this.buildApiUrl(`/delete/${exportId}`));
  }

  save(commodityExport: CommodityExportModel) {
    return this.$http.post<CommodityExportModel>(
      this.buildApiUrl("/save"),
      commodityExport
    );
  }

  findById(exportId: IdType) {
    return this.$http.get<CommodityExportModel>(
      this.buildApiUrl(`/${exportId}`)
    );
  }
}
