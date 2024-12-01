import { NgModule } from "@angular/core";
import { CommodityTypeRoutingModule } from "./commodity-type-mgmt.routes";

@NgModule({
  imports: [CommodityTypeRoutingModule],
  exports: [CommodityTypeRoutingModule],
})
export class CommodityTypeModule {}
