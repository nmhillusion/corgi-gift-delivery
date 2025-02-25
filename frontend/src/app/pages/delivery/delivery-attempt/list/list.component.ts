import { Component, signal } from "@angular/core";
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
import { ProcessComponent } from "../process/process.component";
import { Nullable } from "@app/model/core/nullable.model";
import { DeliveryService } from "@app/service/delivery.service";
import { DeliveryModel } from "@app/model/business/delivery.model";
import { NoteDialog } from "@app/pages/shared/note/note-dialog/note-dialog.component";
import { BehaviorSubject } from "rxjs";
import { NoteOwnerDto } from "@app/model/business/note.model";

@Component({
  standalone: true,
  templateUrl: "./list.component.html",
  styleUrls: ["./list.component.scss"],
  imports: [AppCommonModule, MainLayoutComponent],
})
export class ListComponent extends BasePage {
  deliveryId: IdType = "";
  delivery$ = signal<Nullable<DeliveryModel>>(null);

  tableDatasource = new MatTableDataSource<DeliveryAttemptFEModel>();
  paginator = this.generatePaginator();

  displayedColumns = [
    "attemptId",
    "deliveryType",
    "shipper",
    "deliveryStatus",
    "startTime",
    "endTime",
    ///
    "action",
  ];
  /// methods

  constructor(
    private $deliveryAttemptService: DeliveryAttemptService,
    private $deliveryService: DeliveryService
  ) {
    super("Delivery Attempt List");
  }

  override __ngOnInit__() {
    this.deliveryId = this.paramUtils.getParamOrThrow(
      this.$activatedRoute,
      "deliveryId"
    );

    this.registerSubscription(
      this.$deliveryService.findById(this.deliveryId).subscribe((delivery) => {
        this.delivery$.set(delivery);
      })
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
        deliveryId: this.deliveryId,
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

  processAttempt(attempt: DeliveryAttemptFEModel) {
    const dialogRef = this.$dialog.open<ProcessComponent>(ProcessComponent, {
      data: {
        deliveryId: this.deliveryId,
        deliveryAttempt: attempt,
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

  noteAttempt(attempt: DeliveryAttemptFEModel) {
    this.showNoteDialog<NoteDialog>(
      new BehaviorSubject<NoteOwnerDto>({
        deliveryAttemptId: attempt.attemptId,
      }),
      NoteDialog
    );
  }
}
