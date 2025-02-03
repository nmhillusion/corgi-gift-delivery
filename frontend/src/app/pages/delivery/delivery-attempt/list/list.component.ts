import { Component } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { PAGE } from "@app/layout/page.constant";
import { DeliveryAttemptFEModel } from "@app/model/business/delivery-attempt.model";
import { IdType } from "@app/model/core/id.model";
import { BasePage } from "@app/pages/base.page";
import { DeliveryAttemptService } from "@app/service/delivery-attempt.service";
import { EditComponent } from "../edit/edit.component";
import { SIZE } from "@app/layout/size.constant";

@Component({
  standalone: true,
  templateUrl: "./list.component.html",
  styleUrls: ["./list.component.scss"],
  imports: [AppCommonModule, MainLayoutComponent],
})
export class ListComponent extends BasePage {
  deliveryId: IdType = "";

  tableDatasource = new MatTableDataSource<DeliveryAttemptFEModel>();
  paginator = this.generatePaginator();

  displayedColumns = [];
  /// methods

  constructor(private $deliveryAttemptService: DeliveryAttemptService) {
    super("Delivery Attempt List");
  }

  override __ngOnInit__() {
    this.deliveryId = this.paramUtils.getParamOrThrow(
      this.$activatedRoute,
      "deliveryId"
    );

    this.search(PAGE.DEFAULT_PAGE_EVENT);
  }

  override search(pageEvt: PageEvent) {
    this.registerSubscription(
      this.$deliveryAttemptService
        .search(
          this.deliveryId,
          { name: "" },
          pageEvt.pageIndex,
          pageEvt.pageSize
        )
        .subscribe({
          next: (pageResult) => {
            this.handlePageDataUpdate(
              {
                content: pageResult.content.map((m) =>
                  this.$deliveryAttemptService.convertToFEModel(m, this)
                ),
                page: pageResult.page,
              },
              this.paginator,
              this.tableDatasource
            );
          },
        })
    );
  }

  editAttempt(deliveryAttempt: DeliveryAttemptFEModel) {
    this.openEditDialog(deliveryAttempt);
  }

  private openEditDialog(deliveryAttempt?: DeliveryAttemptFEModel) {
    const dialogRef = this.$dialog.open<EditComponent>(EditComponent, {
      data: {
        deliveryAttempt,
      },
      width: SIZE.DIALOG.width,
      maxHeight: SIZE.DIALOG.height,
    });

    this.registerSubscription(
      dialogRef.afterClosed().subscribe(() => {
        this.search(PAGE.DEFAULT_PAGE_EVENT);
      })
    );
  }

  addAttempt() {
    this.openEditDialog();
  }
}
