import { NgModule } from "@angular/core";
import { DeliveryAttemptRoutingModule } from "./delivery-attempt.routes";

@NgModule({
  imports: [DeliveryAttemptRoutingModule],
  exports: [DeliveryAttemptRoutingModule],
})
export class DeliveryAttemptModule {}
