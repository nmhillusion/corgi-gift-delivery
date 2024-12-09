import { Component, signal } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { SIZE } from "@app/layout/size.constant";
import { CustomerModel } from "@app/model/business/customer.model";
import { PaginatorHandler } from "@app/model/core/page.model";
import { BasePage } from "@app/pages/base.page";
import { CustomerService } from "@app/service/customer.service";
import { EditComponent } from "../edit/edit.component";

@Component({
  templateUrl: "./list.component.html",
  styleUrls: ["./list.component.scss"],
  imports: [AppCommonModule, MainLayoutComponent],
})
export class ListComponent extends BasePage {

  pageHandler: PaginatorHandler = {
    length$: signal(0),
    pageSize$: signal(10),
    pageIndex$: signal(0),
    onPageChange: this.search.bind(this),
  };

  customerDataSource = new MatTableDataSource<CustomerModel>();

  displayedColumns = ["customerId", "fullName", "idCardNumber", "action"];

  /// methods

  constructor(private $customerService: CustomerService) {
    super("Customer");
  }

  protected override __ngOnInit__() {}

  search() {
    this.registerSubscription(
      this.$customerService
        .search(
          {
            name: "",
          },
          this.pageHandler.pageIndex$(),
          this.pageHandler.pageSize$()
        )
        .subscribe((result) => {
          this.handlePageDataUpdate(
            result,
            this.pageHandler,
            this.customerDataSource
          );
        })
    );
  }

  addCustomer() {
    this.openEditDialog();  
  }

  editCustomer(customer: CustomerModel) {
    this.openEditDialog(customer);
  }

  private openEditDialog(customer?: CustomerModel) {
    const ref = this.$dialog.open<EditComponent>(EditComponent, {
      width: SIZE.DIALOG.width,
      maxHeight: SIZE.DIALOG.height,
    });

    this.registerSubscription(
      ref.afterClosed().subscribe((result) => {
        this.search();
      })
    );
  }
}
