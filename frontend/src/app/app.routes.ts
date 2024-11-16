import { Routes } from "@angular/router";

export const routes: Routes = [
  {
    path: "",
    redirectTo: "delivery",
    pathMatch: "full",
  },
  {
    path: "commodity",
    loadChildren: () =>
      import("./pages/commodity/commodity.module").then(
        (m) => m.CommodityModule
      ),
  },
  {
    path: "delivery",
    loadChildren: () =>
      import("./pages/delivery/delivery.module").then((m) => m.DeliveryModule),
  },
];
