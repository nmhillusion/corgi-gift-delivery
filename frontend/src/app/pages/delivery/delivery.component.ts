import { Component } from "@angular/core";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { BasePage } from "@app/pages/base.page";
import { MatTableDataSource, MatTableModule } from "@angular/material/table";
import { Delivery } from "@app/model/business/delivery.model";
import { AppCommonModule } from "@app/core/app-common.module";
import { AppInputFileComponent } from "@app/widget/component/input-file/input-file.component";
import { BehaviorSubject } from "rxjs";
import { DeliveryService } from "./delivery.service";
import { PageEvent } from "@angular/material/paginator";

@Component({
  standalone: true,
  templateUrl: "./delivery.component.html",
  styleUrl: "./delivery.component.scss",
  imports: [
    MainLayoutComponent,
    MatTableModule,
    AppCommonModule,
    AppInputFileComponent,
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
  ];
  dataSource = new MatTableDataSource<Delivery>([]);

  paginator = this.generatePaginator();

  deliveryImportFile$ = new BehaviorSubject<File[]>([]);

  // methods
  constructor(private $deliveryService: DeliveryService) {
    super("Delivery Management");
  }

  protected override __ngOnInit__() {
    // Initial search
    this.search(this.generateDefaultPage());
  }

  override search(pageEvt: PageEvent): void {
    this.paginator.pageIndex$.set(pageEvt.pageIndex);
    this.paginator.pageSize$.set(pageEvt.pageSize);

    this.registerSubscription(
      this.$deliveryService
        .search({}, pageEvt.pageIndex, pageEvt.pageSize)
        .subscribe({
          next: (page) => {
            this.dataSource.data = page.content;
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
}
