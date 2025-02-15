import { HttpClient } from "@angular/common/http";
import { Injectable, signal } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { RecipientTypeModel } from "@app/model/business/recipient-type.model";
import {
  RecipientFEModel,
  RecipientModel,
} from "@app/model/business/recipient.model";
import { IdType } from "@app/model/core/id.model";
import { Nullable } from "@app/model/core/nullable.model";
import { Page } from "@app/model/core/page.model";
import { BasePage } from "@app/pages/base.page";
import { RecipientTypeService } from "./recipient-type.service";

@Injectable({
  providedIn: "root",
})
export class RecipientService {
  constructor(private $http: HttpClient) {}

  private buildApiUrl(path: string) {
    return `${environment.LINK.API_BASE_URL}/api/recipient${path}`;
  }

  search(
    dto: {
      name?: string;
    },
    pageIndex: number,
    pageSize: number
  ) {
    return this.$http.post<Page<RecipientModel>>(
      this.buildApiUrl(`/search`),
      dto,
      {
        params: {
          pageIndex,
          pageSize,
        },
      }
    );
  }

  sync(recipient: RecipientModel) {
    return this.$http.post<RecipientModel>(
      this.buildApiUrl("/sync"),
      recipient
    );
  }

  importExcel(currentItem: File) {
    const submitForm = new FormData();

    submitForm.append("excelFile", new Blob([currentItem]));

    return this.$http.post<RecipientModel[]>(
      this.buildApiUrl("/import/excel"),
      submitForm
    );
  }

  findById(recipientId: IdType) {
    return this.$http.get<RecipientModel>(this.buildApiUrl(`/${recipientId}`));
  }

  convertToFEModel(
    recipient: RecipientModel,
    basePage: BasePage
  ): Nullable<RecipientFEModel> {
    const feModel = recipient as RecipientFEModel;

    feModel.recipientType$ = signal<Nullable<RecipientTypeModel>>(null);

    basePage.registerSubscription(
      basePage.$injector
        .get(RecipientTypeService)
        .findById(recipient.recipientTypeId || 0)
        .subscribe((type_) => {
          feModel.recipientType$.set(type_);
        })
    );

    return feModel;
  }
}
