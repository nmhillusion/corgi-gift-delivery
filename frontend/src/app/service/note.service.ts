import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { IdType } from "@app/model/core/id.model";
import { environment } from "@app/../environments/environment";
import { NoteModel } from "@app/model/business/note.model";

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

  findByRecipientId(recipientId: IdType) {
    return this.$http.get<NoteModel[]>(
      this.buildApiUrl(`findByRecipientId/${recipientId}`)
    );
  }

  findByDeliveryId(deliveryId: IdType) {
    return this.$http.get<NoteModel[]>(
      this.buildApiUrl(`findByDeliveryId/${deliveryId}`)
    );
  }

  findByDeliveryAttemptId(deliveryAttemptId: IdType) {
    return this.$http.get<NoteModel[]>(
      this.buildApiUrl(`findByDeliveryAttemptId/${deliveryAttemptId}`)
    );
  }

  findByImportId(importId: IdType) {
    return this.$http.get<NoteModel[]>(
      this.buildApiUrl(`findByImportId/${importId}`)
    );
  }

  findByWarehouseItemId(warehouseItemId: IdType) {
    return this.$http.get<NoteModel[]>(
      this.buildApiUrl(`findByWarehouseItemId/${warehouseItemId}`)
    );
  }
  
}
