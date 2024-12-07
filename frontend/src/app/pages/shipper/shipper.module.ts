import { NgModule } from "@angular/core";
import { ShipperRoutingModule } from "./shipper.routes";

@NgModule({
  imports: [ShipperRoutingModule],
  exports: [ShipperRoutingModule],
})
export class ShipperModule {}