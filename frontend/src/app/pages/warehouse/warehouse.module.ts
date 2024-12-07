import { NgModule } from "@angular/core";
import { WarehouseRoutingModule } from "./warehouse.routes";

@NgModule({
  imports: [WarehouseRoutingModule],
  exports: [WarehouseRoutingModule],
})
export class WarehouseModule {}