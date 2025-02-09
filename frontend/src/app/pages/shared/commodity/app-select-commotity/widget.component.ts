import { Component, forwardRef, Input, signal } from "@angular/core";
import {
  ControlValueAccessor,
  FormControl,
  NG_VALUE_ACCESSOR,
  Validators,
} from "@angular/forms";
import { AppCommonModule } from "@app/core/app-common.module";
import { CommodityModel } from "@app/model/business/commodity.model";
import { IdType } from "@app/model/core/id.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { CommodityService } from "@app/pages/commodity/commodity-mgmt/commodity.service";
import { BehaviorSubject, map, Observable, startWith } from "rxjs";

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

  list$ = new BehaviorSubject<CommodityModel[]>([]);

  filteredOptions$?: Observable<CommodityModel[]>;

  disableState$ = signal<boolean>(false);

  /// methods
  constructor(private $commodityService: CommodityService) {
    super();
  }

  writeValue(obj: any): void {
    console.log("writeValue: ", obj);
    this.formControl.setValue(obj);
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
      this.$commodityService.findAll().subscribe((commodityList) => {
        this.list$.next(commodityList);

        this.filteredOptions$ = this.formControl.valueChanges.pipe(
          startWith(""),
          map((value) =>
            this.list$
              .getValue()
              .filter(
                (com) =>
                  !value ||
                  ("string" === typeof value &&
                    com.comName?.toLowerCase().includes(value.toLowerCase()))
              )
          )
        );
      })
    );
  }

  displayFn(comId: IdType): string {
    const com_ = this.list$?.getValue().find((com) => com.comId === comId);
    return com_ ? `${com_.comName} (${com_.comId})` : "";
  }
}
