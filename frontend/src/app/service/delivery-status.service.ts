import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "../../environment/environment";

export interface DeliveryStatus {
  statusId: string;
  statusName: string;
}

@Injectable({
  providedIn: "root",
})
export class DeliveryStatusService {
  constructor(private http: HttpClient) {}

  private buildApiUrl(path: string): string {
    return `${environment.LINK.API_BASE_URL}/api/v1/delivery-status/${path}`;
  }

  getDeliveryStatus() {
    return this.http.get<DeliveryStatus[]>(this.buildApiUrl("list"));
  }
}
