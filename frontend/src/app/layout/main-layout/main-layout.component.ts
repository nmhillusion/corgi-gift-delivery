import {
  Component,
  Input,
  signal,
  WritableSignal
} from "@angular/core";
import { MatProgressBarModule } from "@angular/material/progress-bar";
import { Menu } from "@app/model/core/menu.model";
import { LoadingMonitor } from "@app/monitor/loading.monitor";
import { BasePage } from "@app/pages/base.page";
import { MenuComponent } from "../menu/menu.component";

@Component({
  standalone: true,
  selector: "main-layout",
  templateUrl: "./main-layout.component.html",
  styleUrls: ["./main-layout.component.scss"],
  imports: [MenuComponent, MatProgressBarModule],
})
export class MainLayoutComponent {
  menuList$: WritableSignal<Menu[]> = signal([]);

  loading$: WritableSignal<boolean> = signal(false);

  @Input({ required: true }) basePage!: BasePage;

  constructor(loadingMonitor: LoadingMonitor) {
    loadingMonitor.onLoadingStateChange((loading) => {
      this.loading$.set(loading);
    });

    this.menuList$.update((list) => {
      list.push({
        url: "/delivery",
        name: "Delivery Mgmt",
        icon: "local_shipping",
      });

      list.push({
        url: "/delivery-attempt",
        name: "Delivery Attempt Mgmt",
        icon: "delivery_dining",
      });

      list.push({
        url: "/delivery-return",
        name: "Delivery Return Mgmt",
        icon: "repartition",
      });

      return list;
    });
  }
}
