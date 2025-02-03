import { NgModule } from "@angular/core";
import { Route, RouterModule } from "@angular/router";

const routes: Route[] = [
  {
    path: "",
    loadComponent: () =>
      import("./commodity-export.component").then(
        (m) => m.CommodityExportComponent
      ),
  },
  {
    path: ":commodityExportId",
    loadChildren: () =>
      import("./export-items/export-items.module").then(
        (m) => m.ExportItemsModule
      ),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CommodityExportRoutingModule {}
