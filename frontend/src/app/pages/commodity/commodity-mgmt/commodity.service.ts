import { HttpClient } from "@angular/common/http";
import { Injectable, signal } from "@angular/core";
import { environment } from "@app/../environments/environment";
import {
  CommodityFEModel,
  CommodityModel,
} from "@app/model/business/commodity.model";
import { IdType } from "@app/model/core/id.model";
import { Page } from "@app/model/core/page.model";
import { BasePage } from "@app/pages/base.page";
import { CommodityTypeService } from "@app/service/commodity-type.service";

@Injectable({ providedIn: "root" })
export class CommodityService {
  constructor(private $httpClient: HttpClient) {}

  private buildApiUrl(path: string): string {
    return `${environment.LINK.API_BASE_URL}/api/commodity${path}`;
  }

  search(dto: { keyword: string }, pageIndex: number, pageSize: number) {
    return this.$httpClient.post<Page<CommodityModel>>(
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

  sync(commodity: CommodityModel) {
    return this.$httpClient.post(this.buildApiUrl("/sync"), commodity);
  }

  importExcel(excelFile: File) {
    const formData = new FormData();
    formData.append("excelFile", new Blob([excelFile]));

    return this.$httpClient.post<CommodityModel[]>(
      this.buildApiUrl("/import/excel"),
      formData
    );
  }

  findById(comId: IdType) {
    return this.$httpClient.get<CommodityModel>(this.buildApiUrl(`/${comId}`));
  }

  convertToFe(comEntity: CommodityModel, basePage: BasePage) {
    const comFe = comEntity as CommodityFEModel;

    comFe.comType$ = signal(null);

    basePage.registerSubscription(
      basePage.$injector
        .get(CommodityTypeService)
        .findById(comEntity.comTypeId || 0)
        .subscribe((comType) => {
          comFe.comType$.set(comType);
        })
    );

    return comFe;
  }
}
