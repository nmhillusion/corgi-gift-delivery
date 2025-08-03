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
        url: "/delivery/list",
        name: "Delivery Mgmt",
        icon: "local_shipping",
      });

      list.push({
        url: "/delivery-attempt/list",
        name: "Delivery Attempt Mgmt",
        icon: "delivery_dining",
      });

      return list;
    });
  }
}
