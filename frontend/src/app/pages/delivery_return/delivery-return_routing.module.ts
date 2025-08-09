import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { DeliveryReturnComponent } from "./delivery-return.component";

const routes = [
  {
    path: "",
    component: DeliveryReturnComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DeliveryReturnRoutingModule {}
