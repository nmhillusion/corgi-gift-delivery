import { Component } from "@angular/core";
import { MainLayoutComponent } from "../../../layout/main-layout/main-layout.component";

@Component({
  standalone: true,
  templateUrl: "./commodity-mgmt.component.html",
  styleUrl: "./commodity-mgmt.component.scss",
  imports: [MainLayoutComponent],
})
export class CommodityMgmtComponent {
  title = "commodity-mgmt";
}
