import { Component, inject, signal } from "@angular/core";
import { AppCommonModule } from "@app/core/app-common.module";
import { BasePage } from "@app/pages/base.page";
import { CommodityService } from "../commodity-mgmt/commodity.service";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import {
  CommodityFEModel,
  CommodityModel,
} from "@app/model/business/commodity.model";
import { MatTableDataSource } from "@angular/material/table";
import { PageEvent } from "@angular/material/paginator";
import { Nullable } from "@app/model/core/nullable.model";
import { PAGE } from "@app/layout/page.constant";

@Component({
  standalone: true,
  templateUrl: "./select-commodity--dialog.component.html",
  styleUrls: ["./select-commodity--dialog.component.scss"],
  imports: [AppCommonModule],
})
export class SelectCommodityDialog extends BasePage {
  /// fields
  $$dialogData = inject<{
    commodity?: CommodityModel;
  }>(MAT_DIALOG_DATA);

  $$tableDS = new MatTableDataSource<CommodityFEModel>();

  paginator = this.generatePaginator();
  displayedColumns = [
    "selection",
    ///
    "comId",
    "comName",
    "comType",
  ];

  $$selectedCom$ = signal<Nullable<CommodityModel>>(null);

  /// methods
  constructor(
    private $commodityService: CommodityService,
    private $dialogRef: MatDialogRef<
      SelectCommodityDialog,
      Nullable<CommodityModel>
    >
  ) {
    super();
  }

  protected override __ngOnInit__() {
    this.search(PAGE.DEFAULT_PAGE_EVENT);

    if (this.$$dialogData.commodity?.comId) {
      this.$$selectedCom$.set(this.$$dialogData.commodity);
    }
  }

  override search(pageEvt: PageEvent): void {
    this.registerSubscription(
      this.$commodityService
        .search(
          {
            keyword: "",
          },
          pageEvt.pageIndex,
          pageEvt.pageSize
        )
        .subscribe((pageResult) => {
          this.handlePageDataUpdate<CommodityFEModel>(
            {
              page: pageResult.page,
              content: pageResult.content
                .map((it) => this.$commodityService.convertToFe(it, this))
                .map((it) => {
                  it.selected$ = signal(
                    this.$$dialogData.commodity?.comId == it.comId
                  );
                  return it;
                }),
            },
            this.paginator,
            this.$$tableDS
          );
        })
    );
  }

  onChangeCommodity(el: CommodityFEModel): void {
    this.$$tableDS.data.forEach((it) => it.selected$.set(false));
    el.selected$.set(true);
    this.$$selectedCom$.set(el);
  }

  save() {
    const result = this.$$selectedCom$();
    console.log("Selected commodity: ", result);

    this.$dialogRef.close(result);
  }
}
