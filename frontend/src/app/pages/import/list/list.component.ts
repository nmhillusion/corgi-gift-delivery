import { Component } from "@angular/core";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { BasePage } from "@app/pages/base.page";

@Component({
  templateUrl: "./list.component.html",
  styleUrls: ["./list.component.scss"],
  imports: [
    AppCommonModule,
    MainLayoutComponent
  ]
})
export class ListComponent extends BasePage {
  constructor() {
    super("Import");
  }
}