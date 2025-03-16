import { Component } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { CommodityTypeModel } from "@app/model/business/commodity-type.model";
import { BasePage } from "@app/pages/base.page";
import { CommodityTypeService } from "@app/service/commodity-type.service";

@Component({
  templateUrl: "./select-commodity-type--dialog.component.html",
  styleUrls: ["./select-commodity-type--dialog.component.scss"],
  standalone: true,
  imports: [AppCommonModule],
})
export class SelectCommodityTypeDialog extends BasePage {
  // fields
  $$comTypeSource = new MatTableDataSource<CommodityTypeModel>();
  paginator = this.generatePaginator();

  // methods
  constructor(private $commodityTypeService: CommodityTypeService) {
    super();
  }

  protected override __ngOnInit__() {}

  override search(pageEvt: PageEvent): void {
    this.registerSubscription(
      this.$commodityTypeService
        .search(
          {
            keyword: "",
          },
          pageEvt.pageIndex,
          pageEvt.pageSize
        )
        .subscribe((pageResult) => {
          this.handlePageDataUpdate<CommodityTypeModel>(
            pageResult,
            this.paginator,
            this.$$comTypeSource
          );
        })
    );
  }
}
