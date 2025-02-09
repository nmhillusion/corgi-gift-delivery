import {
  Component,
  forwardRef,
  Input,
  signal
} from "@angular/core";
import {
  ControlValueAccessor,
  FormControl,
  NG_VALUE_ACCESSOR
} from "@angular/forms";
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
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => AppSelectRecipientWidget),
      multi: true,
    },
  ],
})
export class AppSelectRecipientWidget
  extends BasePage
  implements ControlValueAccessor
{
  @Input({ required: true })
  formControl = new FormControl<Nullable<IdType>>("");

  list$ = new BehaviorSubject<RecipientModel[]>([]);

  filteredOptions$?: Observable<RecipientModel[]>;

  disableState$ = signal<boolean>(false);

  /// methods
  constructor(private $recipientService: RecipientService) {
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

          this.filteredOptions$ = this.formControl.valueChanges.pipe(
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
  }

  displayFn(comId: IdType): string {
    const com_ = this.list$
      ?.getValue()
      .find((com) => com.recipientId === comId);
    return com_ ? `${com_.fullName} (${com_.recipientId})` : "";
  }
}
