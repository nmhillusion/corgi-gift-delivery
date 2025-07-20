import { NgModule } from "@angular/core";
import { DeliveryRoutingModule } from "./delivery_routing.module";
import { DeliveryComponent } from "./delivery.component";

@NgModule({
  imports: [DeliveryRoutingModule, DeliveryComponent],
  exports: [DeliveryComponent],
})
export class DeliveryModule {}
