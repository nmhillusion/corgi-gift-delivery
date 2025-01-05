import { NgModule } from "@angular/core";
import { Route, RouterModule } from "@angular/router";

const routes: Route[] = [
  {
    path: "",
    loadComponent: () =>
      import("./commodity-import.component").then(
        (m) => m.CommodityImportComponent
      ),
  },
  {
    path: ":importId/items",
    loadChildren: () =>
      import("./import-items/import-items.module").then(
        (m) => m.ImportItemsModule
      ),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CommodityImportRoutingModule {}
