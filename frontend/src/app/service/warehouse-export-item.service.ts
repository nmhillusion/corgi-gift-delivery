import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { WarehouseExportItemModel } from "@app/model/business/warehouse-export-item.model";
import { Page } from "@app/model/core/page.model";

@Injectable({
  providedIn: "root",
})
export class WarehouseExportItemService {
  constructor(private $http: HttpClient) {}

  private buildApiUrl(path: string): string {
    return `${environment.LINK.API_BASE_URL}/warehouse-export-item/${path}`;
  }

  search(
    dto: {
      name?: string;
    },
    pageIndex: number,
    pageSize: number
  ) {
    return this.$http.post<Page<WarehouseExportItemModel>>(
      this.buildApiUrl("search"),
      dto,
      {
        params: {
          pageIndex,
          pageSize,
        },
      }
    );
  }

  save(warehouseExportItem: WarehouseExportItemModel) {
    return this.$http.post<WarehouseExportItemModel>(
      this.buildApiUrl("save"),
      warehouseExportItem
    );
  }

  delete(warehouseExportItem: WarehouseExportItemModel) {
    return this.$http.delete(
      this.buildApiUrl(`delete/${warehouseExportItem.itemId}`)
    );
  }

  findById(itemId: number) {
    return this.$http.get<WarehouseExportItemModel>(
      this.buildApiUrl(`${itemId}`)
    );
  }

  countExportedCommodity(
    warehouseId: string | number,
    commodityId: string | number
  ) {
    return this.$http.get<number>(
      this.buildApiUrl("commodity/exported/count"),
      {
        params: {
          warehouseId,
          commodityId,
        },
      }
    );
  }
}
