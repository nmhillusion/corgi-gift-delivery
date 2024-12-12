import { Component, signal } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { PAGE } from "@app/layout/page.constant";
import { SIZE } from "@app/layout/size.constant";
import {
  RecipientModel,
  RecipientFEModel,
} from "@app/model/business/recipient.model";
import { Page, PaginatorHandler } from "@app/model/core/page.model";
import { BasePage } from "@app/pages/base.page";
import { RecipientTypeService } from "@app/service/recipient-type.service";
import { RecipientService } from "@app/service/recipient.service";
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
    pageSizeOptions$: signal(PAGE.SIZE_OPTIONS),
    onPageChange: this.search.bind(this),
  };

  recipientDataSource = new MatTableDataSource<RecipientFEModel>();

  displayedColumns = [
    "recipientId",
    "fullName",
    "idCardNumber",
    "recipientType",
    "action",
  ];

  /// methods

  constructor(
    private $recipientService: RecipientService,
    private $recipientTypeService: RecipientTypeService
  ) {
    super("Recipient");
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
      this.$recipientService
        .search(
          {
            name: "",
          },
          pageEvent.pageIndex,
          pageEvent.pageSize
        )
        .subscribe(async (result) => {
          const convertedPageContent = result.content.map((recipient) => {
            const convertedRecipient = recipient as RecipientFEModel;
            convertedRecipient.recipientType$ = signal(null);

            this.registerSubscription(
              this.$recipientTypeService
                .findById(recipient.recipientTypeId || 0)
                .subscribe((customerType) => {
                  convertedRecipient.recipientType$.set(customerType);
                })
            );

            return convertedRecipient;
          });
          const convertedResult: Page<RecipientFEModel> = {
            page: result.page,
            content: convertedPageContent,
          };

          this.handlePageDataUpdate(
            convertedResult,
            this.pageHandler,
            this.recipientDataSource
          );
        })
    );
  }

  addRecipient() {
    this.openEditDialog();
  }

  editRecipient(customer: RecipientModel) {
    this.openEditDialog(customer);
  }

  private openEditDialog(customer?: RecipientModel) {
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
