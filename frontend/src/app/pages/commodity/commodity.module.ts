import { NgModule } from "@angular/core";
import { CommodityRoutingModule } from "./commodity.routes";

@NgModule({
  imports: [CommodityRoutingModule],
  exports: [CommodityRoutingModule],
})
export class CommodityModule {}
