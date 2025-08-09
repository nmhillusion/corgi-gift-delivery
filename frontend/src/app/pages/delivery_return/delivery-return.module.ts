import { NgModule } from "@angular/core";
import { DeliveryReturnComponent } from "./delivery-return.component";
import { DeliveryReturnRoutingModule } from "./delivery-return_routing.module";

@NgModule({
  imports: [DeliveryReturnRoutingModule, DeliveryReturnComponent],
  exports: [DeliveryReturnComponent],
})
export class DeliveryReturnModule {}
