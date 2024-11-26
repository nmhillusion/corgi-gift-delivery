import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@app/../environments/environment";

@Injectable({
  providedIn: "root",
})
export class CommodityTypeService {
  constructor(private http: HttpClient) {}

  private buildApiUrl(path: string): string {
    return `${environment.LINK.API_BASE_URL}/api/v1/commodity-type${path}`;
  }

  findAll() {
    return this.http.get(this.buildApiUrl("/find-all"));
  }
}
