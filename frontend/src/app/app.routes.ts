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
  {
    path: "shipper",
    loadChildren: () =>
      import("./pages/shipper/shipper.module").then((m) => m.ShipperModule),
  },
  {
    path: "recipient",
    loadChildren: () =>
      import("./pages/recipient/recipient.module").then(
        (m) => m.RecipientModule
      ),
  },
  {
    path: "warehouse",
    loadChildren: () =>
      import("./pages/warehouse/warehouse.module").then(
        (m) => m.WarehouseModule
      ),
  },
  {
    path: "import",
    loadChildren: () =>
      import("./pages/import/import.module").then((m) => m.ImportModule),
  },
];
