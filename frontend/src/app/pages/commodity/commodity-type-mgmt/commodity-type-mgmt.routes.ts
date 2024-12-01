import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";

const routes: Routes = [
  {
    path: "",
    loadComponent: () =>
      import("./commodity-type-mgmt.component").then(
        (m) => m.CommodityTypeMgmtComponent
      ),
  },
  {
    path: "edit/:id",
    loadComponent: () =>
      import("./edit/edit.component").then((m) => m.EditComponent),
  },
  {
    path: "add",
    loadComponent: () =>
      import("./edit/edit.component").then((m) => m.EditComponent),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CommodityTypeRoutingModule {}
