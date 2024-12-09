import { Component, signal } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { SIZE } from "@app/layout/size.constant";
import {
  CustomerFEModel,
  CustomerModel,
} from "@app/model/business/customer.model";
import { Page, PaginatorHandler } from "@app/model/core/page.model";
import { BasePage } from "@app/pages/base.page";
import { CustomerService } from "@app/service/customer.service";
import { EditComponent } from "../edit/edit.component";
import { PAGE } from "@app/layout/page.constant";
import { CustomerTypeService } from "@app/service/customer-type.service";
import { firstValueFrom } from "rxjs";

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
    pageSizeOptions$: signal(PAGE.SIZE_OPTIONS),
    onPageChange: this.search.bind(this),
  };

  customerDataSource = new MatTableDataSource<CustomerFEModel>();

  displayedColumns = [
    "customerId",
    "fullName",
    "idCardNumber",
    "customerType",
    "action",
  ];

  /// methods

  constructor(
    private $customerService: CustomerService,
    private $customerTypeService: CustomerTypeService
  ) {
    super("Customer");
  }

  protected override __ngOnInit__() {
    this.search();
  }

  search(pageEvent?: PageEvent) {
    if (!pageEvent) {
      pageEvent = {
        pageIndex: this.pageHandler.pageIndex$(),
        pageSize: this.pageHandler.pageSize$(),
        length: this.pageHandler.length$(),
      };
    }

    this.registerSubscription(
      this.$customerService
        .search(
          {
            name: "",
          },
          pageEvent.pageIndex,
          pageEvent.pageSize
        )
        .subscribe(async (result) => {
          const convertedPageContent = result.content.map((customer) => {
            const convertedCustomer = customer as CustomerFEModel;
            convertedCustomer.customerType$ = signal(null);

            this.registerSubscription(
              this.$customerTypeService
                .findById(customer.customerTypeId || 0)
                .subscribe((customerType) => {
                  convertedCustomer.customerType$.set(customerType);
                })
            );

            return convertedCustomer;
          });
          const convertedResult: Page<CustomerFEModel> = {
            page: result.page,
            content: convertedPageContent,
          };

          this.handlePageDataUpdate(
            convertedResult,
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
      data: {
        customer,
      },
    });

    this.registerSubscription(
      ref.afterClosed().subscribe((result) => {
        this.search();
      })
    );
  }
}
