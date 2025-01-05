import { Component, signal } from "@angular/core";
import { AppCommonModule } from "@app/core/app-common.module";
import { BasePage } from "@app/pages/base.page";
import { MainLayoutComponent } from "../../../../layout/main-layout/main-layout.component";
import { EditComponent } from "../edit/edit.component";
import { SIZE } from "@app/layout/size.constant";
import { PageEvent } from "@angular/material/paginator";
import { DEFAULT_PAGE_EVENT } from "@app/layout/page.constant";
import { WarehouseItemModel } from "@app/model/business/warehouse-item.model";

@Component({
  templateUrl: "./list.component.html",
  styleUrls: ["./list.component.scss"],
  imports: [AppCommonModule, MainLayoutComponent],
})
export class ListComponent extends BasePage {
  importId?: number;

  /// methods
  constructor() {
    super("List Import Item");
  }

  protected override __ngOnInit__() {
    this.importId = this.$activatedRoute.snapshot.params["importId"];

    console.log("importId: ", this.importId);

    this.search(DEFAULT_PAGE_EVENT);
  }

  search(pageRequest: PageEvent) {}

  createImportItem() {
    this.openEditDialog({
      importId: this.importId,
    });
  }

  private openEditDialog(importItem?: WarehouseItemModel) {
    const ref_ = this.$dialog.open<
      EditComponent,
      { importItem?: WarehouseItemModel }
    >(EditComponent, {
      data: {
        importItem,
      },
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
