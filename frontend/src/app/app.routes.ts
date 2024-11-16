import { Routes } from "@angular/router";

export const routes: Routes = [
  {
    path: "commodity",
    loadChildren: () =>
      import("./pages/commodity/commodity.module").then(
        (m) => m.CommodityModule
      ),
  },
];
