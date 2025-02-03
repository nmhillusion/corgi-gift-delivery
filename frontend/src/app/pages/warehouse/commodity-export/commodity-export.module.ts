import { NgModule } from "@angular/core";
import { CommodityExportRoutingModule } from "./commodity-export.routes";

@NgModule({
  imports: [CommodityExportRoutingModule],
  exports: [CommodityExportRoutingModule],
})
export class CommodityExportModule {}
