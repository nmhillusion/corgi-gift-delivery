import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { IdType } from "@app/model/core/id.model";
import { environment } from "@app/../environments/environment";
import { NoteModel, NoteOwnerDto } from "@app/model/business/note.model";
import { Page } from "@app/model/core/page.model";

@Injectable({ providedIn: "root" })
export class NoteService {
  constructor(private $http: HttpClient) {}

  private buildApiUrl(path_: string | IdType) {
    return `${environment.LINK.API_BASE_URL}/api/note/${path_}`;
  }

  save(note: NoteModel) {
    return this.$http.post<NoteModel>(this.buildApiUrl("save"), note);
  }

  delete(id: IdType) {
    return this.$http.delete(this.buildApiUrl(id));
  }

  findById(id: IdType) {
    return this.$http.get<NoteModel>(this.buildApiUrl(id));
  }

  search(dto: NoteOwnerDto, pageIndex: number, pageSize: number) {
    return this.$http.post<Page<NoteModel>>(this.buildApiUrl("search"), dto, {
      params: {
        pageIndex,
        pageSize,
      },
    });
  }
}
