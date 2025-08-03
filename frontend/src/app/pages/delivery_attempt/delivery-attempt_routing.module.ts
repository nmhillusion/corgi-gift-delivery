import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { DeliveryAttemptComponent } from "./delivery-attempt.component";

const routes = [
  {
    path: "",
    component: DeliveryAttemptComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DeliveryAttemptRoutingModule {}
