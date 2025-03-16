import { Component, inject, signal } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { PAGE } from "@app/layout/page.constant";
import {
  CommodityTypeModel,
  CommodityTypeSelectableModel,
} from "@app/model/business/commodity-type.model";
import { Nullable } from "@app/model/core/nullable.model";
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
  $$dialogData = inject<{ commodityType: Nullable<CommodityTypeModel> }>(
    MAT_DIALOG_DATA
  );

  $$comTypeSource = new MatTableDataSource<CommodityTypeSelectableModel>();
  paginator = this.generatePaginator();

  displayedColumns = ["selection", "typeId", "typeName"];

  $$selectedType$ = signal<Nullable<CommodityTypeSelectableModel>>(null);

  // methods
  constructor(
    private $commodityTypeService: CommodityTypeService,
    private $dialogRef: MatDialogRef<
      SelectCommodityTypeDialog,
      Nullable<CommodityTypeModel>
    >
  ) {
    super();
  }

  protected override __ngOnInit__() {
    this.search(PAGE.DEFAULT_PAGE_EVENT);
  }

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
          this.handlePageDataUpdate<CommodityTypeSelectableModel>(
            {
              content: pageResult.content.map((it) => {
                const itE = it as CommodityTypeSelectableModel;
                itE.selected$ = signal(
                  this.$$dialogData.commodityType?.typeId == it.typeId
                );
                return itE;
              }),
              page: pageResult.page,
            },
            this.paginator,
            this.$$comTypeSource
          );
        })
    );
  }

  onSelectType(mType: CommodityTypeSelectableModel) {
    this.$$comTypeSource.data.forEach((it) => {
      it.selected$.set(false);
    });

    mType.selected$.set(true);
    this.$$selectedType$.set(mType);
  }

  save() {
    const result = this.$$selectedType$();
    console.log("Selected on type: ", result);

    this.$dialogRef.close(result);
  }
}
