import { Component } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource, MatTableModule } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { BasePage } from "@app/pages/base.page";
import { AppInputFileComponent } from "@app/widget/component/input-file/input-file.component";
import { BehaviorSubject } from "rxjs";
import { DeliveryReturnService } from "./delivery-return.service";
import { DeliveryReturnFE } from "@app/model/business/delivery-return.model";

@Component({
  standalone: true,
  templateUrl: "./delivery-return.component.html",
  styleUrl: "./delivery-return.component.scss",
  imports: [
    MainLayoutComponent,
    MatTableModule,
    AppCommonModule,
    AppInputFileComponent,
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
    "returnStatusName",
    "note",
  ];
  dataSource = new MatTableDataSource<DeliveryReturnFE>([]);

  paginator = this.generatePaginator();

  deliveryReturnImportFile$ = new BehaviorSubject<File[]>([]);

  // methods
  constructor(private $deliveryReturnService: DeliveryReturnService) {
    super("Delivery Return Management");
  }

  protected override __ngOnInit__() {
    // Initial search
    this.search(this.generateDefaultPage());
  }

  override search(pageEvt: PageEvent): void {
    this.paginator.pageIndex$.set(pageEvt.pageIndex);
    this.paginator.pageSize$.set(pageEvt.pageSize);

    this.registerSubscription(
      this.$deliveryReturnService
        .search({}, pageEvt.pageIndex, pageEvt.pageSize)
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
}
