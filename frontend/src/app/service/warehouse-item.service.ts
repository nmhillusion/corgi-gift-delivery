import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { WarehouseItemModel } from "@app/model/business/warehouse-item.model";
import { Page } from "@app/model/core/page.model";

@Injectable({ providedIn: "root" })
export class WarehouseItemService {
  constructor(private $http: HttpClient) {}

  buildApiUrl(path: string): string {
    return `${environment.LINK.API_BASE_URL}/api/warehouse-item${path}`;
  }

  findById(itemId: number) {
    return this.$http.get<WarehouseItemModel>(this.buildApiUrl(`/${itemId}`));
  }

  searchItemsInWarehouse(
    warehouseId: number,
    pageIndex: number,
    pageSize: number,
    searchDto: {
      name: string;
    }
  ) {
    return this.$http.post<Page<WarehouseItemModel>>(
      this.buildApiUrl(`/search-in-warehouse/${warehouseId}`),
      searchDto,
      {
        params: {
          pageIndex,
          pageSize,
        },
      }
    );
  }

  searchItemsInImport(
    importId: number | string,
    pageIndex: number,
    pageSize: number,
    searchDto: {
      name: string;
    }
  ) {
    return this.$http.post<Page<WarehouseItemModel>>(
      this.buildApiUrl(`/search-in-import/${importId}`),
      searchDto,
      {
        params: {
          pageIndex,
          pageSize,
        },
      }
    );
  }

  sync(warehouseItem: WarehouseItemModel) {
    return this.$http.post<WarehouseItemModel>(
      this.buildApiUrl("/sync"),
      warehouseItem
    );
  }
}
