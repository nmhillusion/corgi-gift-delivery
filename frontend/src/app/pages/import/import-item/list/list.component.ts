import { Component } from "@angular/core";
import { AppCommonModule } from "@app/core/app-common.module";
import { BasePage } from "@app/pages/base.page";

@Component({
  templateUrl: "./list.component.html",
  styleUrls: [
    "./list.component.scss"
  ],
  imports: [
    AppCommonModule
  ]
})
export class ListComponent extends BasePage {

  /// methods
  constructor() {
    super("List Import Item");
  }
}