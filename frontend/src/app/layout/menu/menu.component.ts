import { Component, Input } from "@angular/core";
import { MatIconModule } from "@angular/material/icon";
import { Menu } from "@app/model/menu.model";

@Component({
  selector: "app-menu",
  templateUrl: "./menu.component.html",
  styleUrls: ["./menu.component.scss"],
  imports: [MatIconModule],
})
export class MenuComponent {
  @Input("data") data!: Menu;
}
