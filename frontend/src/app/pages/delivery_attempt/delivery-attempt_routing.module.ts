
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { DeliveryComponent } from "./delivery.component";

const routes = [
  {
    path: "",
    component: DeliveryComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DeliveryRoutingModule {}