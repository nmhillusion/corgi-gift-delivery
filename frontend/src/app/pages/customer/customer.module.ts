import { NgModule } from "@angular/core";
import { CustomerRoutingModule } from "./customer.routes";

@NgModule({
  imports: [CustomerRoutingModule],
  exports: [CustomerRoutingModule],
})
export class CustomerModule {}