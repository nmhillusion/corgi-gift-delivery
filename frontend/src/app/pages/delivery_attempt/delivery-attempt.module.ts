import { NgModule } from "@angular/core";
import { DeliveryAttemptComponent } from "./delivery-attempt.component";
import { DeliveryAttemptRoutingModule } from "./delivery-attempt_routing.module";

@NgModule({
  imports: [DeliveryAttemptRoutingModule, DeliveryAttemptComponent],
  exports: [DeliveryAttemptComponent],
})
export class DeliveryAttemptModule {}
