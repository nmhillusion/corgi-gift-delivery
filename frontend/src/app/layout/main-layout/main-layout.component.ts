import {
  Component,
  Input,
  OnInit,
  signal,
  WritableSignal,
} from "@angular/core";
import { Menu } from "@app/model/core/menu.model";
import { MenuComponent } from "../menu/menu.component";
import { BasePage } from "@app/pages/base.page";

@Component({
  standalone: true,
  selector: "main-layout",
  templateUrl: "./main-layout.component.html",
  styleUrls: ["./main-layout.component.scss"],
  imports: [MenuComponent],
})
export class MainLayoutComponent implements OnInit {
  menuList$: WritableSignal<Menu[]> = signal([]);

  @Input({ required: true }) basePage!: BasePage;

  ngOnInit(): void {
    this.menuList$.update((list) => {
      list.push({
        url: "",
        name: "Commodity Mgmt",
        icon: "inventory_2",
        children: [
          {
            url: "/commodity/commodity-mgmt",
            name: "Commodity",
            icon: "inventory_2",
          },
          {
            url: "/commodity/commodity-type-mgmt",
            name: "Commodity Type",
            icon: "inventory_2",
          },
        ],
      });

      list.push({
        url: "/delivery/list",
        name: "Delivery Mgmt",
        icon: "inventory_2",
      });

      list.push({
        url: "/recipient/list",
        name: "Recipient Mgmt",
        icon: "inventory_2",
      });

      list.push({
        url: "/shipper",
        name: "Shipper Mgmt",
        icon: "inventory_2",
      });

      list.push({
        url: "/warehouse/list",
        name: "Warehouse Mgmt",
        icon: "inventory_2",
      });

      return list;
    });
  }
}
