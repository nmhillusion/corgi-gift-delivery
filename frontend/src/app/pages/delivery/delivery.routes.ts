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
      import("./delivery/list/list.component").then((m) => m.ListComponent),
  },
  {
    path: ":deliveryId/delivery-attempt",
    loadChildren: () =>
      import("./delivery-attempt/delivery-attempt.module").then(
        (m) => m.DeliveryAttemptModule
      ),
  },
  {
    path: ":deliveryId/delivery-package",
    loadComponent: () =>
      import("./delivery-package/delivery-package.component").then(
        (m) => m.DeliveryPackageComponent
      ),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DeliveryRoutingModule {}
