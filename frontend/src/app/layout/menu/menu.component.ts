import { Component, Input } from "@angular/core";
import { MatIconModule } from "@angular/material/icon";
import { RouterModule } from "@angular/router";
import { Menu } from "@app/model/core/menu.model";

@Component({
  selector: "app-menu",
  templateUrl: "./menu.component.html",
  styleUrls: ["./menu.component.scss"],
  imports: [MatIconModule, RouterModule],
})
export class MenuComponent {
  @Input("data") data!: Menu;
}
