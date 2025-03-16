import { Component, forwardRef, Input, signal } from "@angular/core";
import {
  ControlValueAccessor,
  FormControl,
  NG_VALUE_ACCESSOR,
  Validators,
} from "@angular/forms";
import { AppCommonModule } from "@app/core/app-common.module";
import { SIZE } from "@app/layout/size.constant";
import { CommodityModel } from "@app/model/business/commodity.model";
import { IdType } from "@app/model/core/id.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { CommodityService } from "@app/pages/commodity/commodity-mgmt/commodity.service";
import { SelectCommodityDialog } from "@app/pages/commodity/select-commodity--dialog/select-commodity--dialog.component";
import { BehaviorSubject } from "rxjs";

@Component({
  standalone: true,
  selector: "app-select-commodity-widget",
  templateUrl: "./widget.component.html",
  styleUrls: ["./widget.component.scss"],
  imports: [AppCommonModule],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => AppSelectCommodityWidget),
      multi: true,
    },
  ],
})
export class AppSelectCommodityWidget
  extends BasePage
  implements ControlValueAccessor
{
  @Input({ required: true })
  formControl = new FormControl<Nullable<IdType>>("", [Validators.required]);

  disableState$ = signal<boolean>(false);

  initedValueState$ = new BehaviorSubject<Nullable<IdType>>(null);

  $$selectedCom$ = signal<Nullable<CommodityModel>>(null);

  /// methods
  constructor(private $commodityService: CommodityService) {
    super();
  }

  writeValue(obj: any): void {
    console.log("writeValue: ", obj);
    this.initedValueState$.next(obj);
  }
  registerOnChange(fn: any): void {
    // DO NOTHING
  }

  registerOnTouched(fn: any): void {
    // DO NOTHING
  }
  setDisabledState?(isDisabled: boolean): void {
    console.log("setDisabledState: ", isDisabled);
    this.disableState$.set(isDisabled);
  }

  protected override __ngOnInit__() {
    this.registerSubscription(
      this.initedValueState$.subscribe((newVal) => {
        if (newVal) {
          this.registerSubscription(
            this.$commodityService.findById(newVal).subscribe((commodity) => {
              this.$$selectedCom$.set(commodity);
            })
          );
        }
      })
    );
  }

  openSelectCommodityDialog() {
    const dialogRef = this.$dialog.open<
      SelectCommodityDialog,
      {
        commodity: Nullable<CommodityModel>;
      },
      Nullable<CommodityModel>
    >(SelectCommodityDialog, {
      data: {
        commodity: this.$$selectedCom$(),
      },
      width: SIZE.DIALOG.width,
      maxHeight: SIZE.DIALOG.height,
    });

    this.registerSubscription(
      dialogRef.afterClosed().subscribe((commodity) => {
        if (commodity) {
          this.$$selectedCom$.set(commodity);
          this.formControl.setValue(commodity.comId);
        }
      })
    );
  }
}
