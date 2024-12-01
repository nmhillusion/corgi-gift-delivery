import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";

const routes: Routes = [
  {
    path: "",
    redirectTo: "commodity-mgmt",
    pathMatch: "full",
  },
  {
    path: "commodity-type-mgmt",
    loadChildren: () =>
      import("./commodity-type-mgmt/commodity-type-mgmt.module").then(
        (m) => m.CommodityTypeModule
      ),
  },
  {
    path: "commodity-mgmt",
    loadComponent: () =>
      import("./commodity-mgmt/commodity-mgmt.component").then(
        (m) => m.CommodityMgmtComponent
      ),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CommodityRoutingModule {}
