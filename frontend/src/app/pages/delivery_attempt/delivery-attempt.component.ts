import { Component, signal } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource, MatTableModule } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { DeliveryAttemptFE } from "@app/model/business/delivery-attempt.model";
import { BasePage } from "@app/pages/base.page";
import { AppInputFileComponent } from "@app/widget/component/input-file/input-file.component";
import { BehaviorSubject } from "rxjs";
import { DeliveryAttemptSearchDto, DeliveryAttemptService } from "./delivery-attempt.service";
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { BlobUtil } from "@app/util/blob.util";
import { FormControl, FormGroup } from "@angular/forms";

@Component({
  standalone: true,
  templateUrl: "./delivery-attempt.component.html",
  styleUrl: "./delivery-attempt.component.scss",
  imports: [
    MainLayoutComponent,
    MatTableModule,
    AppCommonModule,
    AppInputFileComponent,
    MatSlideToggleModule,
  ],
})
export class DeliveryAttemptComponent extends BasePage {
  // fields
  displayedColumns = [
    "attemptId",
    "deliveryId",
    "eventId",
    "customerId",
    "customerName",
    "giftName",
    "deliveryTypeName",
    "deliveryStatusName",
    "deliveryDate",
    "note",
  ];
  dataSource = new MatTableDataSource<DeliveryAttemptFE>([]);

  paginator = this.generatePaginator();

  handler = {
    isImporting$: signal<boolean>(false),
    toggleImporting() {
      this.isImporting$.update((value: boolean) => !value);
    },
  };

  searchForm = new FormGroup({
    eventId: new FormControl<string | null>(null),
    customerId: new FormControl<string | null>(null),
  });

  deliveryAttemptImportFile$ = new BehaviorSubject<File[]>([]);

  // methods
  constructor(private $deliveryAttemptService: DeliveryAttemptService) {
    super("Delivery Attempt Management");
  }

  protected override __ngOnInit__() {
    // Initial search
    this.search(this.generateDefaultPage());
  }

  override search(pageEvt: PageEvent): void {
    this.paginator.pageIndex$.set(pageEvt.pageIndex);
    this.paginator.pageSize$.set(pageEvt.pageSize);

    this.registerSubscription(
      this.$deliveryAttemptService
        .search(this.buildSearchDto(), pageEvt.pageIndex, pageEvt.pageSize)
        .subscribe({
          next: (page) => {
            console.log("Delivery attempt data:", page);
            this.dataSource.data = page.content.map((item) =>
              this.$deliveryAttemptService.convertToFE(item, this)
            );
            this.paginator.length$.set(page.page.totalElements || 0);
          },
          error: (error) => {
            console.error("Error searching delivery attempt data:", error);
            this.dialogHandler.alert(
              "Failed to search delivery attempt data. " +
                this.$errorUtil.retrieErrorMessage(error)
            );
          },
        })
    );
  }

  importNewDeliveryAttempt() {
    const files = this.deliveryAttemptImportFile$.getValue();

    console.log("Importing delivery attempt data from files:", files);

    if (files.length > 0) {
      const file = files[0];
      this.registerSubscription(
        this.$deliveryAttemptService.insertBatchByExcelFile(file).subscribe({
          next: (data) => {
            console.log("Delivery attempt data imported successfully:", data);
            this.dialogHandler.alert(
              "Delivery attempt data imported successfully."
            );
            this.deliveryAttemptImportFile$.next([]);
            this.search(this.generateDefaultPage()); // Refresh the data
          },
          error: (error) => {
            console.error("Error importing delivery attempt data:", error);
            this.dialogHandler.alert(
              "Failed to import delivery attempt data. " +
                this.$errorUtil.retrieErrorMessage(error)
            );
          },
        })
      );
    } else {
      this.dialogHandler.alert("No file selected for import.");
    }
  }

  updateDeliveryAttemptsBatch() {
    const files = this.deliveryAttemptImportFile$.getValue();

    console.log("Updating delivery attempt data from files:", files);

    if (files.length > 0) {
      const file = files[0];
      this.registerSubscription(
        this.$deliveryAttemptService.updateBatchByExcelFile(file).subscribe({
          next: (data) => {
            console.log("Delivery attempt data updated successfully:", data);
            this.dialogHandler.alert(
              "Delivery attempt data updated successfully."
            );
            this.deliveryAttemptImportFile$.next([]);
            this.search(this.generateDefaultPage()); // Refresh the data
          },
          error: (error) => {
            console.error("Error updating delivery attempt data:", error);
            this.dialogHandler.alert(
              "Failed to update delivery attempt data. " +
                this.$errorUtil.retrieErrorMessage(error)
            );
          },
        })
      );
    } else {
      this.dialogHandler.alert("No file selected for update.");
    }
  }

  private buildSearchDto(): DeliveryAttemptSearchDto {
    return {
      eventId: this.searchForm.value.eventId,
      customerId: this.searchForm.value.customerId,
    };
  }

  export() {
    this.registerSubscription(
      this.$deliveryAttemptService
        .export(this.buildSearchDto())
        .subscribe({
          next: (response) => {
            const blob = new Blob([response], {
              type: "application/vnd.ms-excel",
            });
            BlobUtil.downloadBlob(blob, "deliveries_attempts_export.xlsx");
          },
          error: (error) => {
            console.error("Error exporting delivery attempts:", error);
            this.dialogHandler.alert(
              "Failed to export delivery attempts. " +
                this.$errorUtil.retrieErrorMessage(error)
            );
          },
        })
    );
  }
}
