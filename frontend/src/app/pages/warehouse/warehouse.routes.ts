import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";

const routes: Routes = [
  {
    path: "",
    redirectTo: "list",
    pathMatch: "full",
  },
  {
    path: "list",
    loadComponent: () =>
      import("./list/list.component").then((m) => m.ListComponent),
  },
  {
    path: ":warehouseId/commodity-import",
    loadChildren: () =>
      import("./commodity-import/commodity-import.module").then(
        (m) => m.CommodityImportModule
      ),
  },
  {
    path: ":warehouseId/warehouse-item",
    loadComponent: () =>
      import("./warehouse-item/warehouse-item.component").then(
        (m) => m.WarehouseItemComponent
      ),
  },
  {
    path: ":warehouseId/commodity-export",
    loadChildren: () =>
      import("./commodity-export/commodity-export.module").then(
        (m) => m.CommodityExportModule
      ),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class WarehouseRoutingModule {}
