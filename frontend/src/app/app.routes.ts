import { Routes } from "@angular/router";

export const routes: Routes = [
  {
    path: "",
    redirectTo: "delivery",
    pathMatch: "full",
  },
  {
    path: "delivery",
    loadChildren: () =>
      import("./pages/delivery/delivery.module").then((m) => m.DeliveryModule),
  },
  {
    path: "delivery-attempt",
    loadChildren: () =>
      import("./pages/delivery_attempt/delivery-attempt.module").then(
        (m) => m.DeliveryAttemptModule
      ),
  },
];
