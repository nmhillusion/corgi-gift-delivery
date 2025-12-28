import { Component, signal } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource, MatTableModule } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { DeliveryFE } from "@app/model/business/delivery.model";
import { BasePage } from "@app/pages/base.page";
import { AppInputFileComponent } from "@app/widget/component/input-file/input-file.component";
import { BehaviorSubject } from "rxjs";
import { DeliverySearchDto, DeliveryService } from "./delivery.service";
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { BlobUtil } from "@app/util/blob.util";
import { DeliveryStatusService } from "@app/service/delivery-status.service";
import { DeliveryStatus } from "@app/model/business/delivery-status.model";
import { DeliveryReturnStatusService } from "@app/service/delivery-return-status.service";
import { DeliveryReturnStatus } from "@app/model/business/delivery-return-status.model";
import { DeliveryType } from "@app/model/business/delivery-type.model";
import { Nullable } from "@app/model/core/nullable.model";
import { IdType } from "@app/model/core/id.model";
import { DeliveryTypeService } from "@app/service/delivery-type.service";
import { EditDialogComponent } from "./edit-dialog/edit-dialog.component";
import { MatDialog, MatDialogRef } from "@angular/material/dialog";

@Component({
  standalone: true,
  templateUrl: "./delivery.component.html",
  styleUrl: "./delivery.component.scss",
  imports: [
    MainLayoutComponent,
    MatTableModule,
    AppCommonModule,
    AppInputFileComponent,
    MatSlideToggleModule,
  ],
})
export class DeliveryComponent extends BasePage {
  // fields
  displayedColumns = [
    "deliveryId",
    "eventId",
    "deliveryPeriodYear",
    "deliveryPeriodMonth",
    "territory",
    "region",
    "organId",
    "receivedOrgan",
    "amdName",
    "customerLevel",
    "customerId",
    "customerName",
    "idCardNumber",
    "phoneNumber",
    "address",
    "giftName",
    "note",
    ///
    "attempt_deliveryType",
    "attempt_deliveryStatus",
    "attempt_note",
    ///
    "return_returnStatus",
    "return_note",
    ///
    "action",
  ];
  dataSource = new MatTableDataSource<DeliveryFE>([]);

  handler = {
    isImporting$: signal<boolean>(false),
    toggleImporting() {
      this.isImporting$.update((value: boolean) => !value);
    },
  };

  paginator = this.generatePaginator();

  deliveryStatusList$ = signal<DeliveryStatus[]>([]);
  deliveryTypeList$ = signal<DeliveryType[]>([]);
  deliveryReturnStatusList$ = signal<DeliveryReturnStatus[]>([]);

  deliveryImportFile$ = new BehaviorSubject<File[]>([]);

  searchForm = new FormGroup({
    eventId: new FormControl<Nullable<IdType>>(null),
    customerId: new FormControl<Nullable<IdType>>(null),
    deliveryStatusId: new FormControl<Nullable<IdType>>(null),
    deliveryTypeId: new FormControl<Nullable<IdType>>(null),
    returnStatusId: new FormControl<Nullable<IdType>>(null),
  });

  // methods
  constructor(
    private $deliveryService: DeliveryService,
    private $deliveryStatusService: DeliveryStatusService,
    private $deliveryTypeService: DeliveryTypeService,
    private $deliveryReturnStatusService: DeliveryReturnStatusService,
    private dialog: MatDialog
  ) {
    super("Delivery Management");
  }

  protected override __ngOnInit__() {
    this.registerSubscription(
      this.$deliveryStatusService.getAll().subscribe({
        next: (list) => {
          console.log("Fetched delivery status list:", list);
          this.deliveryStatusList$.set(list);
        },
        error: (error) => {
          console.error("Error fetching delivery status list:", error);
          this.dialogHandler.alert(
            "Failed to fetch delivery status list. " +
              this.$errorUtil.retrieErrorMessage(error)
          );
        },
      }),

      this.$deliveryTypeService.getAll().subscribe({
        next: (list) => {
          console.log("Fetched delivery type list:", list);
          this.deliveryTypeList$.set(list);
        },
        error: (error) => {
          console.error(
            "Error fetching delivery type delivery type list: ",
            error
          );
          this.dialogHandler.alert(
            "Failed to fetch delivery type list. " +
              this.$errorUtil.retrieErrorMessage(error)
          );
        },
      }),

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

  buildSearchDto(): DeliverySearchDto {
    return {
      eventId: this.searchForm.value.eventId,
      customerId: this.searchForm.value.customerId,
      deliveryStatusId: this.searchForm.value.deliveryStatusId,
      deliveryTypeId: this.searchForm.value.deliveryTypeId,
      returnStatusId: this.searchForm.value.returnStatusId,
    };
  }

  override search(pageEvt: PageEvent): void {
    console.log(" search form = ", this.searchForm.value);

    this.paginator.pageIndex$.set(pageEvt.pageIndex);
    this.paginator.pageSize$.set(pageEvt.pageSize);

    this.registerSubscription(
      this.$deliveryService
        .search(this.buildSearchDto(), pageEvt.pageIndex, pageEvt.pageSize)
        .subscribe({
          next: (page) => {
            this.dataSource.data = page.content.map((item) =>
              this.$deliveryService.convertToFE(item, this)
            );
            this.paginator.length$.set(page.page.totalElements || 0);
          },
          error: (error) => {
            console.error("Error searching delivery data:", error);
            this.dialogHandler.alert(
              "Failed to search delivery data. " +
                this.$errorUtil.retrieErrorMessage(error)
            );
          },
        })
    );
  }

  importNewDelivery() {
    const files = this.deliveryImportFile$.getValue();

    console.log("Importing delivery data from files:", files);

    if (files.length > 0) {
      const file = files[0];
      this.registerSubscription(
        this.$deliveryService.insertBatchByExcelFile(file).subscribe({
          next: (data) => {
            console.log("Delivery data imported successfully:", data);
            this.dialogHandler.alert("Delivery data imported successfully.");
            this.deliveryImportFile$.next([]);
            this.search(this.generateDefaultPage()); // Refresh the data
          },
          error: (error) => {
            console.error("Error importing delivery data:", error);
            this.dialogHandler.alert(
              "Failed to import delivery data. " +
                this.$errorUtil.retrieErrorMessage(error)
            );
          },
        })
      );
    } else {
      this.dialogHandler.alert("No file selected for import.");
    }
  }

  updateDeliveriesBatch() {
    const files = this.deliveryImportFile$.getValue();

    console.log("Updating delivery data from files:", files);

    if (files.length > 0) {
      const file = files[0];
      this.registerSubscription(
        this.$deliveryService.updateBatchByExcelFile(file).subscribe({
          next: (data) => {
            console.log("Delivery data updated successfully:", data);
            this.dialogHandler.alert("Delivery data updated successfully.");
            this.deliveryImportFile$.next([]);
            this.search(this.generateDefaultPage()); // Refresh the data
          },
          error: (error) => {
            console.error("Error updating delivery data:", error);
            this.dialogHandler.alert(
              "Failed to update delivery data. " +
                this.$errorUtil.retrieErrorMessage(error)
            );
          },
        })
      );
    } else {
      this.dialogHandler.alert("No file selected for update.");
    }
  }

  exportDeliveries() {
    this.registerSubscription(
      this.$deliveryService.export(this.buildSearchDto()).subscribe({
        next: (response) => {
          const blob = new Blob([response], {
            type: "application/vnd.ms-excel",
          });
          BlobUtil.downloadBlob(blob, "deliveries_export.xlsx");
        },
        error: (error) => {
          console.error("Error exporting deliveries:", error);
          this.dialogHandler.alert(
            "Failed to export deliveries. " +
              this.$errorUtil.retrieErrorMessage(error)
          );
        },
      })
    );
  }

  exportSummaryDeliveries() {
    this.registerSubscription(
      this.$deliveryService
        .exportSummaryDeliveries(this.buildSearchDto())
        .subscribe({
          next: (response) => {
            const blob = new Blob([response], {
              type: "application/vnd.ms-excel",
            });
            BlobUtil.downloadBlob(blob, "summary_deliveries_export.xlsx");
          },
          error: (error) => {
            console.error("Error exporting deliveries:", error);
            this.dialogHandler.alert(
              "Failed to export deliveries. " +
                this.$errorUtil.retrieErrorMessage(error)
            );
          },
        })
    );
  }

  editDelivery(el: DeliveryFE) {
    const dialogRef = this.dialog.open<
      EditDialogComponent,
      DeliveryFE,
      DeliveryFE
    >(EditDialogComponent, {
      data: el,
      width: "600px",
    });

    this.registerSubscription(
      dialogRef.afterClosed().subscribe((result) => {
        if (result) {
          console.log("result = ", result);
          this.$deliveryService.update(result.deliveryId, result).subscribe({
            next: (data) => {
              console.log("Delivery updated successfully:", data);
              this.dialogHandler.alert("Delivery updated successfully.");
              this.search(this.generateDefaultPage()); // Refresh the data
            },
            error: (error) => {
              console.error("Error updating delivery:", error);
              this.dialogHandler.alert(
                "Failed to update delivery. " +
                  this.$errorUtil.retrieErrorMessage(error)
              );
            },
          });
        }
      })
    );
  }
}
