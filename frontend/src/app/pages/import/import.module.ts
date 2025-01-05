import { NgModule } from "@angular/core";
import { ImportRoutingModule } from "./import.routes";

@NgModule({
  imports: [ImportRoutingModule],
  exports: [ImportRoutingModule],
})
export class ImportModule {}