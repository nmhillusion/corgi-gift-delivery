import { Component, Input, ModelSignal, WritableSignal } from "@angular/core";
import {
  FormControl,
  FormControlOptions,
  ValidatorFn,
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
})
export class AppSelectCommodityWidget extends BasePage {
  @Input({ required: true })
  value$!: BehaviorSubject<Nullable<IdType>>;

  @Input()
  validators?: ValidatorFn | ValidatorFn[] | null = null;

  list$ = new BehaviorSubject<CommodityModel[]>([]);
  keywordControl = new FormControl<string>("", [Validators.required]);

  filteredOptions$?: Observable<CommodityModel[]>;

  /// methods
  constructor(private $commodityService: CommodityService) {
    super();
  }

  protected override __ngOnInit__() {
    this.registerSubscription(
      this.$commodityService.findAll().subscribe((commodityList) => {
        this.list$.next(commodityList);

        this.filteredOptions$ = this.keywordControl.valueChanges.pipe(
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

    this.registerSubscription(
      this.keywordControl.valueChanges.subscribe((value) => {
        this.value$.next(value);
      })
    );

    if (this.validators) {
      this.keywordControl.setValidators(this.validators);
    }
  }

  displayFn(comId: IdType): string {
    const com_ = this.list$?.getValue().find((com) => com.comId === comId);
    return com_ ? `${com_.comName} (${com_.comId})` : "";
  }
}
