import { Component, forwardRef, Input, signal } from "@angular/core";
import {
  ControlValueAccessor,
  FormControl,
  NG_VALUE_ACCESSOR,
  Validators,
} from "@angular/forms";
import { AppCommonModule } from "@app/core/app-common.module";
import { ShipperModel } from "@app/model/business/shipper.model";
import { IdType } from "@app/model/core/id.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { ShipperService } from "@app/service/shipper.service";
import { BehaviorSubject, map, Observable, startWith } from "rxjs";

@Component({
  selector: "app-select-shipper-widget",
  templateUrl: "./widget.component.html",
  styleUrls: ["./widget.component.scss"],
  imports: [AppCommonModule],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => AppSelectShipperWidget),
      multi: true,
    },
  ],
})
export class AppSelectShipperWidget
  extends BasePage
  implements ControlValueAccessor
{
  @Input({ required: true })
  formControl = new FormControl<Nullable<IdType>>("", [Validators.required]);

  list$ = new BehaviorSubject<ShipperModel[]>([]);

  filteredOptions$?: Observable<ShipperModel[]>;

  disableState$ = signal<boolean>(false);

  initedValueState$ = new BehaviorSubject<Nullable<IdType>>(null);

  /// methods

  constructor(private $shipperService: ShipperService) {
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

  private triggerUpdateFormControlValue() {
    if (0 < this.list$?.getValue().length) {
      this.formControl.setValue(this.initedValueState$.getValue());
    }
  }

  protected override __ngOnInit__() {
    this.registerSubscription(
      this.$shipperService
        .search(
          {
            name: "",
          },
          0,
          20
        )
        .subscribe((shipperPage) => {
          this.list$.next(shipperPage.content);

          this.triggerUpdateFormControlValue();

          this.filteredOptions$ = this.formControl.valueChanges.pipe(
            startWith(""),
            map((value) =>
              this.list$
                .getValue()
                .filter(
                  (shipper) =>
                    !value ||
                    ("string" === typeof value &&
                      shipper.shipperName
                        ?.toLowerCase()
                        .includes(value.toLowerCase()))
                )
            )
          );
        })
    );

    this.triggerUpdateFormControlValue();
  }

  displayFn(shipperId: IdType): string {
    const com_ = this.list$
      ?.getValue()
      .find((com) => com.shipperId == shipperId);
    return com_ ? `${com_.shipperName} (${com_.shipperId})` : "";
  }
}
