import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@app/../environments/environment";
import { RecipientModel } from "@app/model/business/recipient.model";
import { Page } from "@app/model/core/page.model";

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
}
