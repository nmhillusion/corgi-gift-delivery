import { NgModule } from "@angular/core";
import { ExportItemsRoutingModule } from "./export-items.routes";


@NgModule({
  imports: [ExportItemsRoutingModule],
  exports: [ExportItemsRoutingModule]
})
export class ExportItemsModule {}