import { Component, Input, ModelSignal } from "@angular/core";
import { FormControl, ValidatorFn } from "@angular/forms";
import { AppCommonModule } from "@app/core/app-common.module";
import { RecipientModel } from "@app/model/business/recipient.model";
import { IdType } from "@app/model/core/id.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { RecipientService } from "@app/service/recipient.service";
import { BehaviorSubject, map, Observable, startWith } from "rxjs";

@Component({
  standalone: true,
  selector: "app-select-recipient-widget",
  templateUrl: "./widget.component.html",
  styleUrls: ["./widget.component.scss"],
  imports: [AppCommonModule],
})
export class AppSelectRecipientWidget extends BasePage {
  @Input({ required: true })
  value$!: BehaviorSubject<Nullable<IdType>>;

  @Input()
  validators?: ValidatorFn | ValidatorFn[] | null = null;

  list$ = new BehaviorSubject<RecipientModel[]>([]);
  keywordControl = new FormControl<string>("");

  filteredOptions$?: Observable<RecipientModel[]>;

  /// methods
  constructor(private $recipientService: RecipientService) {
    super();
  }

  protected override __ngOnInit__() {
    this.registerSubscription(
      this.$recipientService
        .search(
          {
            name: "",
          },
          0,
          20
        )
        .subscribe((recipientPage) => {
          this.list$.next(recipientPage.content);

          this.filteredOptions$ = this.keywordControl.valueChanges.pipe(
            startWith(""),
            map((value) =>
              this.list$
                .getValue()
                .filter(
                  (com) =>
                    !value ||
                    ("string" === typeof value &&
                      com.fullName?.toLowerCase().includes(value.toLowerCase()))
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
    const com_ = this.list$
      ?.getValue()
      .find((com) => com.recipientId === comId);
    return com_ ? `${com_.fullName} (${com_.recipientId})` : "";
  }
}
