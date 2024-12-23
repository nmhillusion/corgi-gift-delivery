import { Component } from "@angular/core";
import { AppCommonModule } from "@app/core/app-common.module";
import { BasePage } from "@app/pages/base.page";
import { MainLayoutComponent } from "../../../../layout/main-layout/main-layout.component";

@Component({
  templateUrl: "./list.component.html",
  styleUrls: [
    "./list.component.scss"
  ],
  imports: [
    AppCommonModule,
    MainLayoutComponent
]
})
export class ListComponent extends BasePage {

  /// methods
  constructor() {
    super("List Import Item");
  }
}