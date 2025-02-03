import { HttpClient } from "@angular/common/http";
import { environment } from "../../environments/environment";
import {
  ShipperFEModel,
  ShipperModel,
} from "@app/model/business/shipper.model";
import { Page } from "@app/model/core/page.model";
import { Injectable, signal } from "@angular/core";
import { Observable } from "rxjs";
import { BasePage } from "@app/pages/base.page";
import { ShipperTypeService } from "./shipper-type.service";
import { ShipperTypeModel } from "../model/business/shipper-type.model";
import { Nullable } from "@app/model/core/nullable.model";
import { IdType } from "@app/model/core/id.model";

@Injectable({
  providedIn: "root",
})
export class ShipperService {
  constructor(private $http: HttpClient) {}

  buildApiUrl(path: string | IdType) {
    return `${environment.LINK.API_BASE_URL}/api/shipper/${path}`;
  }

  findById(shipperId: IdType) {
    return this.$http.get<ShipperModel>(this.buildApiUrl(shipperId));
  }

  search(dto: { name?: string }, pageIndex: number, pageSize: number) {
    return this.$http.post<Page<ShipperModel>>(
      this.buildApiUrl(`search`),
      dto,
      {
        params: {
          pageIndex,
          pageSize,
        },
      }
    );
  }

  save(shipper: ShipperModel) {
    return this.$http.post<ShipperModel>(this.buildApiUrl(`save`), shipper);
  }

  deleteById(shipperId: number) {
    return this.$http.delete<ShipperModel>(this.buildApiUrl(shipperId));
  }

  convertToFEModel(shipper: ShipperModel, basePage: BasePage): ShipperFEModel {
    const typeSignal$ = signal<Nullable<ShipperTypeModel>>(null);

    basePage.registerSubscription(
      basePage.$injector
        .get(ShipperTypeService)
        .findById(shipper.shipperTypeId || 0)
        .subscribe((type) => {
          typeSignal$.set(type);
        })
    );

    return {
      shipperId: shipper.shipperId,
      shipperCode: shipper.shipperCode,
      shipperName: shipper.shipperName,
      shipperTypeId: shipper.shipperTypeId,
      shipperType$: typeSignal$,
    };
  }

  importExcel(currentItem: File) {
    const submitForm = new FormData();

    submitForm.append("excelFile", new Blob([currentItem]));

    return this.$http.post<ShipperModel[]>(
      this.buildApiUrl("import/excel"),
      submitForm
    );
  }
}
