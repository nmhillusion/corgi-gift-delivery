import { NgModule } from "@angular/core";
import { DeliveryRoutingModule } from "./delivery.routes";

@NgModule({
  imports: [DeliveryRoutingModule],
  exports: [DeliveryRoutingModule],
})
export class DeliveryModule {}
