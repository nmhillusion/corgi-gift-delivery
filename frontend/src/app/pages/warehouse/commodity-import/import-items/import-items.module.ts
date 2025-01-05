import { NgModule } from "@angular/core";
import { ImportItemsRoutingModule } from "./import-items.routes";


@NgModule({
  imports: [ImportItemsRoutingModule],
  exports: [ImportItemsRoutingModule]
})
export class ImportItemsModule {}