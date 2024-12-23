import { Component } from "@angular/core";
import { AppCommonModule } from "@app/core/app-common.module";
import { BasePage } from "@app/pages/base.page";
import { MainLayoutComponent } from "../../../../layout/main-layout/main-layout.component";
import { EditComponent } from "../edit/edit.component";
import { SIZE } from "@app/layout/size.constant";
import { PageEvent } from "@angular/material/paginator";
import { DEFAULT_PAGE_EVENT } from "@app/layout/page.constant";

@Component({
  templateUrl: "./list.component.html",
  styleUrls: ["./list.component.scss"],
  imports: [AppCommonModule, MainLayoutComponent],
})
export class ListComponent extends BasePage {
  /// methods
  constructor() {
    super("List Import Item");
  }

  protected override __ngOnInit__() {
    this.search(DEFAULT_PAGE_EVENT);
  }

  search(pageRequest: PageEvent) {}

  createImportItem() {
    this.openEditDialog();
  }

  private openEditDialog(importItem?: any) {
    const ref_ = this.$dialog.open<EditComponent, any>(EditComponent, {
      data: {},
      width: SIZE.DIALOG.width,
      maxHeight: SIZE.DIALOG.height,
    });

    this.registerSubscription(
      ref_.afterClosed().subscribe((result) => {
        this.search(DEFAULT_PAGE_EVENT);
      })
    );
  }
}
