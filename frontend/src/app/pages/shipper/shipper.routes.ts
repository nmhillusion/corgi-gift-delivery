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
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ShipperRoutingModule {}