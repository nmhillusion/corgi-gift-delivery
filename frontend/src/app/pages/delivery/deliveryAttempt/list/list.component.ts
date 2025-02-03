import { Component, signal } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { PAGE } from "@app/layout/page.constant";
import { DeliveryAttemptModel } from "@app/model/business/delivery-attempt.model";
import { PaginatorHandler } from "@app/model/core/page.model";
import { BasePage } from "@app/pages/base.page";
import { DeliveryAttemptService } from "@app/service/delivery-attempt.service";

@Component({
  standalone: true,
  templateUrl: "./list.component.html",
  styleUrls: ["./list.component.scss"],
  imports: [AppCommonModule],
})
export class ListComponent extends BasePage {
  tableDatasource = new MatTableDataSource<DeliveryAttemptModel>();
  paginator = this.generatePaginator();

  /// methods

  constructor(private $deliveryAttemptService: DeliveryAttemptService) {
    super("Delivery Attempt List");
  }

  override __ngOnInit__() {
    this.search(PAGE.DEFAULT_PAGE_EVENT);
  }

  override search(pageEvt: PageEvent) {}
}
