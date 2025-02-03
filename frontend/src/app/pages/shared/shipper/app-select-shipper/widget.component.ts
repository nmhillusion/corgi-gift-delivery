import { Component, Input } from "@angular/core";
import { ValidatorFn, FormControl, Validators } from "@angular/forms";
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
})
export class AddSelectShipperWidget extends BasePage {
  @Input({ required: true })
  value$!: BehaviorSubject<Nullable<IdType>>;

  @Input()
  validators?: ValidatorFn | ValidatorFn[] | null = null;

  list$ = new BehaviorSubject<ShipperModel[]>([]);
  keywordControl = new FormControl<string>("", [Validators.required]);

  filteredOptions$?: Observable<ShipperModel[]>;

  /// methods

  constructor(private $shipperService: ShipperService) {
    super();
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

          this.filteredOptions$ = this.keywordControl.valueChanges.pipe(
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

    this.registerSubscription(
      this.keywordControl.valueChanges.subscribe((value) => {
        this.value$.next(value);
      })
    );

    if (this.validators) {
      this.keywordControl.setValidators(this.validators);
    }
  }

  displayFn(shipperId: IdType): string {
    const com_ = this.list$?.getValue().find((com) => com.shipperId === shipperId);
    return com_ ? `${com_.shipperName} (${com_.shipperId})` : "";
  }
}
