import { Component, signal } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource, MatTableModule } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { BasePage } from "@app/pages/base.page";
import { AppInputFileComponent } from "@app/widget/component/input-file/input-file.component";
import { BehaviorSubject } from "rxjs";
import {
  DeliveryReturnSearchDto,
  DeliveryReturnService,
} from "./delivery-return.service";
import { DeliveryReturnFE } from "@app/model/business/delivery-return.model";
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { BlobUtil } from "@app/util/blob.util";
import { FormGroup, FormControl } from "@angular/forms";
import { DeliveryReturnStatus } from "@app/model/business/delivery-return-status.model";
import { IdType } from "@app/model/core/id.model";
import { DeliveryReturnStatusService } from "@app/service/delivery-return-status.service";
import { EditDialogComponent } from "./edit-dialog/edit-dialog.component";

@Component({
  standalone: true,
  templateUrl: "./delivery-return.component.html",
  styleUrl: "./delivery-return.component.scss",
  imports: [
    MainLayoutComponent,
    MatTableModule,
    AppCommonModule,
    AppInputFileComponent,
    MatSlideToggleModule,
  ],
})
export class DeliveryReturnComponent extends BasePage {
  // fields
  displayedColumns = [
    "returnId",
    "deliveryId",
    "eventId",
    "customerId",
    "customerName",
    "giftName",
    "returnStatusName",
    "note",
    ///
    "actions",
  ];
  dataSource = new MatTableDataSource<DeliveryReturnFE>([]);

  paginator = this.generatePaginator();

  handler = {
    isImporting$: signal<boolean>(false),
    toggleImporting() {
      this.isImporting$.update((value: boolean) => !value);
    },
  };

  deliveryReturnStatusList$ = signal<DeliveryReturnStatus[]>([]);

  searchForm = new FormGroup({
    eventId: new FormControl<string | null>(null),
    customerId: new FormControl<string | null>(null),
    returnStatusId: new FormControl<IdType | null>(null),
  });

  deliveryReturnImportFile$ = new BehaviorSubject<File[]>([]);

  // methods
  constructor(
    private $deliveryReturnService: DeliveryReturnService,
    private $deliveryReturnStatusService: DeliveryReturnStatusService
  ) {
    super("Delivery Return Management");
  }

  protected override __ngOnInit__() {
    this.registerSubscription(
      this.$deliveryReturnStatusService.getAll().subscribe({
        next: (statusList) => {
          this.deliveryReturnStatusList$.set(statusList);
        },
        error: (error) => {
          console.error("Error fetching delivery return status list:", error);
          this.dialogHandler.alert(
            "Failed to fetch delivery return status list. " +
              this.$errorUtil.retrieErrorMessage(error)
          );
        },
      })
    );

    // Initial search
    this.search(this.generateDefaultPage());
  }

  private buildSearchDto(): DeliveryReturnSearchDto {
    return {
      eventId: this.searchForm.value.eventId,
      customerId: this.searchForm.value.customerId,
      returnStatusId: this.searchForm.value.returnStatusId,
    };
  }

  override search(pageEvt: PageEvent): void {
    this.paginator.pageIndex$.set(pageEvt.pageIndex);
    this.paginator.pageSize$.set(pageEvt.pageSize);

    this.registerSubscription(
      this.$deliveryReturnService
        .search(this.buildSearchDto(), pageEvt.pageIndex, pageEvt.pageSize)
        .subscribe({
          next: (page) => {
            console.log("Delivery return data:", page);
            this.dataSource.data = page.content.map((item) =>
              this.$deliveryReturnService.convertToFE(item, this)
            );
            this.paginator.length$.set(page.page.totalElements || 0);
          },
          error: (error) => {
            console.error("Error searching delivery return data:", error);
            this.dialogHandler.alert(
              "Failed to search delivery return data. " +
                this.$errorUtil.retrieErrorMessage(error)
            );
          },
        })
    );
  }

  importNewDeliveryReturns() {
    const files = this.deliveryReturnImportFile$.getValue();

    console.log("Importing delivery return data from files:", files);

    if (files.length > 0) {
      const file = files[0];
      this.registerSubscription(
        this.$deliveryReturnService.insertBatchByExcelFile(file).subscribe({
          next: (data) => {
            console.log("Delivery return data imported successfully:", data);
            this.dialogHandler.alert(
              "Delivery return data imported successfully."
            );
            this.deliveryReturnImportFile$.next([]);
            this.search(this.generateDefaultPage()); // Refresh the data
          },
          error: (error) => {
            console.error("Error importing delivery return data:", error);
            this.dialogHandler.alert(
              "Failed to import delivery return data. " +
                this.$errorUtil.retrieErrorMessage(error)
            );
          },
        })
      );
    } else {
      this.dialogHandler.alert("No file selected for import.");
    }
  }

  updateDeliveryReturnsBatch() {
    const files = this.deliveryReturnImportFile$.getValue();

    console.log("Updating delivery return data from files:", files);

    if (files.length > 0) {
      const file = files[0];
      this.registerSubscription(
        this.$deliveryReturnService.updateBatchByExcelFile(file).subscribe({
          next: (data) => {
            console.log("Delivery return data updated successfully:", data);
            this.dialogHandler.alert(
              "Delivery return data updated successfully."
            );
            this.deliveryReturnImportFile$.next([]);
            this.search(this.generateDefaultPage()); // Refresh the data
          },
          error: (error) => {
            console.error("Error updating delivery return data:", error);
            this.dialogHandler.alert(
              "Failed to update delivery return data. " +
                this.$errorUtil.retrieErrorMessage(error)
            );
          },
        })
      );
    } else {
      this.dialogHandler.alert("No file selected for update.");
    }
  }

  export() {
    this.registerSubscription(
      this.$deliveryReturnService.export(this.buildSearchDto()).subscribe({
        next: (response) => {
          const blob = new Blob([response], {
            type: "application/vnd.ms-excel",
          });
          BlobUtil.downloadBlob(blob, "delivery_returns_export.xlsx");
        },
        error: (error) => {
          console.error("Error exporting delivery returns:", error);
          this.dialogHandler.alert(
            "Failed to export delivery returns. " +
              this.$errorUtil.retrieErrorMessage(error)
          );
        },
      })
    );
  }

  editDeliveryReturn(deliveryReturn: DeliveryReturnFE) {
    const dialogRef = this.$dialog.open<
      EditDialogComponent,
      DeliveryReturnFE,
      DeliveryReturnFE
    >(EditDialogComponent, {
      data: deliveryReturn,
    });

    this.registerSubscription(
      dialogRef.afterClosed().subscribe((result) => {
        if (result) {
          this.$deliveryReturnService
            .update(result.returnId, result)
            .subscribe({
              next: (data) => {
                console.log("Delivery return data updated successfully:", data);
                this.dialogHandler.alert(
                  "Delivery return data updated successfully."
                );
                this.search(this.generateDefaultPage()); // Refresh the data
              },
              error: (error) => {
                console.error("Error updating delivery return data:", error);
                this.dialogHandler.alert(
                  "Failed to update delivery return data. " +
                    this.$errorUtil.retrieErrorMessage(error)
                );
              },
            });
        }
      })
    );
  }
}
